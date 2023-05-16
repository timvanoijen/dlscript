package nl.timocode.dlscript.lang.expressions;

public class Subtraction extends BinaryExpressionOperator implements Expression {

    public Subtraction(Expression left, Expression right) {
        super(left, right, (a, b) -> (a - b));
    }

    public static class Type extends BinaryExpressionOperator.Type<Subtraction> {

        private static final int PARSE_PRIORITY = 1;

        @Override
        public int parsePriority() {
            return PARSE_PRIORITY;
        }

        @Override
        protected String operator() {
            return "-";
        }

        @Override
        protected Subtraction create(Expression left, Expression right) {
            return new Subtraction(left, right);
        }
    }
}
