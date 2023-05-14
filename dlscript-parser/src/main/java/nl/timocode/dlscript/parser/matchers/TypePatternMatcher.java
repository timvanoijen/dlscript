package nl.timocode.dlscript.parser.matchers;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import nl.timocode.dlscript.parser.Element;

@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public final class TypePatternMatcher extends SingleElementPatternMatcher {

    private final Class<?> type;

    @Override
    protected boolean matches(Element element) {
        return type.isAssignableFrom(element.getClass());
    }
}
