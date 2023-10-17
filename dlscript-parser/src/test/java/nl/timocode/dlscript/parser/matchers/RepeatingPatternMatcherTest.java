package nl.timocode.dlscript.parser.matchers;

import lombok.Getter;
import nl.timocode.dlscript.parser.Element;
import nl.timocode.dlscript.parser.primitives.CharToken;
import nl.timocode.dlscript.parser.primitives.LongToken;
import nl.timocode.dlscript.parser.primitives.StringLiteralToken;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** @noinspection unchecked*/
class RepeatingPatternMatcherTest {

    @Test
    void matches() {
        // GIVEN
        RepeatingPatternMatcher<?> cut = RepeatingPatternMatcher.of(
                TypePatternMatcher.of(LongToken.class, TestBuilder::addElement));
        List<? extends Element> elements = createElements(0, 1, "s", 1);

        // WHEN
        List<? extends MatchResult<?>> matches = cut.matches(elements, false);

        // THEN
        assertEquals(3, matches.size());
        matches = matches.stream().sorted(Comparator.comparingInt(MatchResult::getEndElementIdx)).toList();
        assertMatch(0, 2, List.of(0L, 1L), (MatchResult<TestBuilder>) matches.get(0));
        assertMatch(3, 4, List.of(1L), (MatchResult<TestBuilder>) matches.get(1));
        assertEquals(MatchResult.partial(3), matches.get(2));
    }

    @Test
    void matchesFirstMatchHalfway() {
        // GIVEN
        RepeatingPatternMatcher<?> cut = RepeatingPatternMatcher.of(
                TypePatternMatcher.of(LongToken.class, TestBuilder::addElement));
        List<? extends Element> elements = createElements("s", 0, 1, "t", 1);

        // WHEN
        List<? extends MatchResult<?>> matches = cut.matches(elements, false);

        // THEN
        //assertEquals(3, matches.size());
        matches = matches.stream().sorted(Comparator.comparingInt(MatchResult::getEndElementIdx)).toList();
        assertMatch(1, 3, List.of(0L, 1L), (MatchResult<TestBuilder>) matches.get(0));
        assertMatch(4, 5, List.of(1L), (MatchResult<TestBuilder>) matches.get(1));
        assertEquals(MatchResult.partial(4), matches.get(2));
    }

    @Test
    void matchesFromStart() {
        // GIVEN
        RepeatingPatternMatcher<?> cut = RepeatingPatternMatcher.of(
                TypePatternMatcher.of(LongToken.class, TestBuilder::addElement));
        List<? extends Element> elements = createElements(0, 1, "s", 1);

        // WHEN
        List<? extends MatchResult<?>> matches = cut.matches(elements, true);

        // THEN
        assertEquals(1, matches.size());
        assertMatch(0, 2, List.of(0L, 1L), (MatchResult<TestBuilder>) matches.get(0));
    }

    @Test
    void matchesFromStartTillEnd() {
        // GIVEN
        RepeatingPatternMatcher<?> cut = RepeatingPatternMatcher.of(
                TypePatternMatcher.of(LongToken.class, TestBuilder::addElement));
        List<? extends Element> elements = createElements(0, 1);

        // WHEN
        List<? extends MatchResult<?>> matches = cut.matches(elements, true);

        // THEN
        assertEquals(2, matches.size());
        assertMatch(0, 2, List.of(0L, 1L), (MatchResult<TestBuilder>) matches.get(0));
        assertEquals(MatchResult.partial(0), matches.get(1));
    }

    @Test
    void matchesWithDelimiter() {
        // GIVEN
        PatternMatcher<?> cut = RepeatingPatternMatcher.of(
                TypePatternMatcher.of(LongToken.class, TestBuilder::addElement))
                .withDelimiter(',');

        List<? extends Element> elements = createElements(0, ',', 1, ',', 2);

        // WHEN
        List<? extends MatchResult<?>> matches = cut.matches(elements, true);

        // THEN
        assertEquals(3, matches.size());
        assertMatch(0, 5, List.of(0L, 1L, 2L), (MatchResult<TestBuilder>) matches.get(1));
        assertEquals(MatchResult.partial(0), matches.get(0));
    }

    private List<? extends Element> createElements(Object... input) {
        List<Object> inputList = List.of(input);

        return inputList.stream()
                .map(o -> {
                    if (o instanceof Integer) {
                        return new LongToken((Integer) o);
                    } else if (o instanceof Character) {
                        return new CharToken((Character) o);
                    } else {
                        return new StringLiteralToken(o.toString());
                    }
                }).toList();
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

        public void addElement(LongToken value) {
            values.add(value.getValue());
        }
    }
}