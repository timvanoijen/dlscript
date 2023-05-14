package nl.timocode.dlscript.parser.matchers;

import nl.timocode.dlscript.parser.ParseElement;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TypePatternMatcherTest {

    @Test
    void matchesSameType() {
        // GIVEN
        TypePatternMatcher cut = new TypePatternMatcher(Integer.class);
        List<ParseElement> elements = List.of(
                new ParseElement(1L, Long.class),
                new ParseElement(2, Integer.class),
                new ParseElement(3L, Long.class),
                new ParseElement(4, Integer.class)
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
        TypePatternMatcher cut = new TypePatternMatcher(Number.class);
        List<ParseElement> elements = List.of(
                new ParseElement(1L, Long.class),
                new ParseElement("x", String.class)
        );

        // WHEN
        List<PatternMatch> matches = cut.matches(elements);

        // THEN
        assertEquals(List.of(PatternMatch.fullMatch(0, 1)), matches);
    }
}