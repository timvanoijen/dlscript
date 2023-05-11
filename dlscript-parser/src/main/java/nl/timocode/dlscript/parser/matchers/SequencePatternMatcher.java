package nl.timocode.dlscript.parser.matchers;

import nl.timocode.dlscript.parser.ParseElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class SequencePatternMatcher implements PatternMatcher {

    private final List<SingleElementPatternMatcher> matchers;

    private SequencePatternMatcher(List<SingleElementPatternMatcher> matchers) {
        Objects.requireNonNull(matchers);
        this.matchers = matchers;
    }

    public static SequencePatternMatcher of(SingleElementPatternMatcher... matcher) {
        return new SequencePatternMatcher(Arrays.asList(matcher));
    }

    @Override
    public List<PatternMatch> matches(List<? extends ParseElement<?>> elements) {
        List<PatternMatch> matches = new ArrayList<>();
        for(int i = 0; i < elements.size(); i++) {
            boolean allMatch = true;
            int m = Math.min(elements.size() - i, matchers.size());
            for(int j = 0; j < m; j++) {
                if (!matchers.get(j).matches(elements.get(i + j))) {
                    allMatch = false;
                    break;
                }
            }
            if (allMatch) {
                matches.add( m == matchers.size()
                        ? PatternMatch.fullMatch(i, i + m)
                        : PatternMatch.partialMatch(i));
            }
        }
        return matches;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SequencePatternMatcher that = (SequencePatternMatcher) o;
        return matchers.equals(that.matchers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matchers);
    }
}
