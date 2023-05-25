package nl.timocode.dlscript.parser.matchers;

import nl.timocode.dlscript.parser.Element;
import nl.timocode.dlscript.parser.primitives.LongElement;
import nl.timocode.dlscript.parser.primitives.StringElement;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TypePatternMatcherTest {

    @Test
    void matchesSameType() {
        // GIVEN
        TypePatternMatcher cut = new TypePatternMatcher(StringElement.class);
        List<Element> elements = List.of(
                new LongElement(1L),
                new StringElement("2"),
                new LongElement(3L),
                new StringElement("4")
        );

        // WHEN
        List<MatchResult> matches = cut.matches(elements, false);

        // THEN
        assertEquals(List.of(
                MatchResult.full(1, 2, new TypePatternMatcher.Match(elements.get(1))),
                MatchResult.full(3, 4, new TypePatternMatcher.Match(elements.get(3)))),
                matches);
    }

    @Test
    void matchesSubType() {
        // GIVEN
        TypePatternMatcher cut = new TypePatternMatcher(LongElement.class);
        List<Element> elements = List.of(
                new SubLongElement(1L),
                new StringElement("x")
        );

        // WHEN
        List<MatchResult> matches = cut.matches(elements, false);

        // THEN
        assertEquals(List.of(
                MatchResult.full(0, 1, new TypePatternMatcher.Match(elements.get(0)))),
                matches);
    }

    private static class SubLongElement extends LongElement {
        public SubLongElement(long value) {
            super(value);
        }
    }
}