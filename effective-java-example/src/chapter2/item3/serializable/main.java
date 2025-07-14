package chapter2.item3.serializable;

import java.io.*;

public class main {
    public static void main(String[] args) {

        try {
            // 싱글턴 인스턴스 가져오기
            Singleton singleton = Singleton.getInstance();

            // 직렬화: 객체를 파일에 저장
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("singleton.ser"));
            out.writeObject(singleton);
            out.close();

            // 역직렬화: 파일에서 객체 복원
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("singleton.ser"));
            Singleton deserializedSingleton = (Singleton) in.readObject();
            in.close();

            // 인스턴스 동일성 확인
            System.out.println("원본 객체: " + singleton);
            System.out.println("직렬화/역직렬화 객체: " + deserializedSingleton);
            System.out.println("같은지 비교 : " + (singleton == deserializedSingleton));

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
