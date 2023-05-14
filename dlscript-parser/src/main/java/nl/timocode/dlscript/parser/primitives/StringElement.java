package nl.timocode.dlscript.parser.primitives;

public class StringElement extends WrapperElement<String> {

    public StringElement(String value) {
        super(value);
    }

    public static class Type extends WrapperElement.Type<String, StringElement> {

        @Override
        protected Class<String> getWrappedType() {
            return String.class;
        }

        @Override
        protected StringElement create(String object) {
            return new StringElement(object);
        }
    }


}
