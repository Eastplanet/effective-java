package chapter2.item1.composition;

public class main {

    public static void main(String[] args) {
        Hamburger hamburger = new Hamburger();
        hamburger.meat = new 불고기패티(); // 런타임에 객체 결정

        Hamburger hamburger2 = new Hamburger();
        hamburger.meat = new 새우패티();
    }
}
