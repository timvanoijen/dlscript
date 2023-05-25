package nl.timocode.dlscript.parser.matchers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import nl.timocode.dlscript.parser.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@AllArgsConstructor
public class OptionalPatternMatcher implements PatternMatcher {

    private final PatternMatcher innerMatcher;

    @Override
    public List<MatchResult> matches(List<? extends Element> elements, boolean fromStart) {
        List<MatchResult> result = new ArrayList<>();
        result.addAll(IntStream.range(0, fromStart ? 1 : elements.size())
                .mapToObj(i -> MatchResult.full(i, i, Match.empty()))
                .toList());
        result.addAll(innerMatcher.matches(elements, fromStart).stream()
                .map(mr -> mr.isFullMatch()
                        ? MatchResult.full(mr.getStartElementIdx(), mr.getEndElementIdx(), Match.of(mr.getMatch()))
                        : MatchResult.partial(mr.getStartElementIdx()))
                .toList());
        return result;
    }

    @EqualsAndHashCode
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Match implements nl.timocode.dlscript.parser.matchers.Match {

        private final nl.timocode.dlscript.parser.matchers.Match innerMatch;

        public boolean isPresent() {
            return innerMatch != null;
        }

        public static Match empty() {
            return new Match(null);
        }

        public static Match of(nl.timocode.dlscript.parser.matchers.Match innerMatch) {
            return new Match(innerMatch);
        }

        public nl.timocode.dlscript.parser.matchers.Match get() {
            assert isPresent();
            return innerMatch;
        }
    }
}
