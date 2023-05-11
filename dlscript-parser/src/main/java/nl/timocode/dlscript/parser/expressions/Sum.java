package nl.timocode.dlscript.parser.expressions;

public class Sum extends BinaryOperator<Expression, Expression> implements Expression {

    public Sum(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public double eval() {
        return getLeft().eval() + getRight().eval();
    }

    public static class Type extends BinaryOperator.Type<Expression, Expression, Sum> {

        private static final int PARSE_PRIORITY = 1;

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
            return "+";
        }

        @Override
        protected Sum create(Expression left, Expression right) {
            return new Sum(left, right);
        }
    }
}
