package nl.timocode.dlscript.parser.matchers;

import lombok.Data;
import nl.timocode.dlscript.parser.Element;
import nl.timocode.dlscript.parser.primitives.LongToken;
import nl.timocode.dlscript.parser.primitives.StringLiteralToken;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** @noinspection unchecked*/
class TypePatternMatcherTest {

    @Test
    void matches() {
        // GIVEN
        TypePatternMatcher<?,?> cut = TypePatternMatcher.of(LongToken.class, TestBuilder::setE);
        List<Element> elements = List.of(
                new LongToken(1L),
                new StringLiteralToken("2"),
                new LongToken(3L),
                new StringLiteralToken("4")
        );

        // WHEN
        List<? extends MatchResult<?>> matches = cut.matches(elements, false);

        // THEN
        assertEquals(2, matches.size());
        assertMatch(0, 1, elements.get(0), (MatchResult<TestBuilder>) matches.get(0));
        assertMatch(2, 3, elements.get(2), (MatchResult<TestBuilder>) matches.get(1));
    }

    @Test
    void matchesFromStart() {
        // GIVEN
        TypePatternMatcher<?,?> cut = TypePatternMatcher.of(LongToken.class, TestBuilder::setE);
        List<Element> elements = List.of(
                new LongToken(1L),
                new StringLiteralToken("2"),
                new LongToken(3L)
        );

        // WHEN
        List<? extends MatchResult<?>> matches = cut.matches(elements, true);

        // THEN
        assertEquals(1, matches.size());
        assertMatch(0, 1, elements.get(0), (MatchResult<TestBuilder>) matches.get(0));
    }

    @Test
    void matchesSubType() {
        // GIVEN
        TypePatternMatcher<?,?> cut = TypePatternMatcher.of(LongToken.class, TestBuilder::setE);
        List<Element> elements = List.of(
                new SubLongToken(1L),
                new StringLiteralToken("x")
        );

        // WHEN
        List<? extends MatchResult<?>> matches = cut.matches(elements, false);

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

    private static class SubLongToken extends LongToken {
        public SubLongToken(long value) {
            super(value);
        }
    }

    @Data
    private static class TestBuilder {
        private LongToken e;
    }
}