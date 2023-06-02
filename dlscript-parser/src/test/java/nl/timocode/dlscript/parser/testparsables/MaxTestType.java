package nl.timocode.dlscript.parser.testparsables;

import lombok.Data;
import nl.timocode.dlscript.parser.ElementBuilder;
import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.*;
import nl.timocode.dlscript.parser.primitives.DoubleElement;
import nl.timocode.dlscript.parser.primitives.StringElement;

public class MaxTestType implements Parsable<DoubleElement, MaxTestType.Builder> {

    @Data
    public static class Builder implements ElementBuilder<DoubleElement> {
        private DoubleElement left;
        private DoubleElement right;

        @Override
        public DoubleElement build() {
            return new DoubleElement(Math.max(left.getValue(), right.getValue()));
        }
    }

    @Override
    public PatternMatcher<Builder> patternMatcher() {
        return SequencePatternMatcher.of(
                ValuePatternMatcher.of(new StringElement("max")),
                ValuePatternMatcher.of(new StringElement("(")),
                TypePatternMatcher.of(DoubleElement.class, Builder::setLeft),
                ValuePatternMatcher.of(new StringElement(",")),
                TypePatternMatcher.of(DoubleElement.class, Builder::setRight),
                ValuePatternMatcher.of(new StringElement(")"))
        );
    }

    @Override
    public Builder createBuilder() {
        return new Builder();
    }
}
