package chapter2.item2;

import java.util.Objects;

public class SpeedPhone extends Phone {

    private final int speed;

    public static class Builder extends Phone.Builder<Builder> {
        private final int speed;

        public Builder(int speed) {
            this.speed = speed;
        }

        @Override
        SpeedPhone build() {
            return new SpeedPhone(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    SpeedPhone(Builder builder) {
        super(builder);
        speed = builder.speed;
    }
}
