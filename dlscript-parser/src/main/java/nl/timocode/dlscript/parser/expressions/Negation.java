package nl.timocode.dlscript.parser.expressions;

import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.ParseElement;
import nl.timocode.dlscript.parser.matchers.PatternMatcher;
import nl.timocode.dlscript.parser.matchers.SequencePatternMatcher;
import nl.timocode.dlscript.parser.matchers.TypePatternMatcher;
import nl.timocode.dlscript.parser.matchers.ValuePatternMatcher;
import nl.timocode.dlscript.parser.primitives.StringElement;

import java.util.List;

public class Negation implements Expression {

    private final Expression expression;

    public Negation(Expression expression) {
        this.expression = expression;
    }

    @Override
    public double eval() {
        return -(expression.eval());
    }

    public static class Type implements Parsable<Negation> {

        @Override
        public PatternMatcher patternMatcher() {
            return SequencePatternMatcher.of(
                    new ValuePatternMatcher<>(new StringElement("-")),
                    new TypePatternMatcher(Expression.class)
            );
        }

        @Override
        public Negation create(List<ParseElement> elements) {
            assert elements.size() == 2;
            return new Negation((Expression) elements.get(1).value());
        }
    }
}
