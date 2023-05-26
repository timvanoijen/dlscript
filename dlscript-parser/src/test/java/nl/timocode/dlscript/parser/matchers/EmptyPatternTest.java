package nl.timocode.dlscript.parser.matchers;

import nl.timocode.dlscript.parser.Element;
import nl.timocode.dlscript.parser.primitives.LongElement;
import nl.timocode.dlscript.parser.primitives.StringElement;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmptyPatternTest {

    @Test
    void matches() {
        // GIVEN
        EmptyPattern.Matcher cut = new EmptyPattern.Matcher();
        List<Element> elements = List.of(
                new LongElement(1L),
                new StringElement("2"),
                new LongElement(3L)
        );

        // WHEN
        List<MatchResult> matches = cut.matches(elements, false);

        // THEN
        assertEquals(List.of(
                        MatchResult.full(0, 0, EmptyPattern.INSTANCE),
                        MatchResult.full(1, 1, EmptyPattern.INSTANCE),
                        MatchResult.full(2, 2, EmptyPattern.INSTANCE)),
                matches);
    }
}