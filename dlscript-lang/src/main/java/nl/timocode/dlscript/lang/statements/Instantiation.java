package nl.timocode.dlscript.lang.statements;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import nl.timocode.dlscript.lang.expressions.Expression;
import nl.timocode.dlscript.parser.ElementBuilder;
import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.*;
import nl.timocode.dlscript.parser.primitives.CharToken;
import nl.timocode.dlscript.parser.primitives.IdentifierToken;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public class Instantiation implements Expression {

    private final String type;
    private final Map<String, Expression> fieldValues;

    @Data
    public static class Builder implements ElementBuilder<Instantiation> {

        private IdentifierToken type;
        private final Map<String, Expression> fieldValues = new HashMap<>();

        public void addFieldAssignment(Assignment assignment) {
            fieldValues.put(assignment.getName(), assignment.getExpression());
        }

        @Override
        public Instantiation build() {
            return new Instantiation(type.getValue(), fieldValues);
        }
    }

    public static class Type implements Parsable<Instantiation, Builder> {

        @Override
        public PatternMatcher<Builder> patternMatcher() {
            return SequencePatternMatcher.of(
                TypePatternMatcher.of(IdentifierToken.class, Builder::setType),
                ValuePatternMatcher.of(new CharToken('(')),
                OptionalPatternMatcher.of(
                    RepeatingPatternMatcher.of(
                        SequencePatternMatcher.of(
                            TypePatternMatcher.of(Assignment.NoInstantiation.class, Builder::addFieldAssignment)
                        )
                    ).withDelimiter(',')
                ),
                ValuePatternMatcher.of(new CharToken(')'))
            );
        }

        @Override
        public Builder createBuilder() {
            return new Builder();
        }
    }
}

