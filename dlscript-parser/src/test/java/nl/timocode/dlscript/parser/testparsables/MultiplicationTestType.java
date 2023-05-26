package nl.timocode.dlscript.parser.testparsables;

import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.*;
import nl.timocode.dlscript.parser.primitives.LongElement;
import nl.timocode.dlscript.parser.primitives.StringElement;

import java.util.List;

public class MultiplicationTestType implements Parsable<LongElement> {

    @Override
    public PatternMatcher patternMatcher() {
        return SequencePattern.Matcher.of(
                TypePattern.Matcher.of(LongElement.class),
                ValuePattern.Matcher.of(new StringElement("*")),
                TypePattern.Matcher.of(LongElement.class));
    }

    @Override
    public LongElement create(Pattern pattern) {
        List<Pattern> innerPatterns = ((SequencePattern) pattern).getInnerPatterns();
        long left = ((LongElement)((TypePattern) innerPatterns.get(0)).getElement()).getValue();
        long right = ((LongElement)((TypePattern) innerPatterns.get(2)).getElement()).getValue();
        return new LongElement(left * right);
    }

    @Override
    public int parsePriority() {
        return 1;
    }
}
