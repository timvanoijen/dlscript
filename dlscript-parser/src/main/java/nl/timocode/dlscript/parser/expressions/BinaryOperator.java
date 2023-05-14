package nl.timocode.dlscript.parser.expressions;

import nl.timocode.dlscript.parser.Element;
import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.PatternMatcher;
import nl.timocode.dlscript.parser.matchers.SequencePatternMatcher;
import nl.timocode.dlscript.parser.matchers.TypePatternMatcher;
import nl.timocode.dlscript.parser.matchers.ValuePatternMatcher;
import nl.timocode.dlscript.parser.primitives.StringElement;

import java.util.List;
import java.util.Objects;

public class BinaryOperator<L, R> implements Element {
    private final L left;
    private final R right;

    public BinaryOperator(L left, R right) {
        Objects.requireNonNull(left);
        Objects.requireNonNull(right);
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinaryOperator<?, ?> that = (BinaryOperator<?, ?>) o;
        return left.equals(that.left) && right.equals(that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    public abstract static class Type<L, R, U extends BinaryOperator<L, R>> implements Parsable<U> {

        private final PatternMatcher patternMatcher = SequencePatternMatcher.of(
                new TypePatternMatcher(leftType()),
                new ValuePatternMatcher<>(new StringElement(operator())),
                new TypePatternMatcher(rightType()));

        protected abstract Class<L> leftType();

        protected abstract Class<R> rightType();

        protected abstract String operator();

        protected abstract U create(L left, R right);

        @Override
        public PatternMatcher patternMatcher() {
            return patternMatcher;
        }

        @Override
        public U create(List<Element> elements) {
            assert elements.size() == 3;
            //noinspection unchecked
            return create((L) elements.get(0), (R) elements.get(2));
        }
    }
}
