package nl.timocode.dlscript.lang.statements;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import nl.timocode.dlscript.lang.expressions.Expression;
import nl.timocode.dlscript.lang.types.Variable;
import nl.timocode.dlscript.parser.ElementBuilder;
import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.*;
import nl.timocode.dlscript.parser.primitives.StringElement;

@AllArgsConstructor
@Getter
public class Assignment implements Statement, Expression {

    private final boolean isInitialisation;
    private final String type;
    private final String name;
    private final Expression expression;

    public static class NoInstantiation extends Assignment {
        public NoInstantiation(String name, Expression expression) {
            super(false, null, name, expression);
        }
    }

    public static class Instantiation extends Assignment {
        public Instantiation(String type, String name, Expression expression) {
            super(true, type, name, expression);
        }
    }

    @Data
    public static class Builder implements ElementBuilder<Assignment> {

        private Variable variable;
        private StringElement type;
        private StringElement name;
        private Expression expression;

        @Override
        public Assignment build() {
            if (variable != null) {
                return new Assignment.Instantiation(variable.getType(), variable.getName(), expression);
            } else {
                return new Assignment.NoInstantiation(name.getValue(), expression);
            }
        }
    }

    public static class Type implements Parsable<Assignment, Builder> {

        @Override
        public PatternMatcher<Builder> patternMatcher() {
            return AnyPatternMatcher.of(
                    SequencePatternMatcher.of(
                            TypePatternMatcher.of(Variable.class, Builder::setVariable),
                            ValuePatternMatcher.of(new StringElement("=")),
                            TypePatternMatcher.of(Expression.class, Builder::setExpression)
                    ),
                    SequencePatternMatcher.of(
                            TypePatternMatcher.of(StringElement.class, Builder::setName),
                            ValuePatternMatcher.of(new StringElement("=")),
                            TypePatternMatcher.of(Expression.class, Builder::setExpression)
                    )
            );
        }

        @Override
        public Builder createBuilder() {
            return new Builder();
        }
    }
}