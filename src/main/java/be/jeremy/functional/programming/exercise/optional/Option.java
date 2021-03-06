package be.jeremy.functional.programming.exercise.optional;

import be.jeremy.functional.programming.exercise.List;

import java.util.function.Function;
import java.util.function.Supplier;

import static be.jeremy.functional.programming.exercise.List.foldRight;
import static be.jeremy.functional.programming.exercise.List.list;

/**
 * @author Jeremy
 */
public abstract class Option<T> {

    public abstract T getOrThrow();

    public abstract T getOrElse(T defaultValue);

    public abstract T getOrElse(Supplier<T> defaultValueSupplier);

    public abstract <S> Option<S> map(Function<T, S> f);

    public <S> Option<S> flatMap(Function<T, Option<S>> f) {
        return map(f).getOrElse(none());
    }

    public Option<T> orElse(Supplier<Option<T>> defaultValue) {
        return map(x -> this).getOrElse(defaultValue);
    }

    public Option<T> filter(Function<T, Boolean> p) {
        return flatMap(x -> p.apply(x) ? this : none());
    }

    public static <S, T> Function<Option<S>, Option<T>> lift(Function<S, T> f) {
        return o -> o.map(f);
    }

    public static <S, T> Function<Option<S>, Option<T>> liftWhenThrow(Function<S, T> f) {
        return o -> {
            try {
                return o.map(f);
            } catch (Exception e) {
                return none();
            }
        };
    }

    public static <S, T, U> Option<U> map2(Option<S> s, Option<T> t, Function<S, Function<T, U>> f) {
        return s.flatMap(sx -> t.map(tx -> f.apply(sx).apply(tx)));
    }

    public static <T> Option<List<T>> sequence(List<Option<T>> l) {
        return foldRight(l, some(list()), x -> y -> map2(x, y, a -> b -> b.cons(a)));
    }

    public static <S, T> Option<List<T>> traverse(List<S> list, Function<S, Option<T>> f) {
        return foldRight(list, some(list()), x -> y -> map2(f.apply(x), y, a -> b -> b.cons(a)));
    }

    @SuppressWarnings("rawtypes")
    private static Option none = new None();

    private static class None<T> extends Option<T> {

        private None() {
        }

        @Override
        public T getOrThrow() {
            throw new IllegalArgumentException("get called on None");
        }

        @Override
        public T getOrElse(T defaultValue) {
            return defaultValue;
        }

        @Override
        public T getOrElse(Supplier<T> defaultValueSupplier) {
            return defaultValueSupplier.get();
        }

        @Override
        public <S> Option<S> map(Function<T, S> f) {
            return none;
        }

        @Override
        public String toString() {
            return "None";
        }
    }

    private static class Some<T> extends Option<T> {

        private final T value;

        private Some(T value) {
            this.value = value;
        }

        @Override
        public T getOrThrow() {
            return value;
        }

        @Override
        public T getOrElse(T defaultValue) {
            return value;
        }

        @Override
        public T getOrElse(Supplier<T> defaultValueSupplier) {
            return value;
        }

        @Override
        public <S> Option<S> map(Function<T, S> f) {
            return some(f.apply(value));
        }

        @Override
        public boolean equals(Object that) {
            if (that instanceof Some) {
                return this.value.equals(((Some) that).value);
            }
            return this == that;
        }

        @Override
        public String toString() {
            return String.format("Some(%s)", value);
        }

    }

    public static <T> Option<T> some(T value) {
        return new Some<>(value);
    }

    public static <T> Option<T> none() {
        return none;
    }
}
