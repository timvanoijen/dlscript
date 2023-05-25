package nl.timocode.dlscript.parser.matchers;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.timocode.dlscript.parser.Element;

import java.util.*;

@AllArgsConstructor
@EqualsAndHashCode
public final class SequencePatternMatcher implements PatternMatcher {

    private final List<PatternMatcher> matchers;

    public static SequencePatternMatcher of(PatternMatcher... matcher) {
        return new SequencePatternMatcher(List.of(matcher));
    }

    @Override
    public List<MatchResult> matches(List<? extends Element> elements, boolean fromStart) {
        record ResultState(List<nl.timocode.dlscript.parser.matchers.Match> innerMatches,
                           int matcherIdx, int startIdx, int endIdx) {
        }

        List<MatchResult> finalResults = new ArrayList<>();
        Deque<ResultState> intermediateResults = new ArrayDeque<>();
        intermediateResults.add(new ResultState(List.of(), 0, 0, 0));

        while(!intermediateResults.isEmpty()) {
            ResultState intermediateResult = intermediateResults.poll();
            int matcherIdx = intermediateResult.matcherIdx;

            if (matcherIdx == matchers.size()) {
                finalResults.add(MatchResult.full(intermediateResult.startIdx, intermediateResult.endIdx,
                        new SequencePatternMatcher.Match(intermediateResult.innerMatches)));
                continue;
            } else if (intermediateResult.endIdx == elements.size()) {
                finalResults.add(MatchResult.partial(intermediateResult.startIdx));
                continue;
            }

            List<? extends Element> remElements = elements.subList(intermediateResult.endIdx, elements.size());
            boolean innerFromStart = matcherIdx > 0 || fromStart;
            for(MatchResult innerMatchResult : matchers.get(matcherIdx).matches(remElements, innerFromStart)) {
                if (innerMatchResult.isFullMatch()) {
                    List<nl.timocode.dlscript.parser.matchers.Match> newInnerMatches =
                            new ArrayList<>(intermediateResult.innerMatches);
                    newInnerMatches.add(innerMatchResult.getMatch());
                    int newStartIdx = matcherIdx == 0 ? innerMatchResult.getStartElementIdx() : intermediateResult.startIdx;
                    int newEndIdx = intermediateResult.endIdx + innerMatchResult.getEndElementIdx();
                    intermediateResults.add(new ResultState(newInnerMatches,
                            matcherIdx + 1, newStartIdx, newEndIdx));
                }
            }
        }

        return finalResults;
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    public static class Match implements nl.timocode.dlscript.parser.matchers.Match {

        private final List<nl.timocode.dlscript.parser.matchers.Match> innerMatches;
    }
}
