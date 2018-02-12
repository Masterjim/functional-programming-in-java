package be.jeremy.functional.programming.exercise.optional;

import be.jeremy.functional.programming.exercise.List;
import org.junit.Test;

import java.util.function.Function;

import static be.jeremy.functional.programming.exercise.List.length;
import static be.jeremy.functional.programming.exercise.optional.Option.none;
import static be.jeremy.functional.programming.exercise.optional.Option.some;
import static org.assertj.core.api.Assertions.assertThat;

public class OptionTest {

    @Test
    public void testMap() {
        assertThat(none().map(Object::toString)).isEqualTo(none());
        assertThat(some(10L).map(Object::toString)).isEqualTo(some("10"));
    }

    @Test
    public void testFlatMap() {
        assertThat(none().flatMap(o -> some(o.toString()))).isEqualTo(none());
        assertThat(some(10L).flatMap(o -> some(o.toString()))).isEqualTo(some("10"));
    }

    @Test
    public void testFilter() {
        assertThat(Option.<Integer>none().filter(i -> i % 2 == 0)).isEqualTo(none());
        assertThat(some(5).filter(i -> i % 2 == 0)).isEqualTo(none());
        assertThat(some(10).filter(i -> i % 2 == 0)).isEqualTo(some(10));
    }

}