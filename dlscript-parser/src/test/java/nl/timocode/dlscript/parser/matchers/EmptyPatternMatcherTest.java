package nl.timocode.dlscript.parser.matchers;

import lombok.Data;
import nl.timocode.dlscript.parser.Element;
import nl.timocode.dlscript.parser.primitives.LongToken;
import nl.timocode.dlscript.parser.primitives.StringLiteralToken;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** @noinspection unchecked*/
class EmptyPatternMatcherTest {

    @Test
    void matches() {
        // GIVEN
        EmptyPatternMatcher<TestBuilder> cut = new EmptyPatternMatcher<>();
        List<Element> elements = List.of(
                new LongToken(1L),
                new StringLiteralToken("2")
        );

        // WHEN
        List<? extends MatchResult<?>> matches = cut.matches(elements, false);

        // THEN
        assertEquals(2, matches.size());
        assertMatch(0, 0, (MatchResult<TestBuilder>) matches.get(0));
        assertMatch(1, 1, (MatchResult<TestBuilder>) matches.get(1));
    }

    @Test
    void matchesFromStart() {
        // GIVEN
        EmptyPatternMatcher<TestBuilder> cut = new EmptyPatternMatcher<>();
        List<Element> elements = List.of(
                new LongToken(1L),
                new StringLiteralToken("2")
        );

        // WHEN
        List<? extends MatchResult<?>> matches = cut.matches(elements, true);

        // THEN
        assertEquals(1, matches.size());
        assertMatch(0, 0, (MatchResult<TestBuilder>) matches.get(0));
    }

    private void assertMatch(int expStartIdx, int expEndIdx, MatchResult<TestBuilder> result) {
        assertEquals(expStartIdx, result.getStartElementIdx());
        assertEquals(expEndIdx, result.getEndElementIdx());
        TestBuilder tb = new TestBuilder();
        result.consume(tb);
        assertNull(tb.getE());
    }

    @Data
    private static class TestBuilder {
        private LongToken e;
    }
}