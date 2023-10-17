package nl.timocode.dlscript.parser.testparsables;

import lombok.Data;
import nl.timocode.dlscript.parser.ElementBuilder;
import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.*;
import nl.timocode.dlscript.parser.primitives.CharToken;
import nl.timocode.dlscript.parser.primitives.LongToken;

public class MultiplicationTestType implements Parsable<LongToken, MultiplicationTestType.Builder> {

    @Data
    public static class Builder implements ElementBuilder<LongToken> {
        private LongToken left;
        private LongToken right;

        @Override
        public LongToken build() {
            return new LongToken(left.getValue() * right.getValue());
        }
    }

    @Override
    public PatternMatcher<Builder> patternMatcher() {
        return SequencePatternMatcher.of(
                TypePatternMatcher.of(LongToken.class, Builder::setLeft),
                ValuePatternMatcher.of(new CharToken('*')),
                TypePatternMatcher.of(LongToken.class, Builder::setRight));
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
