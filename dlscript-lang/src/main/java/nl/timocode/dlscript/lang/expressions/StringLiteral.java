package nl.timocode.dlscript.lang.expressions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import nl.timocode.dlscript.parser.ElementBuilder;
import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.PatternMatcher;
import nl.timocode.dlscript.parser.matchers.TypePatternMatcher;
import nl.timocode.dlscript.parser.primitives.StringLiteralToken;

@AllArgsConstructor
@Getter
public class StringLiteral implements Expression {

    private final String value;

    @Data
    public static class Builder implements ElementBuilder<StringLiteral> {

        private StringLiteralToken element;

        @Override
        public StringLiteral build() {
            return new StringLiteral(element.getValue());
        }
    }

    public static class Type implements Parsable<StringLiteral, StringLiteral.Builder> {

        @Override
        public PatternMatcher<Builder> patternMatcher() {
            return TypePatternMatcher.of(StringLiteralToken.class, Builder::setElement);
        }

        @Override
        public Builder createBuilder() {
            return new Builder();
        }
    }
        @Override
    public String getType() {
        return "string"; // TODO: replace by future static const of long primitive type
    }
}
