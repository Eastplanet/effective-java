package chapter2.item2;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public abstract class Phone {

    private final int size;

    abstract static class Builder<T extends Builder<T>> {

        private int size;
        public T size(int size) {
            this.size = size;
            return self();
        }

        abstract Phone build();

        protected abstract T self();
    }

    Phone(Builder<?> builder){
        size = builder.size;
    }
}
