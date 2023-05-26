package nl.timocode.dlscript.parser.matchers;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.timocode.dlscript.parser.Element;

import java.util.*;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class SequencePattern implements Pattern {
    private final List<Pattern> innerPatterns;

    @AllArgsConstructor
    @EqualsAndHashCode
    public static final class Matcher implements PatternMatcher {

        private final List<PatternMatcher> matchers;

        public static Matcher of(PatternMatcher... matcher) {
            return new Matcher(List.of(matcher));
        }

        @Override
        public List<MatchResult> matches(List<? extends Element> elements, boolean fromStart) {
            record ResultState(List<Pattern> innerMatches,
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
                            new SequencePattern(intermediateResult.innerMatches)));
                    continue;
                } else if (intermediateResult.endIdx == elements.size()) {
                    finalResults.add(MatchResult.partial(intermediateResult.startIdx));
                    continue;
                }

                List<? extends Element> remElements = elements.subList(intermediateResult.endIdx, elements.size());
                boolean innerFromStart = matcherIdx > 0 || fromStart;
                for(MatchResult innerMatchResult : matchers.get(matcherIdx).matches(remElements, innerFromStart)) {
                    if (innerMatchResult.isFullMatch()) {
                        List<Pattern> newInnerPatterns =
                                new ArrayList<>(intermediateResult.innerMatches);
                        newInnerPatterns.add(innerMatchResult.getPattern());
                        int newStartIdx = matcherIdx == 0 ? innerMatchResult.getStartElementIdx() : intermediateResult.startIdx;
                        int newEndIdx = intermediateResult.endIdx + innerMatchResult.getEndElementIdx();
                        intermediateResults.add(new ResultState(newInnerPatterns,
                                matcherIdx + 1, newStartIdx, newEndIdx));
                    }
                }
            }

            return finalResults;
        }
    }

}
