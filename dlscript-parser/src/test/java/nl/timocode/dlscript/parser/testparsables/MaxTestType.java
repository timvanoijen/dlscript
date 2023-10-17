package nl.timocode.dlscript.parser.testparsables;

import lombok.Data;
import nl.timocode.dlscript.parser.ElementBuilder;
import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.*;
import nl.timocode.dlscript.parser.primitives.CharToken;
import nl.timocode.dlscript.parser.primitives.DoubleToken;
import nl.timocode.dlscript.parser.primitives.IdentifierToken;

public class MaxTestType implements Parsable<DoubleToken, MaxTestType.Builder> {

    @Data
    public static class Builder implements ElementBuilder<DoubleToken> {
        private DoubleToken left;
        private DoubleToken right;

        @Override
        public DoubleToken build() {
            return new DoubleToken(Math.max(left.getValue(), right.getValue()));
        }
    }

    @Override
    public PatternMatcher<Builder> patternMatcher() {
        return SequencePatternMatcher.of(
                ValuePatternMatcher.of(new IdentifierToken("max")),
                ValuePatternMatcher.of(new CharToken('(')),
                TypePatternMatcher.of(DoubleToken.class, Builder::setLeft),
                ValuePatternMatcher.of(new CharToken(',')),
                TypePatternMatcher.of(DoubleToken.class, Builder::setRight),
                ValuePatternMatcher.of(new CharToken(')'))
        );
    }

    @Override
    public Builder createBuilder() {
        return new Builder();
    }
}
