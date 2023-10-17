package nl.timocode.dlscript.lang.types.operations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import nl.timocode.dlscript.lang.expressions.Expression;
import nl.timocode.dlscript.parser.ElementBuilder;
import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.*;
import nl.timocode.dlscript.parser.primitives.CharToken;

@AllArgsConstructor
@Getter
public class BinaryOperation implements Expression {

    private final Expression left;
    private final Expression right;
    private final String operator;

    @Override
    public String getType() {
        return left.getType();
    }

    @Data
    public static class Builder implements ElementBuilder<BinaryOperation> {

        private Expression left;
        private Expression right;
        private String operator;

        @Override
        public BinaryOperation build() {
            return new BinaryOperation(left, right, operator);
        }
    }

    public abstract static class Type implements Parsable<BinaryOperation, BinaryOperation.Builder> {

        protected abstract char getOperator();

        @Override
        public PatternMatcher<BinaryOperation.Builder> patternMatcher() {
            return SequencePatternMatcher.of(
                TypePatternMatcher.of(Expression.class, Builder::setLeft),
                ValuePatternMatcher.of(new CharToken(getOperator())),
                TypePatternMatcher.of(Expression.class, Builder::setRight)
            );
        }

        @Override
        public Builder createBuilder() {
            return new Builder();
        }
    }
}
