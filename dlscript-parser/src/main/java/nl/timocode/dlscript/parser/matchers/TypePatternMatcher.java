package nl.timocode.dlscript.parser.matchers;

import nl.timocode.dlscript.parser.ParseElement;

import java.util.Objects;

public final class TypePatternMatcher extends SingleElementPatternMatcher {

    private final Class<?> type;

    public TypePatternMatcher(Class<?> type) {
        Objects.requireNonNull(type);
        this.type = type;
    }

    @Override
    protected boolean matches(ParseElement element) {
        return type.isAssignableFrom(element.type());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypePatternMatcher that = (TypePatternMatcher) o;
        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
