package nl.timocode.dlscript.parser.primitives;

import nl.timocode.dlscript.parser.expressions.Expression;

public class LongElement extends WrapperElement<Long> implements Expression {

    public LongElement(long value) {
        super(value);
    }

    public double eval() {
        return getValue();
    }

    public static class Type extends WrapperElement.Type<Long, LongElement> {

        @Override
        protected Class<Long> getWrappedType() {
            return Long.class;
        }

        @Override
        protected LongElement create(Long object) {
            return new LongElement(object);
        }
    }
}
