package chapter2.item3.serializable;

import java.io.ObjectStreamException;
import java.io.Serializable;

public class Singleton implements Serializable {
    // 싱글턴 인스턴스
    private static final Singleton INSTANCE = new Singleton();
    // private 생성자
    private Singleton() {}
    public static Singleton getInstance() {
        return INSTANCE;
    }

    // readResolve 메서드로 역직렬화 시 원래 인스턴스 반환
    private Object readResolve() throws ObjectStreamException {
        return INSTANCE;
    }
}


