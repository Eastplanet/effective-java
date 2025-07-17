package chapter2.item13;

public class Car implements Cloneable {
    public String name;

    @Override
    protected Car clone() throws CloneNotSupportedException {
        return (Car)super.clone();
    }
}
