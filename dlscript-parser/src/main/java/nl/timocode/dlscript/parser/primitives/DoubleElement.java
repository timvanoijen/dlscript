package nl.timocode.dlscript.parser.primitives;

import lombok.AllArgsConstructor;
import nl.timocode.dlscript.parser.Element;
import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.expressions.Expression;
import nl.timocode.dlscript.parser.matchers.*;

import java.util.List;

@AllArgsConstructor
public class DoubleElement implements Expression {

    private final double value;

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
        public DoubleElement create(List<Element> elements) {
            Long left = ((LongElement) elements.get(0)).getValue();
            Long right = ((LongElement) elements.get(2)).getValue();
            double combined = Double.parseDouble(left.toString() + "." + right.toString());
            return new DoubleElement(combined);
        }
    }
}
