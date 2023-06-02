package nl.timocode.dlscript.parser.testparsables;

import lombok.Data;
import nl.timocode.dlscript.parser.ElementBuilder;
import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.*;
import nl.timocode.dlscript.parser.primitives.LongElement;
import nl.timocode.dlscript.parser.primitives.StringElement;

public class MultiplicationTestType implements Parsable<LongElement, MultiplicationTestType.Builder> {

    @Data
    public static class Builder implements ElementBuilder<LongElement> {
        private LongElement left;
        private LongElement right;

        @Override
        public LongElement build() {
            return new LongElement(left.getValue() * right.getValue());
        }
    }

    @Override
    public PatternMatcher<Builder> patternMatcher() {
        return SequencePatternMatcher.of(
                TypePatternMatcher.of(LongElement.class, Builder::setLeft),
                ValuePatternMatcher.of(new StringElement("*")),
                TypePatternMatcher.of(LongElement.class, Builder::setRight));
    }

    @Override
    public Builder createBuilder() {
        return new Builder();
    }

    @Override
    public int parsePriority() {
        return 1;
    }
}
