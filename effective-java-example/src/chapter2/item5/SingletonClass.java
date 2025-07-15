package chapter2.item5;

import java.util.Dictionary;
import java.util.Hashtable;

public class SingletonClass {
    private static final SingletonClass INSTANCE = new SingletonClass();

    private SingletonClass() {}

    public static SingletonClass getInstance() {
        return INSTANCE;
    }

    private final Dictionary dictionary = new Hashtable();

    public boolean isValid(String word){
        return false;
    }
}
