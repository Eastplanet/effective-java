package chapter2.item13;

public class Parent implements Cloneable{

    public Car car;

    @Override
    protected Parent clone() throws CloneNotSupportedException {
        Parent clone = (Parent) super.clone();
        clone.car = car.clone();
        return clone;
    }
}
