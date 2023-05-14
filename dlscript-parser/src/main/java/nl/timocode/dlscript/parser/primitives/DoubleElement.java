package nl.timocode.dlscript.parser.primitives;

import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.ParseElement;
import nl.timocode.dlscript.parser.expressions.Expression;
import nl.timocode.dlscript.parser.matchers.*;

import java.util.List;

public class DoubleElement implements Expression {

    private final double value;

    public DoubleElement(double value) {
        this.value = value;
    }

    public double eval() {
        return value;
    }

    public static class Type implements Parsable<DoubleElement> {

        private static final PatternMatcher PATTERNMATCHER = SequencePatternMatcher.of(
                        new TypePatternMatcher(LongElement.class),
                        new ValuePatternMatcher<>(new StringElement(".")),
                        new TypePatternMatcher(LongElement.class));

        @Override
        public PatternMatcher patternMatcher() {
            return PATTERNMATCHER;
        }

        @Override
        public DoubleElement create(List<ParseElement> elements) {
            Long left = ((LongElement) elements.get(0).value()).getValue();
            Long right = ((LongElement) elements.get(2).value()).getValue();
            double combined = Double.parseDouble(left.toString() + "." + right.toString());
            return new DoubleElement(combined);
        }
    }
}
