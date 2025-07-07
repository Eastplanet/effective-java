package chapter2.item2;

public class NutritionFacts3 {

    private final int a;
    private final int b;
    private final int c;

    public static class Builder{
        private final int a;
        private int b;
        private int c;

        public Builder(int a){
            this.a = a;
        }

        public Builder b(int b){
            this.b = b;
            return this;
        }

        public Builder c(int c){
            this.c = c;
            return this;
        }

        public NutritionFacts3 build(){
            return new NutritionFacts3(this);
        }
    }

    private NutritionFacts3(Builder builder){
        a = builder.a;
        b = builder.b;
        c = builder.c;
    }
}
