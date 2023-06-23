package nl.timocode.dlscript.parser;

import nl.timocode.dlscript.parser.primitives.LongElement;
import nl.timocode.dlscript.parser.primitives.StringElement;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ElementReaderTest {

    private final Parser cut = new Parser();

    @Test
    void read() {
        // GIVEN
        String input = String.join(System.lineSeparator(),
                "12.34ab cd56 89#{ef",
                "gh ! @0 123456789()+-*/^");
        ElementReader cut = new ElementReader(new StringReader(input));

        // WHEN
        List<? extends Element> elements = Stream.generate(() -> readWithoutCheckedException(cut))
                .takeWhile(Optional::isPresent).map(o -> o.orElse(null)).toList();

        // THEN
        assertEquals(List.of(
                new LongElement(12L),
                new StringElement("."),
                new LongElement(34L),
                new StringElement("ab"),
                new StringElement("cd"),
                new LongElement(56L),
                new LongElement(89L),
                new StringElement("#"),
                new StringElement("{"),
                new StringElement("ef"),
                new StringElement("gh"),
                new StringElement("!"),
                new StringElement("@"),
                new LongElement(0L),
                new LongElement(123456789L),
                new StringElement("("),
                new StringElement(")"),
                new StringElement("+"),
                new StringElement("-"),
                new StringElement("*"),
                new StringElement("/"),
                new StringElement("^")
        ), elements);
    }

    @Test
    void reset() throws IOException {
        // GIVEN
        String input = "12ab";
        ElementReader cut = new ElementReader(new StringReader(input));
        cut.read();

        // WHEN
        cut.reset();

        // THEN
        assertEquals(Optional.of(new LongElement(12L)), cut.read());
        assertEquals(Optional.of(new StringElement("ab")), cut.read());
        assertEquals(Optional.empty(), cut.read());
    }

    private Optional<Element> readWithoutCheckedException(ElementReader reader) {
        try {
            return reader.read();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}