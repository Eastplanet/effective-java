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
