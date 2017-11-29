package be.jeremy.functional.programming.exercise;

import org.junit.Test;

import static be.jeremy.functional.programming.exercise.FunctionUtils.add;
import static be.jeremy.functional.programming.exercise.FunctionUtils.compose;
import static be.jeremy.functional.programming.exercise.FunctionUtils.composeWithLambda;
import static be.jeremy.functional.programming.exercise.FunctionUtils.curriedCompose;
import static be.jeremy.functional.programming.exercise.FunctionUtils.curriedFunc;
import static be.jeremy.functional.programming.exercise.FunctionUtils.func;
import static be.jeremy.functional.programming.exercise.FunctionUtils.partialFirst;
import static be.jeremy.functional.programming.exercise.FunctionUtils.partialSecond;
import static org.assertj.core.api.Assertions.assertThat;

public class FunctionUtilsTest {

    private Function<Integer, Integer> square = x -> x * x;
    private Function<Integer, Integer> triple = x -> x * 3;

    @Test
    public void applyCompose() {
        assertThat(compose(square, triple).apply(2)).isEqualTo(36);
    }

    @Test
    public void applyComposeWithLambda() {
        assertThat(composeWithLambda(square, triple).apply(2)).isEqualTo(36);
    }

    @Test
    public void applyAdd() {
        assertThat(add().apply(3).apply(2)).isEqualTo(5);
    }

    @Test
    public void applyCombineSquareAndTriple() {
        assertThat(curriedCompose().apply(square).apply(triple).apply(2)).isEqualTo(36);
    }

    @Test
    public void applyPolymorphicCompose() {
        assertThat(FunctionUtils.<Integer, Integer, Integer>polymorphicCompose().apply(square).apply(triple).apply(2)).isEqualTo(36);
    }

    @Test
    public void applyAndThen() {
        assertThat(FunctionUtils.<Integer, Integer, Integer>andThen().apply(square).apply(triple).apply(2)).isEqualTo(12);
    }

    @Test
    public void applyPartialFirst() {
        Function<Integer, Integer> actual = partialFirst(10, i -> j -> i * j);

        assertThat(actual.apply(5)).isEqualTo(50);
        assertThat(actual.apply(2)).isEqualTo(20);
    }

    @Test
    public void applyPartialSecond() {
        Function<Integer, Integer> actual = partialSecond(10, i -> j -> i * j);

        assertThat(actual.apply(5)).isEqualTo(50);
        assertThat(actual.apply(2)).isEqualTo(20);
    }

    @Test
    public void applyFunc() {
        assertThat(func("toto", 1, 10L, 2.5)).isEqualTo("toto, 1, 10, 2.5");
    }

    @Test
    public void applyCurriedFunc() {
        assertThat(curriedFunc()
                .apply("toto")
                .apply(1)
                .apply(10)
                .apply(2.5)).isEqualTo("toto, 1, 10, 2.5");
    }

}