package nl.timocode.dlscript.parser.expressions;

import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.ParseElement;
import nl.timocode.dlscript.parser.matchers.PatternMatcher;
import nl.timocode.dlscript.parser.matchers.SequencePatternMatcher;
import nl.timocode.dlscript.parser.matchers.TypePatternMatcher;
import nl.timocode.dlscript.parser.matchers.ValuePatternMatcher;

import java.util.List;

public class SubExpression implements Expression {

    private final Expression expression;

    public SubExpression(Expression expression) {
        this.expression = expression;
    }

    public double eval() {
        return expression.eval();
    }

    public static class Type implements Parsable<SubExpression> {

        private static final PatternMatcher PATTERNMATCHER = SequencePatternMatcher.of(
                new ValuePatternMatcher<>("("),
                new TypePatternMatcher(Expression.class),
                new ValuePatternMatcher<>(")"));

        @Override
        public PatternMatcher patternMatcher() {
            return PATTERNMATCHER;
        }

        @Override
        public SubExpression create(List<ParseElement> elements) {
            assert elements.size() == 3;
            return new SubExpression((Expression)elements.get(1).value());
        }
    }
}
