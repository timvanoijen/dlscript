package nl.timocode.dlscript.parser.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OptionalBiConsumer<T, U> {

    private final BiConsumer<T, U> inner;

    public static <T, U> OptionalBiConsumer<T, U> of(BiConsumer<T, U> biConsumer) {
        Objects.requireNonNull(biConsumer);
        return new OptionalBiConsumer<>(biConsumer);
    }

    public static <T, U> OptionalBiConsumer<T, U> empty() {
        return new OptionalBiConsumer<>(null);
    }

    public Consumer<U> applyFirst(T t) {
        return inner != null ? (U u) -> inner.accept(t, u) : (U u) -> {};
    }

    public Consumer<T> applySecond(U u) {
        return inner != null ? (T t) -> inner.accept(t, u) : (T t) -> {};
    }
}
