package nl.timocode.dlscript.parser.matchers;

import lombok.Data;
import nl.timocode.dlscript.parser.Element;
import nl.timocode.dlscript.parser.primitives.LongElement;
import nl.timocode.dlscript.parser.primitives.StringElement;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** @noinspection unchecked*/
class OptionalPatternMatcherTest {

    @Test
    void matches() {
        // GIVEN
        OptionalPatternMatcher<?> cut = OptionalPatternMatcher.of(
                ValuePatternMatcher.of(new StringElement("2"), TestBuilder::setE));

        List<Element> elements = List.of(
                new LongElement(1L),
                new StringElement("2")
        );

        // WHEN
        List<? extends MatchResult<?>> matches = cut.matches(elements, false);

        // THEN
        assertEquals(3, matches.size());
        matches = matches.stream().sorted(Comparator.comparingInt(MatchResult::getEndElementIdx)).toList();
        assertMatch(0, 0, null, (MatchResult<TestBuilder>) matches.get(0));
        assertMatch(1, 1, null, (MatchResult<TestBuilder>) matches.get(1));
        assertMatch(1, 2, elements.get(1), (MatchResult<TestBuilder>) matches.get(2));
    }

    @Test
    void matchesFromStart() {
        // GIVEN
        OptionalPatternMatcher<?> cut = OptionalPatternMatcher.of(
                ValuePatternMatcher.of(new StringElement("2"), TestBuilder::setE));

        List<Element> elements = List.of(
                new StringElement("2"),
                new LongElement(1L),
                new StringElement("2")
        );

        // WHEN
        List<? extends MatchResult<?>> matches = cut.matches(elements, true);

        // THEN
        assertEquals(2, matches.size());
        matches = matches.stream().sorted(Comparator.comparingInt(MatchResult::getEndElementIdx)).toList();
        assertMatch(0, 0, null, (MatchResult<TestBuilder>) matches.get(0));
        assertMatch(0, 1, elements.get(0), (MatchResult<TestBuilder>) matches.get(1));
    }

    private void assertMatch(int expStartIdx, int expEndIdx, Element expElement, MatchResult<TestBuilder> result) {
        assertEquals(expStartIdx, result.getStartElementIdx());
        assertEquals(expEndIdx, result.getEndElementIdx());
        TestBuilder tb = new TestBuilder();
        result.consume(tb);
        assertEquals(expElement, tb.getE());
    }

    @Data
    private static class TestBuilder {
        private StringElement e;
    }
}