package nl.timocode.dlscript.lang.expressions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import nl.timocode.dlscript.parser.ElementBuilder;
import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.PatternMatcher;
import nl.timocode.dlscript.parser.matchers.TypePatternMatcher;
import nl.timocode.dlscript.parser.primitives.LongToken;

@AllArgsConstructor
@Getter
public class LongLiteral implements Expression {

    private final long value;

    @Data
    public static class Builder implements ElementBuilder<LongLiteral> {

        private LongToken element;

        @Override
        public LongLiteral build() {
            return new LongLiteral(element.getValue());
        }
    }

    public static class Type implements Parsable<LongLiteral, LongLiteral.Builder> {

        @Override
        public PatternMatcher<Builder> patternMatcher() {
            return TypePatternMatcher.of(LongToken.class, Builder::setElement);
        }

        @Override
        public Builder createBuilder() {
            return new Builder();
        }
    }
        @Override
    public String getType() {
        return "long"; // TODO: replace by future static const of long primitive type
    }
}
