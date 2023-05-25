package nl.timocode.dlscript.parser.testparsables;

import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.*;
import nl.timocode.dlscript.parser.primitives.LongElement;
import nl.timocode.dlscript.parser.primitives.StringElement;

import java.util.List;

public class SumTestType implements Parsable<LongElement> {

    @Override
    public PatternMatcher patternMatcher() {
        return SequencePatternMatcher.of(new TypePatternMatcher(LongElement.class),
                new ValuePatternMatcher<>(new StringElement("+")),
                new TypePatternMatcher(LongElement.class));
    }

    @Override
    public LongElement create(Match match) {
        List<Match> innerMatches = ((SequencePatternMatcher.Match) match).getInnerMatches();
        long left = ((LongElement)((TypePatternMatcher.Match) innerMatches.get(0)).getElement()).getValue();
        long right = ((LongElement)((TypePatternMatcher.Match) innerMatches.get(2)).getElement()).getValue();
        return new LongElement(left + right);
    }

    @Override
    public int parsePriority() {
        return 0;
    }
}
