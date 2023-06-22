package nl.timocode.dlscript.parser.matchers;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import nl.timocode.dlscript.parser.Element;

import java.util.*;

@AllArgsConstructor
@EqualsAndHashCode
public class RepeatingPatternMatcher<C> implements PatternMatcher<C> {

    private final PatternMatcher<C> matcher;

    public static <C> RepeatingPatternMatcher<C> of(PatternMatcher<C> innerMatcher) {
        return new RepeatingPatternMatcher<>(innerMatcher);
    }

    @Override
    public List<MatchResult<C>> matches(List<? extends Element> elements, boolean fromStart) {

        List<MatchResult<C>> innerMatches = matcher.matches(elements, false);
        innerMatches = innerMatches.stream().sorted(Comparator
                .<MatchResult<C>>comparingInt(MatchResult::getStartElementIdx)
                .thenComparingInt(MatchResult::getEndElementIdx)).toList();

        List<MatchResult<C>> finalResults = new ArrayList<>();
        MatchResult<C> curMatch = MatchResult.full(0, 0, c -> {});
        for(MatchResult<C> nextMatch : innerMatches) {
            int nextMatchStartIdx = nextMatch.getStartElementIdx();
            if (nextMatchStartIdx == curMatch.getEndElementIdx()) {
                curMatch = curMatch.followedBy(nextMatch);
            } else {
                if (curMatch.getEndElementIdx() != curMatch.getStartElementIdx()) {
                    finalResults.add(curMatch);
                }
                curMatch = nextMatch;
                if (fromStart) {
                    return finalResults;
                }
            }
        }

        if (curMatch.getEndElementIdx() != curMatch.getStartElementIdx()) {
            finalResults.add(curMatch);
        }
        if (curMatch.getEndElementIdx() == elements.size()) {
            finalResults.add(MatchResult.partial(curMatch.getStartElementIdx()));
        }
        return finalResults;
    }
}
