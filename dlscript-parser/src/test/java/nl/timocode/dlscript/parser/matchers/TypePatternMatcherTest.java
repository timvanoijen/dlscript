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
        List<PatternMatch> matches = cut.matches(elements);

        // THEN
        assertEquals(List.of(
                PatternMatch.fullMatch(1, 2),
                PatternMatch.fullMatch(3, 4)), matches);
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
        List<PatternMatch> matches = cut.matches(elements);

        // THEN
        assertEquals(List.of(PatternMatch.fullMatch(0, 1)), matches);
    }

    private static class SubLongElement extends LongElement {
        public SubLongElement(long value) {
            super(value);
        }
    }
}