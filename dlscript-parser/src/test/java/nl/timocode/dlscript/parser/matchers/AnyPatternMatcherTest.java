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
class AnyPatternMatcherTest {

    @Test
    void matches() {
        // GIVEN
        AnyPatternMatcher<?> cut = AnyPatternMatcher.of(
                ValuePatternMatcher.of(new StringElement("2"), TestBuilder::setE),
                TypePatternMatcher.of(LongElement.class, TestBuilder::setE));

        List<Element> elements = List.of(
                new LongElement(1L),
                new StringElement("2"),
                new LongElement(3L),
                new StringElement("4")
        );

        // WHEN
        List<? extends MatchResult<?>> matches = cut.matches(elements, false);

        // THEN
        assertEquals(3, matches.size());
        matches = matches.stream().sorted(Comparator.comparingInt(MatchResult::getStartElementIdx)).toList();
        assertMatch(0, 1, elements.get(0), (MatchResult<TestBuilder>) matches.get(0));
        assertMatch(1, 2, elements.get(1), (MatchResult<TestBuilder>) matches.get(1));
        assertMatch(2, 3, elements.get(2), (MatchResult<TestBuilder>) matches.get(2));
    }

    @Test
    void matchesFromStart() {
        // GIVEN
        AnyPatternMatcher<?> cut = AnyPatternMatcher.of(
                ValuePatternMatcher.of(new StringElement("2"), TestBuilder::setE),
                TypePatternMatcher.of(LongElement.class, TestBuilder::setE));

        List<Element> elements = List.of(
                new LongElement(1L),
                new StringElement("2"),
                new LongElement(3L)
        );

        // WHEN
        List<? extends MatchResult<?>> matches = cut.matches(elements, true);

        // THEN
        assertEquals(1, matches.size());
        assertMatch(0, 1, elements.get(0), (MatchResult<TestBuilder>) matches.get(0));
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
        private Element e;
    }
}