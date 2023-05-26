package nl.timocode.dlscript.parser.matchers;

import nl.timocode.dlscript.parser.Element;
import nl.timocode.dlscript.parser.primitives.LongElement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class SequencePatternMatcherTest {

    private enum MatchType {
        FULL, PARTIAL
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2, 3, 0, FULL, 0, 3",
            "0, 1, 2, 3, FULL, 1, 4",
            "0, 0, 1, 2, PARTIAL, 2, -1",
            "0, 0, 0, 1, PARTIAL, 3, -1"
    })
    void singleMatch(int el1, int el2, int el3, int el4, MatchType type, int startElement, int endElement) {
        // GIVEN
        SequencePattern.Matcher cut = createSequencePatternMatcher(1, 2, 3);
        List<? extends Element> elements = createElements(el1, el2, el3, el4);

        // WHEN
        List<MatchResult> matches = cut.matches(elements, false);

        // THEN
        MatchResult expected = MatchType.FULL.equals(type)
                ? createFullMatchResult(elements, startElement, endElement)
                : MatchResult.partial(startElement);

        assertEquals(List.of(expected), matches);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2, 1, 3",
            "0, 0, 0, 0",
            "1, 2, 0, 2",
            "3, 2, 1, 0"
    })
    void noMatch(int el1, int el2, int el3, int el4) {
        // GIVEN
        SequencePattern.Matcher cut = createSequencePatternMatcher(1, 2, 3);
        List<? extends Element> elements = createElements(el1, el2, el3, el4);

        // WHEN
        List<MatchResult> matches = cut.matches(elements, false);

        // THEN
        assertEquals(Collections.emptyList(), matches);
    }

    @Test
    void multipleMatches() {
        // GIVEN
        SequencePattern.Matcher cut = createSequencePatternMatcher(1, 2, 1);
        List<? extends Element> elements = createElements(0, 1, 2, 1, 2, 1);

        // WHEN
        List<MatchResult> matches = cut.matches(elements, false);

        // THEN
        MatchResult expected1 = MatchResult.partial(5);
        MatchResult expected2 = createFullMatchResult(elements, 1, 4);
        MatchResult expected3 = createFullMatchResult(elements,3, 6);

        assertEquals(List.of(expected1, expected2, expected3), matches);
    }

    private SequencePattern.Matcher createSequencePatternMatcher(Integer... input) {
        List<ValuePattern.Matcher> matchers = Arrays.stream(input)
                .map(LongElement::new)
                .map(ValuePattern.Matcher::of)
                .toList();
        return SequencePattern.Matcher.of(matchers.toArray(new ValuePattern.Matcher[0]));
    }

    private List<? extends Element> createElements(Integer... input) {
        List<Integer> inputList = List.of(input);

        return inputList.stream()
                .map(LongElement::new)
                .toList();
    }

    private MatchResult createFullMatchResult(List<? extends Element> elements, int startElementIdx, int endElementIdx) {
        List<Pattern> innerPatterns = IntStream.range(startElementIdx, endElementIdx)
                .mapToObj(i -> new ValuePattern(elements.get(i)))
                .collect(Collectors.toList());
        return MatchResult.full(startElementIdx, endElementIdx, new SequencePattern(innerPatterns));
    }
}