package chapter2.item2;

import java.util.List;

public class GenericExample {

    public static class MyClass implements Comparable<Integer>{
        @Override
        public int compareTo(Integer o) {
            return 0;
        }
    }

    public static void main(String[] args) {

        String string = new String("Hello World");
        method1(string);

        Integer integer = Integer.valueOf(1);
        method2(integer);

        Integer integer2 = Integer.valueOf(1);
        method3(integer2);
        MyClass myClass = new MyClass();
        method3(myClass);

        Integer integer3 = Integer.valueOf(1);
        method4(integer3);
        MyClass myClass2 = new MyClass();
//        method4(myClass2); 불가능

        SpeedPhone build = new SpeedPhone.Builder(10).size(10).build();
    }

    // 간단한 제네릭 타입, 제약이 없다.
    public static <T> void method1(T param){}

    // Number를 상속한 타입만 들어올 수 있다.
    public static <T extends Number> void method2(T param){}

    // Comparable<Integer>를 구현한 타입만 들어올 수 있다.
    public static <T extends Comparable<Integer>> void method3(T param){}

    // 재귀적 타입 한정, Comparable<T>를 구현한 타입만 들어올 수 있다. 즉 자기자신에 대해 Comparable이 가능한 타입만 올 수 있다.
    // 위의 MyClass는 오지 못함
    public static <T extends Comparable<T>> void method4(T param){}




}
