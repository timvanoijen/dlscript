package nl.timocode.dlscript.lang.types;

import lombok.*;
import nl.timocode.dlscript.parser.Element;
import nl.timocode.dlscript.parser.ElementBuilder;
import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.PatternMatcher;
import nl.timocode.dlscript.parser.matchers.SequencePatternMatcher;
import nl.timocode.dlscript.parser.matchers.TypePatternMatcher;
import nl.timocode.dlscript.parser.matchers.ValuePatternMatcher;
import nl.timocode.dlscript.parser.primitives.CharToken;
import nl.timocode.dlscript.parser.primitives.IdentifierToken;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class Variable implements Element {
    private final String type;
    private final String name;

    @Data
    public static class Builder implements ElementBuilder<Variable> {

        private IdentifierToken type;
        private IdentifierToken name;

        @Override
        public Variable build() {
            return new Variable(type.getValue(), name.getValue());
        }
    }

    public static class Type implements Parsable<Variable, Builder> {

        @Override
        public PatternMatcher<Builder> patternMatcher() {
            return SequencePatternMatcher.of(
                    ValuePatternMatcher.of(new IdentifierToken("var")),
                    TypePatternMatcher.of(IdentifierToken.class, Builder::setName),
                    ValuePatternMatcher.of(new CharToken(':')),
                    TypePatternMatcher.of(IdentifierToken.class, Builder::setType)
            );
        }

        @Override
        public Builder createBuilder() {
            return new Builder();
        }
    }
}