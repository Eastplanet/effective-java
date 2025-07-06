package chapter2.item1;

public class WirelessMouse implements Mouse {
    @Override
    public void click() {
        System.out.println("무선 마우스 클릭!");
    }
}
