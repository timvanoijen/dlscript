package nl.timocode.dlscript.parser.primitives;

import nl.timocode.dlscript.parser.Element;
import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.PatternMatcher;
import nl.timocode.dlscript.parser.matchers.TypePatternMatcher;

import java.util.List;
import java.util.Objects;

public abstract class WrapperElement<T> implements Element {

    private final T value;

    protected WrapperElement(T value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WrapperElement<?> that = (WrapperElement<?>) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public abstract static class Type<T, U extends Element> implements Parsable<U> {

        protected abstract Class<T> getWrappedType();

        protected abstract U create(T object);

        @Override
        public PatternMatcher patternMatcher() {
            return new TypePatternMatcher(getWrappedType());
        }

        @Override
        public U create(List<Element> elements) {
            assert elements.size() == 1;
            //noinspection unchecked
            return create((T) elements.get(0));
        }
    }
}

