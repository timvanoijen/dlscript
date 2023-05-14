package nl.timocode.dlscript.parser.matchers;

import nl.timocode.dlscript.parser.Element;

import java.util.Objects;

public final class ValuePatternMatcher<T extends Element> extends SingleElementPatternMatcher {

    private final T value;

    public ValuePatternMatcher(T value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    @Override
    protected boolean matches(Element element) {
        return Objects.equals(element, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValuePatternMatcher<?> that = (ValuePatternMatcher<?>) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
