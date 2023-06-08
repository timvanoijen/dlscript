package nl.timocode.dlscript.parser.matchers;

import lombok.Getter;
import nl.timocode.dlscript.parser.Element;
import nl.timocode.dlscript.parser.primitives.LongElement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** @noinspection unchecked*/
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
        SequencePatternMatcher<?> cut = createSequencePatternMatcher(1, 2, 3);
        List<? extends Element> elements = createElements(el1, el2, el3, el4);

        // WHEN
        List<? extends MatchResult<?>> matches = cut.matches(elements, false);

        // THEN
        assertEquals(1, matches.size());
        if (MatchType.FULL.equals(type)) {
            assertMatch(startElement, endElement, List.of(1L, 2L, 3L), (MatchResult<TestBuilder>) matches.get(0));
        } else {
            assertEquals(MatchResult.partial(startElement), matches.get(0));
        }
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
        SequencePatternMatcher<?> cut = createSequencePatternMatcher(1, 2, 3);
        List<? extends Element> elements = createElements(el1, el2, el3, el4);

        // WHEN
        List<? extends MatchResult<?>> matches = cut.matches(elements, false);

        // THEN
        assertEquals(Collections.emptyList(), matches);
    }

    @Test
    void multipleMatches() {
        // GIVEN
        SequencePatternMatcher<?> cut = createSequencePatternMatcher(1, 2, 1);
        List<? extends Element> elements = createElements(0, 1, 2, 1, 2, 1);

        // WHEN
        List<? extends MatchResult<?>> matches = cut.matches(elements, false);

        // THEN
        assertEquals(3, matches.size());
        assertEquals(MatchResult.partial(5), matches.get(0));
        assertMatch(1, 4, List.of(1L, 2L, 1L), (MatchResult<TestBuilder>) matches.get(1));
        assertMatch(3, 6, List.of(1L, 2L, 1L), (MatchResult<TestBuilder>) matches.get(2));
    }

    @Test
    void matchesFromStart() {
        // GIVEN
        SequencePatternMatcher<?> cut = createSequencePatternMatcher(1, 2);
        List<? extends Element> elements = createElements(1, 2, 1, 2, 1);

        // WHEN
        List<? extends MatchResult<?>> matches = cut.matches(elements, true);

        // THEN
        assertEquals(1, matches.size());
        assertMatch(0, 2, List.of(1L, 2L), (MatchResult<TestBuilder>) matches.get(0));
    }

    private SequencePatternMatcher<?> createSequencePatternMatcher(Integer... input) {
        List<ValuePatternMatcher<LongElement, TestBuilder>> matchers = Arrays.stream(input)
                .map(LongElement::new)
                .map(e -> ValuePatternMatcher.of(e, TestBuilder::addElement))
                .toList();
        return SequencePatternMatcher.of(matchers.toArray(new ValuePatternMatcher[0]));
    }

    private List<? extends Element> createElements(Integer... input) {
        List<Integer> inputList = List.of(input);

        return inputList.stream()
                .map(LongElement::new)
                .toList();
    }

    private void assertMatch(int expStartIdx, int expEndIdx, List<Long> expValues, MatchResult<TestBuilder> result) {
        assertEquals(expStartIdx, result.getStartElementIdx());
        assertEquals(expEndIdx, result.getEndElementIdx());
        TestBuilder tb = new TestBuilder();
        result.consume(tb);
        assertEquals(expValues, tb.getValues());
    }

    @Getter
    private static class TestBuilder {
        private final List<Long> values = new ArrayList<>();

        public void addElement(LongElement value) {
            values.add(value.getValue());
        }
    }
}