package nl.timocode.dlscript.parser.expressions;

import java.util.function.BiFunction;

public abstract class BinaryExpressionOperator extends BinaryOperator<Expression, Expression> implements Expression {

    private final BiFunction<Double, Double, Double> operation;

    protected BinaryExpressionOperator(Expression left, Expression right, BiFunction<Double, Double, Double> operation) {
        super(left, right);
        this.operation = operation;
    }

    public double eval(double left, double right) {
        return operation.apply(left, right);
    }

    @Override
    public double eval() {
        return eval(getLeft().eval(), getRight().eval());
    }

    public abstract static class Type<T extends BinaryOperator<Expression, Expression>>
            extends BinaryOperator.Type<Expression, Expression, T> {

        @Override
        protected Class<Expression> leftType() {
            return Expression.class;
        }

        @Override
        protected Class<Expression> rightType() {
            return Expression.class;
        }
    }
}
