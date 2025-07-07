# 아이템 1. 생성자 대신 정적 팩토리 메서드를 고려하라

```java
// new 사용
Boolean b1 = new Boolean(false);

// 정적 팩토리 메소드 사용
Boolean b2 = Boolean.valueOf(false);

// ex) Boolean 내부 정적 팩토리 메서드 구현
public static Boolean valueOf(boolean b) {
	return b ? Boolean.TRUE : Boolean.FALSE;
}
```

<br>

## 정적 팩토리 메소드의 장점

### 1. **이름을 가질 수 있다.**

```java
BigInteger bigInteger = new BigInteger(int, int, Random);
BigInteger bigInteger = BigInteger.probablePrime(int, Random);
```

> 💡probablePrime ? <br>
> 주어진 bit길이에 해당하는 소수일 가능성이 매우 높은 숫자 값을 생성

new BigInteger(int, int, Random)보다는 BigInteger.probablePrime(int, Random)이 훨씬 동작을 이해하기 편하다.

<br>

### 2. **호출될 때마다 인스턴스를 새로 생성하지 않아도 된다.**

new 키워드를 사용한다면 매번 새로 생성되지만, 정적 팩토리 메소드 방식은 미리 만들어 놓거나, 캐싱하는 등 재사용이 가능하다. ex) 싱글톤

```java
Integer integer = new Integer(10);
Integer integer1 = Integer.valueOf(10);
```

위 두 방식은 차이가 없어 보인다. 하지만 위 방식은 `deprecated` 되었고, 일반적으로 아래 방식을 추천한다고 나와있다. 그 이유는 Integer에서 내부적으로 **캐싱**을 수행하기 때문이다.

자주 사용될 것으로 예상되는 -128 ~ 127의 값에 대해서는 미리 인스턴스를 만들어 놓은 후 재사용을 한다.

따라서 다음과 같은 예상치 못한 동작을 볼 수 있다.

```java
Integer valueOf1 = Integer.valueOf(127);
Integer valueOf2 = Integer.valueOf(127);
System.out.println(valueOf1 == valueOf2); // true
```

```java
Integer new1 = new Integer(127);
Integer new2 = new Integer(127);
System.out.println(new1 == new2); // false
```

-128 ~ 127에 대해 캐싱된다고 했으므로 아래와 같은 숫자는 false 가 나타난다

```java
Integer valueOf1 = Integer.valueOf(128);
Integer valueOf2 = Integer.valueOf(128);
System.out.println(valueOf1 == valueOf2); // false
```

( 캐싱 동작을 확인하기 위해 == 비교를 사용했을 뿐, Integer도 객체이기 때문에 equals를 사용해야한다.)

<br>

### 3. **반환 타입의 하위 타입 객체를 반환할 수 있는 능력이 있다.**

Collections의 emptyList와 singletonList는 둘다 List를 반환하지만 내부를 보면 각각 내부적으로 최적화된 하위 타입인 EmptyList, SingletonList를 반환하고 있다.

```java
List<Object> objects = Collections.emptyList();
List<Object> objects1 = Collections.singletonList();
```

이런 방식을 통해 사용자가 아래와 같이 하위 타입을 알아야 할 필요가 없어진다.

(예시일뿐 EmptyList는 Collections의 내부에 private static class로 존재하여 직접 생성할 수 없다.)

```java
EmptyList<Object> object = new EmptyList();
```

> 💡 Collections의 내부에는 이런 클래스가 45개나 있다. <br>
> 사용자가 이 45개의 클래스에 대해 알지 못하게 하여 프로그래밍의 난이도를 낮췄다.

<br>

### 4. **입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다.**

3번과 유사한 내용

<br>

### 5. **정적 팩토리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.**

EmptyList를 사용하다가, 개선된 버전의 EmptyListV2가 나왔다고 생각해보자

new를 사용한다면 기존의 코드를 아래와 같이 수정해야 한다. (동작 코드 X)

```java
// List<Object> list = new EmptyList();
List<Object> list = new EmptyListV2();
```

정적 팩토리 메서드에서는 내부에서 아래와 같이 수정하면 사용자는 아무런 코드 변화 없이 사용할 수 있을 것이다.

```java
public static final List emptyList() {
    //return new EmptyList();
    return new EmptyListV2();
}
```

> 💡 둘 다 수정해야 하는 것 아니냐고 생각할 수도 있지만, 위의 경우 new EmptyList()를 100번 사용했다면, 100번을 수정

