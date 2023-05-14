package nl.timocode.dlscript.parser.matchers;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import nl.timocode.dlscript.parser.Element;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode
public final class SequencePatternMatcher implements PatternMatcher {

    private final List<SingleElementPatternMatcher> matchers;

    public static SequencePatternMatcher of(SingleElementPatternMatcher... matcher) {
        return new SequencePatternMatcher(List.of(matcher));
    }

    @Override
    public List<PatternMatch> matches(List<? extends Element> elements) {
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
}
