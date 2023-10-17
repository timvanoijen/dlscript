package nl.timocode.dlscript.lang.expressions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import nl.timocode.dlscript.parser.ElementBuilder;
import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.PatternMatcher;
import nl.timocode.dlscript.parser.matchers.TypePatternMatcher;
import nl.timocode.dlscript.parser.primitives.DoubleToken;

@AllArgsConstructor
@Getter
public class DoubleLiteral implements Expression {

    private final double value;

    @Data
    public static class Builder implements ElementBuilder<DoubleLiteral> {

        private DoubleToken element;

        @Override
        public DoubleLiteral build() {
            return new DoubleLiteral(element.getValue());
        }
    }

    public static class Type implements Parsable<DoubleLiteral, DoubleLiteral.Builder> {

        @Override
        public PatternMatcher<Builder> patternMatcher() {
            return TypePatternMatcher.of(DoubleToken.class, Builder::setElement);
        }

        @Override
        public Builder createBuilder() {
            return new Builder();
        }
    }
        @Override
    public String getType() {
        return "double"; // TODO: replace by future static const of double primitive type
    }
}
