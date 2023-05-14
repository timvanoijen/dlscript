package nl.timocode.dlscript.parser.matchers;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import nl.timocode.dlscript.parser.Element;

import java.util.Objects;

@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public final class ValuePatternMatcher<T extends Element> extends SingleElementPatternMatcher {

    private final T value;

    @Override
    protected boolean matches(Element element) {
        return Objects.equals(element, value);
    }
}
