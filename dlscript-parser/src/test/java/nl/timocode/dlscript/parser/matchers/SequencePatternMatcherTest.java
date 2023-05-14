package nl.timocode.dlscript.parser.matchers;

import nl.timocode.dlscript.parser.ParseElement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        SequencePatternMatcher cut = createSequencePatternMatcher(1, 2, 3);
        List<? extends ParseElement> elements = createParseElements(el1, el2, el3, el4);

        // WHEN
        List<PatternMatch> matches = cut.matches(elements);

        // THEN
        PatternMatch expected = MatchType.FULL.equals(type)
                ? PatternMatch.fullMatch(startElement, endElement)
                : PatternMatch.partialMatch(startElement);

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
        SequencePatternMatcher cut = createSequencePatternMatcher(1, 2, 3);
        List<? extends ParseElement> elements = createParseElements(el1, el2, el3, el4);

        // WHEN
        List<PatternMatch> matches = cut.matches(elements);

        // THEN
        assertEquals(Collections.emptyList(), matches);
    }

    @Test
    void multipleMatches() {
        // GIVEN
        SequencePatternMatcher cut = createSequencePatternMatcher(1, 2, 1);
        List<? extends ParseElement> elements = createParseElements(0, 1, 2, 1, 2, 1);

        // WHEN
        List<PatternMatch> matches = cut.matches(elements);

        // THEN
        PatternMatch expected1 = PatternMatch.fullMatch(1, 4);
        PatternMatch expected2 = PatternMatch.fullMatch(3, 6);
        PatternMatch expected3 = PatternMatch.partialMatch(5);

        assertEquals(List.of(expected1, expected2, expected3), matches);
    }

    private SequencePatternMatcher createSequencePatternMatcher(Integer... input) {
        List<ValuePatternMatcher<Integer>> matchers = Arrays.stream(input)
                .map(ValuePatternMatcher::new)
                .toList();
        return SequencePatternMatcher.of(matchers.toArray(new ValuePatternMatcher[0]));
    }

    private List<? extends ParseElement> createParseElements(Integer... input) {
        List<Integer> inputList = List.of(input);

        return inputList.stream()
                .map(n -> new ParseElement(n, Integer.class))
                .toList();
    }
}