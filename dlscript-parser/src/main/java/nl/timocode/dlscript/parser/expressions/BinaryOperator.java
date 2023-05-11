package nl.timocode.dlscript.parser.expressions;

import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.ParseElement;
import nl.timocode.dlscript.parser.matchers.PatternMatcher;
import nl.timocode.dlscript.parser.matchers.SequencePatternMatcher;
import nl.timocode.dlscript.parser.matchers.TypePatternMatcher;
import nl.timocode.dlscript.parser.matchers.ValuePatternMatcher;

import java.util.List;

public class BinaryOperator<L, R> {
    private final L left;
    private final R right;

    public BinaryOperator(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

    public abstract static class Type<L, R, U extends BinaryOperator<L, R>> implements Parsable<U> {

        private final PatternMatcher patternMatcher = SequencePatternMatcher.of(
                new TypePatternMatcher(leftType()),
                new ValuePatternMatcher<>(operator()),
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
        public U create(List<ParseElement<?>> elements) {
            assert elements.size() == 3;
            //noinspection unchecked
            return create((L) elements.get(0).value(), (R) elements.get(2).value());
        }
    }
}
