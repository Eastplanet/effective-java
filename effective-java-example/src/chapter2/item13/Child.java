package chapter2.item13;

import javax.crypto.Cipher;

public class Child implements Cloneable{
    @Override
    protected Child clone() throws CloneNotSupportedException {
        return (Child) super.clone();
    }
}
