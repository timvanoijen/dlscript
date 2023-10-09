package nl.timocode.dlscript.parser.primitives;

import lombok.*;
import nl.timocode.dlscript.parser.Element;
import nl.timocode.dlscript.parser.ElementBuilder;
import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.*;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class DoubleElement implements Element {

    private final double value;

    public static class Type implements Parsable<DoubleElement, Type.Builder> {

        private static final PatternMatcher<Builder> PATTERN_MATCHER = SequencePatternMatcher.of(
                TypePatternMatcher.of(LongElement.class, Builder::setLeft),
                ValuePatternMatcher.of(new StringElement(".")),
                TypePatternMatcher.of(LongElement.class, Builder::setRight));

        @Override
        public PatternMatcher<Builder> patternMatcher() {
            return PATTERN_MATCHER;
        }

        @Override
        public Builder createBuilder() {
            return new Builder();
        }

        @Data
        public static class Builder implements ElementBuilder<DoubleElement> {
            private LongElement left;
            private LongElement right;

            @Override
            public DoubleElement build() {
                double value = Double.parseDouble(left.getValue() + "." + right.getValue());
                return new DoubleElement(value);
            }
        }

        @Override
        public int parsePriority() {
            return Integer.MAX_VALUE;
        }
    }
}
