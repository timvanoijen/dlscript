package nl.timocode.dlscript.parser.expressions;

import lombok.AllArgsConstructor;
import nl.timocode.dlscript.parser.Element;
import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.PatternMatcher;
import nl.timocode.dlscript.parser.matchers.SequencePatternMatcher;
import nl.timocode.dlscript.parser.matchers.TypePatternMatcher;
import nl.timocode.dlscript.parser.matchers.ValuePatternMatcher;
import nl.timocode.dlscript.parser.primitives.StringElement;

import java.util.List;

@AllArgsConstructor
public class SubExpression implements Expression {

    private final Expression expression;

    public double eval() {
        return expression.eval();
    }

    public static class Type implements Parsable<SubExpression> {

        private static final PatternMatcher PATTERNMATCHER = SequencePatternMatcher.of(
                new ValuePatternMatcher<>(new StringElement("(")),
                new TypePatternMatcher(Expression.class),
                new ValuePatternMatcher<>(new StringElement(")")));

        @Override
        public PatternMatcher patternMatcher() {
            return PATTERNMATCHER;
        }

        @Override
        public SubExpression create(List<Element> elements) {
            assert elements.size() == 3;
            return new SubExpression((Expression)elements.get(1));
        }
    }
}
