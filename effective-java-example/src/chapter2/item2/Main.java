package chapter2.item2;

public class Main {

    public static void main(String[] args) {

        NutritionFacts2 facts = new NutritionFacts2();
        facts.setA(1);
        facts.setB(1);
        facts.setC(1);

        NutritionFacts3 facts3 = new NutritionFacts3.Builder(1)
                .b(2)
                .c(3)
                .build();

        NutritionFacts4 build = NutritionFacts4.builder(1)
                .b(2)
                .c(3)
                .build();
    }
}
