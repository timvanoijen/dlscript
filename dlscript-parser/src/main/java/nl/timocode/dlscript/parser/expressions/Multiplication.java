package nl.timocode.dlscript.parser.expressions;

public class Multiplication extends BinaryOperator<Expression, Expression> implements Expression {

    public Multiplication(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public double eval() {
        return getLeft().eval() * getRight().eval();
    }

    public static class Type extends BinaryOperator.Type<Expression, Expression, Multiplication> {

        private static final int PARSE_PRIORITY = 2;

        @Override
        public int parsePriority() {
            return PARSE_PRIORITY;
        }

        @Override
        protected Class<Expression> leftType() {
            return Expression.class;
        }

        @Override
        protected Class<Expression> rightType() {
            return Expression.class;
        }

        @Override
        protected String operator() {
            return "*";
        }

        @Override
        protected Multiplication create(Expression left, Expression right) {
            return new Multiplication(left, right);
        }
    }
}