<br>
<br>

## 정적 팩토리 메서드 단점

### 1. 상속을 하려면 public이나 protected 생성자가 필요하니 정적 팩토리 메서드만 제공하면 하위 클래스를 만들 수 없다.

상속을 하려면 public, protected 생성자가 필요하지만 private으로 막아두었다면, 하위 클래스를 만들 수 없다. 하지만 이는 상속보다는 컴포지션을 사용하도록 유도한다는 점에서 오히려 장점으로 받아들일 수도 있다.

@TODO : 상속과 컴포지션의 차이 설명

<br>

### 2. 정적 팩토리 메서드는 프로그래머가 찾기 어렵다.

흔히 사용되는 정적 팩토리 메서드 명명 방식을 이해하여 작성하고, 사용해야 한다는 점이 존재한다.
<br>
쉽게 말해 abcdef() 이런 식으로 작성하면 찾기 어려우니 최대한 통상적으로 사용하는 명명 규칙을 사용하자는 뜻이다.

```java
Date d = Date.from();
BigInteger prime = BigInteger.valueOf();
StackWalker luke = StackWalker.getInstance();
// 등등...
```

<br>
<br>
<br>


# 아이템2. 생성자에 매개변수가 많다면 빌더를 고려하라

정적 팩토리와 생성자에는 매개변수가 많을 때 클라이언트 코드를 작성하거나 읽기 어렵다.

### 점층적 생성자 패턴

아래와 같은 `점층적 생성자 패턴`을 사용하면 매개변수가 가독성이 좋지않고, 또한 a와 c만 받는 생성자를 만들 수 없으니(a,b만 받는 생성자와 시그니처가 동일해서) 
필요없는 b를 받아야할 수도 있다. 

```java
public class NutritionFacts {
    
    private int a;
    private int b;
    private int c;

    public NutritionFacts() {
    }

    public NutritionFacts(int a) {
        this.a = a;
    }

    public NutritionFacts(int a, int b) {
        this.a = a;
        this.b = b;
    }
    
    public NutritionFacts(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
}
```

### 자바빈즈(JavaBeans) 패턴

두번째 방식으로는 `자바빈즈 패턴`을 사용할 수 있다. <br>
매개변수가 없는 생성자를 만든 후 setter를 통해 값을 설정하는 방식이다

```java
public class NutritionFacts2 {

    private int a;
    private int b;
    private int c;

    public NutritionFacts2() {
    }

    public void setA(int a) {
        this.a = a;
    }

    public void setB(int b) {
        this.b = b;
    }

    public void setC(int c) {
        this.c = c;
    }
}
```

```java
NutritionFacts2 facts = new NutritionFacts2();
facts.setA(1);
facts.setB(1);
facts.setC(1);
```
더 읽기 쉬워졌지만, 이 방식의 가장 큰 문제는 값이 세팅되기 전까지 일관성이 무너진 상태에 놓이게 된다.<br>

### 빌더 패턴
세번째 방법은 안정성과 가독성을 겸비한 빌더 패턴이다.<br>

필수 값은 Builder에서 final로 선언하여 생성할 때 받고, 각 setter와 유사한 메서드를 이용하여 값을 세팅한 뒤, 마지막에 build를 통해 생성한다.
setter들은 빌더 자신을 반환하므로 연쇄적으로 호출을 할 수 있다. <br>
이런 방식을 fluent API 혹은 method chaining이라 한다.

```java
public class NutritionFacts3 {

    private final int a;
    private final int b;
    private final int c;

    public static class Builder{
        private final int a;
        private int b;
        private int c;

        public Builder(int a){
            this.a = a;
        }

        public Builder b(int b){
            this.b = b;
            return this;
        }

        public Builder c(int c){
            this.c = c;
            return this;
        }

        public NutritionFacts3 build(){
            return new NutritionFacts3(this);
        }
    }

    private NutritionFacts3(Builder builder){
        a = builder.a;
        b = builder.b;
        c = builder.c;
    }
}
```

```java
NutritionFacts3 facts3 = new NutritionFacts3.Builder(1)
                .b(2)
                .c(3)
                .build();
```

