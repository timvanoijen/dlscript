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

    public static class ParsableType implements Parsable<DoubleElement> {

        private static final PatternMatcher PATTERNMATCHER = SequencePattern.Matcher.of(
                        TypePattern.Matcher.of(LongElement.class),
                        ValuePattern.Matcher.of(new StringElement(".")),
                        TypePattern.Matcher.of(LongElement.class));

        @Override
        public PatternMatcher patternMatcher() {
            return PATTERNMATCHER;
        }

        @Override
        public DoubleElement create(Pattern pattern) {
            List<Pattern> innerPatterns = ((SequencePattern) pattern).getInnerPatterns();
            long left = ((LongElement)((TypePattern) innerPatterns.get(0)).getElement()).getValue();
            long right = ((LongElement)((TypePattern) innerPatterns.get(2)).getElement()).getValue();
            double combined = Double.parseDouble(left + "." + right);
            return new DoubleElement(combined);
        }
    }
}
