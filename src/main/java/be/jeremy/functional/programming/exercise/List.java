package be.jeremy.functional.programming.exercise;

import static be.jeremy.functional.programming.exercise.TailCall.ret;
import static be.jeremy.functional.programming.exercise.TailCall.sus;

/**
 * @author Jeremy
 */
public abstract class List<T> {

    public abstract T head();
    public abstract List<T> tail();
    public abstract Boolean isEmpty();
    public abstract List<T> setHead(T t);

    private List() {}

    public static final List NIL = new Nil();

    public static <T> List<T> list() {
        return NIL;
    }

    @SafeVarargs
    public static <T> List<T> list(T... ts) {
        List<T> result = list();

        for (int i = ts.length - 1; i >= 0; --i) {
            result = new Cons(ts[i], result);
        }

        return result;
    }

    public static <T> List<T> setHead(List<T> list, T t) {
        return list.setHead(t);
    }

    public List<T> cons(T t) {
        return new Cons<>(t, this);
    }

    private static class Nil<T> extends List<T> {

        private Nil() {}

        @Override
        public T head() {
            throw new IllegalStateException("head called on empty list");
        }

        @Override
        public List<T> tail() {
            throw new IllegalStateException("tail called on empty list");
        }

        @Override
        public Boolean isEmpty() {
            return true;
        }

        @Override
        public List<T> setHead(T t) {
            throw new IllegalStateException("setHead called on empty list");
        }

        @Override
        public String toString() {
            return "[NIL]";
        }
    }

    private static class Cons<T> extends List<T> {

        private T head;
        private List<T> tail;

        private Cons(T head, List<T> tail) {
            this.head = head;
            this.tail = tail;
        }

        @Override
        public T head() {
            return head;
        }

        @Override
        public List<T> tail() {
            return tail;
        }

        @Override
        public Boolean isEmpty() {
            return false;
        }

        @Override
        public List<T> setHead(T t) {
            return new Cons<>(t, this.tail);
        }

        @Override
        public String toString() {
            return String.format("[%sNIL]", toString(new StringBuilder(), this).eval());
        }

        private TailCall<String> toString(StringBuilder builder, List<T> l) {
            if (l.isEmpty()) {
                return ret(builder.toString());
            } else {
                return sus(() -> toString(builder.append(l.head()).append(", "), l.tail()));
            }
        }
     }
}