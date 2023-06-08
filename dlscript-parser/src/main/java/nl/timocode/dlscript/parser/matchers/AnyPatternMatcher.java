package nl.timocode.dlscript.parser.matchers;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import nl.timocode.dlscript.parser.Element;

import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode
public class AnyPatternMatcher<C> implements PatternMatcher<C> {

    private final List<PatternMatcher<C>> matchers;

    @SafeVarargs
    public static <C> AnyPatternMatcher<C> of(PatternMatcher<C>... matcher) {
        return new AnyPatternMatcher<>(List.of(matcher));
    }

    @Override
    public List<MatchResult<C>> matches(List<? extends Element> elements, boolean fromStart) {
        return matchers.stream()
                .flatMap(m -> m.matches(elements, fromStart).stream())
                .toList();
    }
}
