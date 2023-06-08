package nl.timocode.dlscript.parser.matchers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import nl.timocode.dlscript.parser.Element;

import java.util.*;
import java.util.function.Consumer;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class SequencePatternMatcher<C> implements PatternMatcher<C> {

    private final List<PatternMatcher<C>> matchers;

    @SafeVarargs
    public static <C> SequencePatternMatcher<C> of(PatternMatcher<C>... matcher) {
        return new SequencePatternMatcher<>(List.of(matcher));
    }

    @Override
    public List<MatchResult<C>> matches(List<? extends Element> elements, boolean fromStart) {

        record ResultState<C>(List<Consumer<C>> innerConsumers, int matcherIdx, int startIdx, int endIdx) {
        }

        List<MatchResult<C>> finalResults = new ArrayList<>();
        Deque<ResultState<C>> intermediateResults = new ArrayDeque<>();
        intermediateResults.add(new ResultState<>(List.of(), 0, 0, 0));

        while(!intermediateResults.isEmpty()) {
            ResultState<C> intermediateResult = intermediateResults.poll();
            int matcherIdx = intermediateResult.matcherIdx;

            if (matcherIdx == matchers.size()) {
                finalResults.add(MatchResult.full(intermediateResult.startIdx, intermediateResult.endIdx,
                        c -> intermediateResult.innerConsumers().forEach(cInner -> cInner.accept(c))));
                continue;
            } else if (intermediateResult.endIdx == elements.size()) {
                finalResults.add(MatchResult.partial(intermediateResult.startIdx));
                continue;
            }

            List<? extends Element> remElements = elements.subList(intermediateResult.endIdx, elements.size());
            boolean innerFromStart = matcherIdx > 0 || fromStart;
            for(MatchResult<C> innerMatchResult : matchers.get(matcherIdx).matches(remElements, innerFromStart)) {
                int newStartIdx = matcherIdx == 0 ? innerMatchResult.getStartElementIdx() : intermediateResult.startIdx;
                if (innerMatchResult.isFullMatch()) {
                    List<Consumer<C>> newInnerConsumers =
                            new ArrayList<>(intermediateResult.innerConsumers);
                    newInnerConsumers.add(innerMatchResult.getAcceptResultCollector());
                    int newEndIdx = intermediateResult.endIdx + innerMatchResult.getEndElementIdx();
                    intermediateResults.add(new ResultState<>(newInnerConsumers,
                            matcherIdx + 1, newStartIdx, newEndIdx));
                } else {
                    finalResults.add(MatchResult.partial(newStartIdx));
                }
            }
        }

        return finalResults;
    }
}