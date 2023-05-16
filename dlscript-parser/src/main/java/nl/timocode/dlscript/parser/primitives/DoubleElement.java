package nl.timocode.dlscript.parser.primitives;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.timocode.dlscript.parser.Element;
import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.*;

import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class DoubleElement implements Element {

    private final double value;

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
            long left = ((LongElement) elements.get(0)).getValue();
            long right = ((LongElement) elements.get(2)).getValue();
            double combined = Double.parseDouble(left + "." + right);
            return new DoubleElement(combined);
        }
    }
}
