package nl.timocode.dlscript.parser;

import nl.timocode.dlscript.parser.primitives.*;
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
                "12.34ab cd1_5$6 89#{ef",
                "123456789()\"a!@2'\"");
        ElementReader cut = new ElementReader(new StringReader(input));

        // WHEN
        List<? extends Element> elements = Stream.generate(() -> readWithoutCheckedException(cut))
                .takeWhile(Optional::isPresent).map(o -> o.orElse(null)).toList();

        // THEN
        assertEquals(List.of(
                new DoubleToken(12.34),
                new IdentifierToken("ab"),
                new IdentifierToken("cd1_5$6"),
                new LongToken(89),
                new CharToken('#'),
                new CharToken('{'),
                new IdentifierToken("ef"),
                new LongToken(123456789),
                new CharToken('('),
                new CharToken(')'),
                new StringLiteralToken("a!@2'")
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
        assertEquals(Optional.of(new LongToken(12L)), cut.read());
        assertEquals(Optional.of(new IdentifierToken("ab")), cut.read());
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