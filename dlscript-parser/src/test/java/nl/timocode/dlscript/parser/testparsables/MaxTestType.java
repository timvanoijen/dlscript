package nl.timocode.dlscript.parser.testparsables;

import nl.timocode.dlscript.parser.Element;
import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.PatternMatcher;
import nl.timocode.dlscript.parser.matchers.SequencePatternMatcher;
import nl.timocode.dlscript.parser.matchers.TypePatternMatcher;
import nl.timocode.dlscript.parser.matchers.ValuePatternMatcher;
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
    public DoubleElement create(List<Element> elements) {
        return new DoubleElement(Math.max(
                ((DoubleElement)elements.get(2)).getValue(),
                ((DoubleElement)elements.get(4)).getValue()));
    }
}
