package nl.timocode.dlscript.parser.testparsables;

import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.*;
import nl.timocode.dlscript.parser.primitives.DoubleElement;
import nl.timocode.dlscript.parser.primitives.StringElement;

import java.util.List;

public class MaxTestType implements Parsable<DoubleElement> {

    @Override
    public PatternMatcher patternMatcher() {
        return SequencePattern.Matcher.of(
                ValuePattern.Matcher.of(new StringElement("max")),
                ValuePattern.Matcher.of(new StringElement("(")),
                TypePattern.Matcher.of(DoubleElement.class),
                ValuePattern.Matcher.of(new StringElement(",")),
                TypePattern.Matcher.of(DoubleElement.class),
                ValuePattern.Matcher.of(new StringElement(")"))
        );
    }

    @Override
    public DoubleElement create(Pattern pattern) {
        List<Pattern> innerPatterns = ((SequencePattern) pattern).getInnerPatterns();
        double left = ((DoubleElement)((TypePattern) innerPatterns.get(2)).getElement()).getValue();
        double right = ((DoubleElement)((TypePattern) innerPatterns.get(4)).getElement()).getValue();
        return new DoubleElement(Math.max(left, right));
    }
}
