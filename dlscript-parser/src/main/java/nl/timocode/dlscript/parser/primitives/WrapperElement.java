package nl.timocode.dlscript.parser.primitives;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import nl.timocode.dlscript.parser.Element;
import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.PatternMatcher;
import nl.timocode.dlscript.parser.matchers.TypePatternMatcher;

import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode
public abstract class WrapperElement<T> implements Element {

    private final T value;

    public T getValue() {
        return value;
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

