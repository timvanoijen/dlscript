package nl.timocode.dlscript.parser.expressions;

public class Sum extends BinaryExpressionOperator implements Expression {

    public Sum(Expression left, Expression right) {
        super(left, right, Double::sum);
    }

    public static class Type extends BinaryExpressionOperator.Type<Sum> {

        private static final int PARSE_PRIORITY = 2;

        @Override
        public int parsePriority() {
            return PARSE_PRIORITY;
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
