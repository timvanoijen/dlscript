package nl.timocode.dlscript.parser;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ParseElementReaderTest {

    @Test
    void read() {
        // GIVEN
        String input = String.join(System.lineSeparator(),
                "12.34ab cd56 89#{ef",
                "gh ! @0 123456789()+-*/^");
        ParseElementReader cut = new ParseElementReader(new StringReader(input));

        // WHEN
        List<? extends ParseElement> elements = Stream.generate(() -> readWithoutCheckedException(cut))
                .takeWhile(Optional::isPresent).map(o -> o.orElse(null)).toList();

        // THEN
        assertEquals(List.of(
                new ParseElement(12L, Long.class),
                new ParseElement(".", String.class),
                new ParseElement(34L, Long.class),
                new ParseElement("ab", String.class),
                new ParseElement("cd", String.class),
                new ParseElement(56L, Long.class),
                new ParseElement(89L, Long.class),
                new ParseElement("#", String.class),
                new ParseElement("{", String.class),
                new ParseElement("ef", String.class),
                new ParseElement("gh", String.class),
                new ParseElement("!", String.class),
                new ParseElement("@", String.class),
                new ParseElement(0L, Long.class),
                new ParseElement(123456789L, Long.class),
                new ParseElement("(", String.class),
                new ParseElement(")", String.class),
                new ParseElement("+", String.class),
                new ParseElement("-", String.class),
                new ParseElement("*", String.class),
                new ParseElement("/", String.class),
                new ParseElement("^", String.class)
        ), elements);
    }

    @Test
    void reset() throws IOException {
        // GIVEN
        String input = "12ab";
        ParseElementReader cut = new ParseElementReader(new StringReader(input));
        cut.read();

        // WHEN
        cut.reset();

        // THEN
        assertEquals(Optional.of(new ParseElement(12L, Long.class)), cut.read());
        assertEquals(Optional.of(new ParseElement("ab", String.class)), cut.read());
        assertEquals(Optional.empty(), cut.read());
    }

    private Optional<ParseElement> readWithoutCheckedException(ParseElementReader reader) {
        try {
            return reader.read();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}