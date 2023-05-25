package nl.timocode.dlscript.parser.matchers;

import nl.timocode.dlscript.parser.Element;
import nl.timocode.dlscript.parser.primitives.LongElement;
import nl.timocode.dlscript.parser.primitives.StringElement;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OptionalPatternMatcherTest {

    @Test
    void matchesWithInnerSingleElementPatternMatcher() {
        // GIVEN
        PatternMatcher innerMatcher = new ValuePatternMatcher<>(new LongElement(3L));
        OptionalPatternMatcher cut = new OptionalPatternMatcher(innerMatcher);
        List<Element> elements = List.of(
                new LongElement(1L),
                new StringElement("2"),
                new LongElement(3L)
        );

        // WHEN
        List<MatchResult> matches = cut.matches(elements, false);

        // THEN
        assertEquals(List.of(
                MatchResult.full(0, 0, OptionalPatternMatcher.Match.empty()),
                MatchResult.full(1, 1, OptionalPatternMatcher.Match.empty()),
                MatchResult.full(2, 2, OptionalPatternMatcher.Match.empty()),
                MatchResult.full(2, 3, OptionalPatternMatcher.Match.of(
                        new ValuePatternMatcher.Match(new LongElement(3L))))),
                matches);
    }

    @Test
    void matchesWithInnerSequencePatternMatcher() {
        // GIVEN
        PatternMatcher innerMatcher = SequencePatternMatcher.of(
                new ValuePatternMatcher<>(new StringElement("a")),
                new TypePatternMatcher(StringElement.class));
        OptionalPatternMatcher cut = new OptionalPatternMatcher(innerMatcher);
        List<Element> elements = List.of(
                new LongElement(1L),
                new StringElement("a"),
                new StringElement("b"),
                new StringElement("a"),
                new LongElement(1L),
                new StringElement("a")
                );

        // WHEN
        List<MatchResult> matches = cut.matches(elements, false);

        // THEN
        assertEquals(List.of(
                        MatchResult.full(0, 0, OptionalPatternMatcher.Match.empty()),
                        MatchResult.full(1, 1, OptionalPatternMatcher.Match.empty()),
                        MatchResult.full(2, 2, OptionalPatternMatcher.Match.empty()),
                        MatchResult.full(3, 3, OptionalPatternMatcher.Match.empty()),
                        MatchResult.full(4, 4, OptionalPatternMatcher.Match.empty()),
                        MatchResult.full(5, 5, OptionalPatternMatcher.Match.empty()),
                        MatchResult.partial(5),
                        MatchResult.full(1, 3, OptionalPatternMatcher.Match.of(
                                new SequencePatternMatcher.Match(List.of(
                                        new ValuePatternMatcher.Match(new StringElement("a")),
                                        new TypePatternMatcher.Match(new StringElement("b"))
                                        ))
                                ))
                ),
                matches);
    }
}
