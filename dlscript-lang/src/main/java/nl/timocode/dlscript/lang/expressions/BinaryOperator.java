package nl.timocode.dlscript.lang.expressions;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.timocode.dlscript.parser.Element;
import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.PatternMatcher;
import nl.timocode.dlscript.parser.matchers.SequencePatternMatcher;
import nl.timocode.dlscript.parser.matchers.TypePatternMatcher;
import nl.timocode.dlscript.parser.matchers.ValuePatternMatcher;
import nl.timocode.dlscript.parser.primitives.StringElement;

import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class BinaryOperator<L, R> implements Element {
    private final L left;
    private final R right;

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
