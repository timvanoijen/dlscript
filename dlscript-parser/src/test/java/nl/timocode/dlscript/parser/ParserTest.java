package nl.timocode.dlscript.parser;

import nl.timocode.dlscript.parser.expressions.Expression;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    private final Parser cut = new Parser();

    @ParameterizedTest
    @CsvSource({
            "3 + 5 * 2, 13.0",
            "3 * 5 + 2, 17.0",
            "3 + 5 + 2, 10.0",
            "3 * 5 * 2, 30.0",
            "1 + 3 * 5 + 4, 20.0",
            "(1 + 3) * ((5 + 4) * 2 + 1), 76.0",
    })
    void parse(String input, double expectedResult) throws IOException {
        // WHEN
        Object result = cut.parse(new StringReader(input));

        // THEN
        assertTrue(result instanceof Expression);
        assertEquals(expectedResult, ((Expression)result).eval());
    }

}