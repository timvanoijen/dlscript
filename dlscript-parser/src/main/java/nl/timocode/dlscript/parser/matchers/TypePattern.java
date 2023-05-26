package nl.timocode.dlscript.parser.matchers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import nl.timocode.dlscript.parser.Element;

public class TypePattern extends SingleElementPattern {

    public TypePattern(Element element) {
        super(element);
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @EqualsAndHashCode(callSuper = false)
    public static final class Matcher extends SingleElementPattern.Matcher<TypePattern> {

        private final Class<?> type;

        public static Matcher of(Class<?> type) {
            return new Matcher(type);
        }

        @Override
        protected TypePattern createPattern(Element element) {
            return new TypePattern(element);
        }

        @Override
        protected boolean matches(Element element) {
            return type.isAssignableFrom(element.getClass());
        }
    }
}
