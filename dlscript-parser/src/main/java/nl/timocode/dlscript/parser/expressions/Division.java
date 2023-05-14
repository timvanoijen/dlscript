package nl.timocode.dlscript.parser.expressions;

public class Division extends BinaryExpressionOperator implements Expression {

    public Division(Expression left, Expression right) {
        super(left, right, (a, b) -> (a / b));
    }

    public static class Type extends BinaryExpressionOperator.Type<Division> {

        private static final int PARSE_PRIORITY = 3;

        @Override
        public int parsePriority() {
            return PARSE_PRIORITY;
        }

        @Override
        protected String operator() {
            return "/";
        }

        @Override
        protected Division create(Expression left, Expression right) {
            return new Division(left, right);
        }
    }
}
