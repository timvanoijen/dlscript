package nl.timocode.dlscript.lang.types;

import lombok.*;
import nl.timocode.dlscript.lang.statements.Statement;
import nl.timocode.dlscript.parser.Element;
import nl.timocode.dlscript.parser.ElementBuilder;
import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.*;
import nl.timocode.dlscript.parser.primitives.CharToken;
import nl.timocode.dlscript.parser.primitives.IdentifierToken;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class Method implements Element {
    private final String returnType;
    private final String name;
    private final List<MethodParameter> parameters;
    private final List<Statement> statements;

    @Data
    public static class Builder implements ElementBuilder<Method> {

        private IdentifierToken returnType;
        private IdentifierToken name;
        private final List<MethodParameter> parameters = new ArrayList<>();
        private final List<Statement> statements = new ArrayList<>();

        public void addParameter(MethodParameter methodParameter) {
            parameters.add(methodParameter);
        }

        public void addStatement(Statement statement) {
            statements.add(statement);
        }

        @Override
        public Method build() {
            return new Method(returnType != null ? returnType.getValue() : null,
                    name.getValue(), List.copyOf(parameters), List.copyOf(statements));
        }
    }

    public static class Type implements Parsable<Method, Builder> {

        @Override
        public PatternMatcher<Builder> patternMatcher() {
            return SequencePatternMatcher.of(
                    ValuePatternMatcher.of(new IdentifierToken("fun")),
                    TypePatternMatcher.of(IdentifierToken.class, Builder::setName),
                    ValuePatternMatcher.of(new CharToken('(')),
                    OptionalPatternMatcher.of(
                            RepeatingPatternMatcher.of(
                                    TypePatternMatcher.of(MethodParameter.class, Builder::addParameter)
                            ).withDelimiter(',')
                    ),
                    ValuePatternMatcher.of(new CharToken(')')),
                    OptionalPatternMatcher.of(
                            SequencePatternMatcher.of(
                                    ValuePatternMatcher.of(new CharToken(':')),
                                    TypePatternMatcher.of(IdentifierToken.class, Builder::setReturnType)
                            )
                    ),
                    ValuePatternMatcher.of(new CharToken('{')),
                    OptionalPatternMatcher.of(
                            RepeatingPatternMatcher.of(
                                    TypePatternMatcher.of(Statement.class, Builder::addStatement)
                            )
                    ),
                    ValuePatternMatcher.of(new CharToken('}'))
            );
        }

        @Override
        public Builder createBuilder() {
            return new Builder();
        }
    }
}
