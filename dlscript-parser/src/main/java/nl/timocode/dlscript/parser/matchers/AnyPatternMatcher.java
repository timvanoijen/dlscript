package nl.timocode.dlscript.parser.matchers;

import nl.timocode.dlscript.parser.ParseElement;

import java.util.List;
import java.util.Objects;

public final class AnyPatternMatcher implements PatternMatcher {

    private final List<PatternMatcher> matchers;

    private AnyPatternMatcher(List<PatternMatcher> matchers) {
        Objects.requireNonNull(matchers);
        this.matchers = matchers;
    }

    public static AnyPatternMatcher of(PatternMatcher... matcher) {
        return new AnyPatternMatcher(List.of(matcher));
    }

    @Override
    public List<PatternMatch> matches(List<? extends ParseElement> elements) {
        return matchers.stream().flatMap(matcher -> matcher.matches(elements).stream()).toList();
    }
}
