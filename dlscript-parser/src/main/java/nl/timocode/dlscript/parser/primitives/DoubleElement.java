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

        private static final PatternMatcher PATTERNMATCHER = SequencePatternMatcher.of(
                        new TypePatternMatcher(LongElement.class),
                        new ValuePatternMatcher<>(new StringElement(".")),
                        new TypePatternMatcher(LongElement.class));

        @Override
        public PatternMatcher patternMatcher() {
            return PATTERNMATCHER;
        }

        @Override
        public DoubleElement create(Match match) {
            List<Match> innerMatches = ((SequencePatternMatcher.Match) match).getInnerMatches();
            long left = ((LongElement)((TypePatternMatcher.Match)innerMatches.get(0)).getElement()).getValue();
            long right = ((LongElement)((TypePatternMatcher.Match)innerMatches.get(2)).getElement()).getValue();
            double combined = Double.parseDouble(left + "." + right);
            return new DoubleElement(combined);
        }
    }
}
