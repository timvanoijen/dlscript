package nl.timocode.dlscript.parser.expressions;

import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.ParseElement;
import nl.timocode.dlscript.parser.matchers.PatternMatcher;
import nl.timocode.dlscript.parser.matchers.TypePatternMatcher;

import java.util.List;

public class Constant implements Expression {

    private final Double value;

    public Constant(double value) {
        this.value = value;
    }

    public double eval() {
        return value;
    }

    public static class Type implements Parsable<Constant> {

        private static final PatternMatcher PATTERNMATCHER = new TypePatternMatcher(Number.class);

        @Override
        public PatternMatcher patternMatcher() {
            return PATTERNMATCHER;
        }

        @Override
        public Constant create(List<ParseElement> elements) {
            assert elements.size() == 1;
            return new Constant(((Number)elements.get(0).value()).doubleValue());
        }
    }
}
