package nl.timocode.dlscript.parser.matchers;

import lombok.EqualsAndHashCode;
import nl.timocode.dlscript.parser.Element;

import java.util.List;

@EqualsAndHashCode
public class OptionalPatternMatcher<C> implements PatternMatcher<C> {

    private final PatternMatcher<C> matcher;

    private OptionalPatternMatcher(PatternMatcher<C> innerMatcher) {
        this.matcher = AnyPatternMatcher.of(innerMatcher, new EmptyPatternMatcher<>());
    }

    public static <C> OptionalPatternMatcher<C> of(PatternMatcher<C> innerMatcher) {
        return new OptionalPatternMatcher<>(innerMatcher);
    }

    @Override
    public List<MatchResult<C>> matches(List<? extends Element> elements, boolean fromStart) {
        return matcher.matches(elements, fromStart);
    }
}
