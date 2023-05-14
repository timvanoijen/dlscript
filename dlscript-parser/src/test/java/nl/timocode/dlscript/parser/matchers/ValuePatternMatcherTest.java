package nl.timocode.dlscript.parser.matchers;

import nl.timocode.dlscript.parser.Element;
import nl.timocode.dlscript.parser.primitives.LongElement;
import nl.timocode.dlscript.parser.primitives.StringElement;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValuePatternMatcherTest {

    @Test
    void matches() {
        // GIVEN
        ValuePatternMatcher<LongElement> cut = new ValuePatternMatcher<>(new LongElement(3L));
        List<Element> elements = List.of(
                new LongElement(1L),
                new StringElement("2"),
                new LongElement(3L)
        );

        // WHEN
        List<PatternMatch> matches = cut.matches(elements);

        // THEN
        assertEquals(List.of(PatternMatch.fullMatch(2, 3)), matches);
    }
}