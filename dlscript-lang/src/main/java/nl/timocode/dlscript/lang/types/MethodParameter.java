package nl.timocode.dlscript.lang.types;

import lombok.*;
import nl.timocode.dlscript.lang.expressions.Expression;
import nl.timocode.dlscript.parser.Element;
import nl.timocode.dlscript.parser.ElementBuilder;
import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.*;
import nl.timocode.dlscript.parser.primitives.CharToken;
import nl.timocode.dlscript.parser.primitives.IdentifierToken;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class MethodParameter implements Element {
    private final String type;
    private final String name;
    private final Expression defaultValue;

    @Data
    public static class Builder implements ElementBuilder<MethodParameter> {

        private IdentifierToken type;
        private IdentifierToken name;
        private Expression defaultValue;

        @Override
        public MethodParameter build() {
            return new MethodParameter(type.getValue(), name.getValue(), defaultValue);
        }
    }

    public static class Type implements Parsable<MethodParameter, Builder> {

        @Override
        public PatternMatcher<Builder> patternMatcher() {
            return SequencePatternMatcher.of(
                    TypePatternMatcher.of(IdentifierToken.class, Builder::setName),
                    ValuePatternMatcher.of(new CharToken(':')),
                    TypePatternMatcher.of(IdentifierToken.class, Builder::setType),
                    OptionalPatternMatcher.of(
                            SequencePatternMatcher.of(
                                    ValuePatternMatcher.of(new CharToken('=')),
                                    TypePatternMatcher.of(Expression.class, Builder::setDefaultValue)
                            )
                    )
            );
        }

        @Override
        public Builder createBuilder() {
            return new Builder();
        }
    }
}