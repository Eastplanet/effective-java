package chapter2.item2;

public class NutritionFacts4 {

    private final int a;
    private final int b;
    private final int c;

    public static Builder builder(int a){
        return new Builder(a);
    }

    public static class Builder{
        private final int a;
        private int b;
        private int c;

        public Builder(int a){
            this.a = a;
        }

        public NutritionFacts4.Builder b(int b){
            this.b = b;
            return this;
        }

        public NutritionFacts4.Builder c(int c){
            this.c = c;
            return this;
        }

        public NutritionFacts4 build(){
            return new NutritionFacts4(this);
        }
    }

    private NutritionFacts4(NutritionFacts4.Builder builder){
        a = builder.a;
        b = builder.b;
        c = builder.c;
    }
}
