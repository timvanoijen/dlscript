package nl.timocode.dlscript.parser.matchers;

import nl.timocode.dlscript.parser.ParseElement;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValuePatternMatcherTest {

    @Test
    void matches() {
        // GIVEN
        ValuePatternMatcher<Integer> cut = new ValuePatternMatcher<>(3);
        List<ParseElement<?>> elements = Arrays.asList(
                new ParseElement<>(3L, Long.class),
                new ParseElement<>(2, Integer.class),
                new ParseElement<>(3, Integer.class)
        );

        // WHEN
        List<PatternMatch> matches = cut.matches(elements);

        // THEN
        assertEquals(Collections.singletonList(PatternMatch.fullMatch(2, 3)), matches);
    }
}