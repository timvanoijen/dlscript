package nl.timocode.dlscript.parser;

import nl.timocode.dlscript.parser.primitives.DoubleElement;
import nl.timocode.dlscript.parser.primitives.LongElement;
import nl.timocode.dlscript.parser.testparsables.MaxTestType;
import nl.timocode.dlscript.parser.testparsables.MultiplicationTestType;
import nl.timocode.dlscript.parser.testparsables.SumTestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    private final Parser cut = new Parser(List.of(
            new SumTestType(),
            new MultiplicationTestType(),
            new MaxTestType()
    ));

    @ParameterizedTest
    @CsvSource({
            "3 + 5, 8",
            "3 + 5 + 2, 10",
            "3 * 5 + 2, 17",
            "3 + 5 * 2, 13",
            "3 * 5 * 2, 30"
    })
    void parseWithLongs(String input, long expectedResult) throws IOException {
        // WHEN
        Object result = cut.parse(new StringReader(input));

        // THEN
        assertTrue(result instanceof LongElement);
        assertEquals(expectedResult, ((LongElement) result).getValue());
    }

    @ParameterizedTest
    @CsvSource({
            "8.0, 8.0",
            "'max(16.0, 12.0)', 16.0"
    })
    void parseWithDoubles(String input, double expectedResult) throws IOException {
        // WHEN
        Object result = cut.parse(new StringReader(input));

        // THEN
        assertTrue(result instanceof DoubleElement);
        assertEquals(expectedResult, ((DoubleElement) result).getValue());
    }
}