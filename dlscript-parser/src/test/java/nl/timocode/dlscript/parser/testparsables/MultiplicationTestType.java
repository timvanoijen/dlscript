package nl.timocode.dlscript.parser.testparsables;

import nl.timocode.dlscript.parser.Element;
import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.PatternMatcher;
import nl.timocode.dlscript.parser.matchers.SequencePatternMatcher;
import nl.timocode.dlscript.parser.matchers.TypePatternMatcher;
import nl.timocode.dlscript.parser.matchers.ValuePatternMatcher;
import nl.timocode.dlscript.parser.primitives.LongElement;
import nl.timocode.dlscript.parser.primitives.StringElement;

import java.util.List;

public class MultiplicationTestType implements Parsable<LongElement> {

    @Override
    public PatternMatcher patternMatcher() {
        return SequencePatternMatcher.of(new TypePatternMatcher(LongElement.class),
                new ValuePatternMatcher<>(new StringElement("*")),
                new TypePatternMatcher(LongElement.class));
    }

    @Override
    public LongElement create(List<Element> elements) {
        return new LongElement(((LongElement)elements.get(0)).getValue()
                * ((LongElement)elements.get(2)).getValue());
    }

    @Override
    public int parsePriority() {
        return 1;
    }
}