아래와 같이 builder라는 메소드를 추가하여 new 키워드 없이 생성할 수도 있을 것 같다.
```java
public class NutritionFacts4 {

    private final int a;
    private final int b;
    private final int c;

    // 추가된 메소드
    public static Builder builder(int a){
        return new Builder(a);
    }

    public static class Builder{
        private final int a;
        private int b;
        private int c;

        public Builder(int a){
            this.a = a;
        }

        public NutritionFacts4.Builder b(int b){
            this.b = b;
            return this;
        }

        public NutritionFacts4.Builder c(int c){
            this.c = c;
            return this;
        }

        public NutritionFacts4 build(){
            return new NutritionFacts4(this);
        }
    }

    private NutritionFacts4(NutritionFacts4.Builder builder){
        a = builder.a;
        b = builder.b;
        c = builder.c;
    }
}
```

```java
NutritionFacts4 build = NutritionFacts4.builder(1)
                .b(2)
                .c(3)
                .build();
```

빌더 패턴은 계층적으로 설계된 클래스와 함께 쓰기에 좋다. <br>

```java
public abstract class Phone {

    private final int size;

    abstract static class Builder<T extends Builder<T>> {

        private int size;
        
        public T size(int size) {
            this.size = size;
            return self();
        }

        abstract Phone build();

        protected abstract T self();
    }

    Phone(Builder<?> builder){
        size = builder.size;
    }
}

public class SpeedPhone extends Phone {

    private final int speed;

    public static class Builder extends Phone.Builder<Builder> {
        
        private final int speed;

        public Builder(int speed) { this.speed = speed; }

        @Override
        SpeedPhone build() { return new SpeedPhone(this); }

        @Override
        protected Builder self() { return this; }
    }

    SpeedPhone(Builder builder) {
        super(builder);
        speed = builder.speed;
    }
}
```

```java
SpeedPhone build = new SpeedPhone.Builder(10).size(10).build();
```

### 빌더 패턴의 단점
- 객체를 만들기 위해 빌더부터 만들어야 한다. 생성 비용이 크지는 않지만 성능에 민감한 상황에서는 문제가 될 수 있다.
- 또한 코드가 장황해진다는 단점이 존재한다.

<br>
<br>

# 아이템 3. private 생성자나 열거 타입으로 싱글톤임을 보증하라

싱글톤이란 인스턴스를 오직 하나만 생성할 수 있는 클래스를 말한다. <br>
클래스를 싱글톤으로 만들면 이를 사용하는 클라이언트를 테스트하가 어려워질 수 있다. <br>
타입을 인터페이스로 정의한 다음 그 인터페이스를 구현해서 만든 싱글톤이 아니라면 싱글톤 인스턴스를 mock으로 대체할 수 없기 때문이다.

### public static final 필드 방식
private 생성자를 통해 초기화할 때 딱 한 번만 호출되도록 보장할 수 있다. <br>
하지만 리플렉션 API를 사용해 private 생성자를 호출할 수 있다. <br>
장점 
- 해당 클래스가 싱글톤임이 API에 잘 드러난다.
- 간결함
```java
public class Singleton1 {
    public static final Singleton1 INSTANCE = new Singleton1();
    private Singleton1() {}
    public void doSomeThing(){}
}
```

### 정적 팩토리 방식
장점
- 추후에 API를 바꾸지 않고도 싱글톤이 아니게 변경할 수 있다.
- 정적 팩토리를 제네릭 싱글톤 팩토리라 만들 수 있다. (아이템 30 참고)
- 정적 팩토리의 메소드 참조를 공급자로 사용할 수 있다. (Singletos2::getInstance를 Supplier<Singleton2>로 사용)
```java
public class Singleton2 {
    private static Singleton2 instance = new Singleton2();
    private Singleton2() {}
    public static Singleton2 getInstance() {
        return instance;
    }
    public void doSomeThing(){}
}
```

두 방식 모두 직렬화하기 위해서 단순히 Serializable을 구현한다고 선언하는 것만으로는 부족하다. <br>
모든 인스턴스 필드를 일시적(transient)이라고 선언하고 readResolve 메서드를 제공해야 한다.(아이템 89) <br>
이렇게 하지 않으면 직렬화된 인스턴스를 역직렬화할 때마다 새로운 인스턴스가 만들어진다.
@TODO : 직렬화와 새로운 인스턴스 생성 확인

### 원소가 하나인 열거 타입 선언 방식
추가 노력 없이 직렬화가 가능하고, 리플렉션 공격에서도 새로운 인스턴스가 생기는 일을 막아준다. 대부분 상황에서는 이 방식이 가장 좋지만, Enum외의 클래스를 상속해야 한다면 사용할 수 없다
```java
public enum Singleton3 {
    INSTANCE;
    
    public void doSomeThing(){}
}
```



