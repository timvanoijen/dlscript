package nl.timocode.dlscript.parser.matchers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import nl.timocode.dlscript.parser.Element;

import java.util.Objects;

public class ValuePattern extends SingleElementPattern {

    public ValuePattern(Element element) {
        super(element);
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @EqualsAndHashCode(callSuper = false)
    public static final class Matcher extends SingleElementPattern.Matcher<ValuePattern> {

        private final Element value;

        public static Matcher of(Element value) {
            return new Matcher(value);
        }

        @Override
        protected ValuePattern createPattern(Element element) {
            return new ValuePattern(element);
        }

        @Override
        protected boolean matches(Element element) {
            return Objects.equals(element, value);
        }
    }
}