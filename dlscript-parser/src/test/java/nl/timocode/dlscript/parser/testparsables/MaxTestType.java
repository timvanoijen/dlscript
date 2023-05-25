package nl.timocode.dlscript.parser.testparsables;

import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.*;
import nl.timocode.dlscript.parser.primitives.DoubleElement;
import nl.timocode.dlscript.parser.primitives.StringElement;

import java.util.List;

public class MaxTestType implements Parsable<DoubleElement> {

    @Override
    public PatternMatcher patternMatcher() {
        return SequencePatternMatcher.of(
                new ValuePatternMatcher<>(new StringElement("max")),
                new ValuePatternMatcher<>(new StringElement("(")),
                new TypePatternMatcher(DoubleElement.class),
                new ValuePatternMatcher<>(new StringElement(",")),
                new TypePatternMatcher(DoubleElement.class),
                new ValuePatternMatcher<>(new StringElement(")"))
        );
    }

    @Override
    public DoubleElement create(Match match) {
        List<Match> innerMatches = ((SequencePatternMatcher.Match) match).getInnerMatches();
        double left = ((DoubleElement)((TypePatternMatcher.Match) innerMatches.get(2)).getElement()).getValue();
        double right = ((DoubleElement)((TypePatternMatcher.Match) innerMatches.get(4)).getElement()).getValue();
        return new DoubleElement(Math.max(left, right));
    }
}
