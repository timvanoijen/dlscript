package nl.timocode.dlscript.parser.matchers;

import lombok.Data;
import nl.timocode.dlscript.parser.Element;
import nl.timocode.dlscript.parser.primitives.LongToken;
import nl.timocode.dlscript.parser.primitives.StringLiteralToken;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** @noinspection unchecked*/
class ValuePatternMatcherTest {

    @Test
    void matches() {
        // GIVEN
        ValuePatternMatcher<?,?> cut = ValuePatternMatcher.of(new LongToken(3L), TestBuilder::setE);
        List<Element> elements = List.of(
                new LongToken(1L),
                new StringLiteralToken("2"),
                new LongToken(3L)
        );

        // WHEN
        List<? extends MatchResult<?>> matches = cut.matches(elements, false);

        // THEN
        assertEquals(1, matches.size());
        assertMatch(2, 3, new LongToken(3L), (MatchResult<TestBuilder>) matches.get(0));
    }

    @Test
    void matchesFromStart() {
        // GIVEN
        ValuePatternMatcher<?,?> cut = ValuePatternMatcher.of(new LongToken(1L), TestBuilder::setE);
        List<Element> elements = List.of(
                new LongToken(1L),
                new LongToken(3L),
                new LongToken(1L)
        );

        // WHEN
        List<? extends MatchResult<?>> matches = cut.matches(elements, true);

        // THEN
        assertEquals(1, matches.size());
        assertMatch(0, 1, new LongToken(1L), (MatchResult<TestBuilder>) matches.get(0));
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
        private LongToken e;
    }
}