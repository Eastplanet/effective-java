package chapter2.item1;

public interface Mouse {

    void click();

    public static Mouse valueOf(){
        return new WirelessMouse();
    }
}
