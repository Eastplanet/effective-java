package chapter2.item1;

public class DefaultMouse implements Mouse {

    @Override
    public void click() {
        System.out.println("기본 마우스 클릭!");
    }
}
