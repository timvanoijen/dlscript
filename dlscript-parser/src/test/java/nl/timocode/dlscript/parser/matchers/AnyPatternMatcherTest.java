package nl.timocode.dlscript.parser.matchers;

import nl.timocode.dlscript.parser.ParseElement;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnyPatternMatcherTest {

    @Test
    void matches() {
        // GIVEN
        AnyPatternMatcher cut = AnyPatternMatcher.of(
                new TypePatternMatcher(Long.class),
                new TypePatternMatcher(String.class)
        );
        List<ParseElement> elements = List.of(
                new ParseElement(1L, Long.class),
                new ParseElement("1", String.class),
                new ParseElement(3L, Long.class),
                new ParseElement(4.0, Double.class));

        // WHEN
        List<PatternMatch> matches = cut.matches(elements);

        // THEN
        PatternMatch expected1 = PatternMatch.fullMatch(0, 1);
        PatternMatch expected2 = PatternMatch.fullMatch(2, 3);
        PatternMatch expected3 = PatternMatch.fullMatch(1, 2);

        assertEquals(List.of(expected1, expected2, expected3), matches);
    }
}