package nl.timocode.dlscript.lang.expressions;

public class Multiplication extends BinaryExpressionOperator implements Expression {

    public Multiplication(Expression left, Expression right) {
        super(left, right, (a, b) -> (a * b));
    }

    public static class Type extends BinaryExpressionOperator.Type<Multiplication> {

        private static final int PARSE_PRIORITY = 4;

        @Override
        public int parsePriority() {
            return PARSE_PRIORITY;
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
