package nl.timocode.dlscript.parser.matchers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import nl.timocode.dlscript.parser.Element;

import java.util.List;
import java.util.stream.IntStream;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EmptyPattern implements Pattern {

    public static EmptyPattern INSTANCE = new EmptyPattern();

    public static class Matcher implements PatternMatcher {

        @Override
        public List<MatchResult> matches(List<? extends Element> elements, boolean fromStart) {
            return IntStream.range(0, fromStart ? 1 : elements.size())
                    .mapToObj(i -> MatchResult.full(i, i, EmptyPattern.INSTANCE))
                    .toList();
        }
    }
}
