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
        ValuePattern.Matcher cut = ValuePattern.Matcher.of(new LongElement(3L));
        List<Element> elements = List.of(
                new LongElement(1L),
                new StringElement("2"),
                new LongElement(3L)
        );

        // WHEN
        List<MatchResult> matches = cut.matches(elements, false);

        // THEN
        assertEquals(List.of(
                MatchResult.full(2, 3, new ValuePattern(elements.get(2)))),
                matches);
    }
}