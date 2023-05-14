package nl.timocode.dlscript.parser;

import nl.timocode.dlscript.parser.expressions.Expression;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    private static final double EPSILON = 0.00000001;

    private final Parser cut = new Parser();

    @ParameterizedTest
    @CsvSource({
            "3, 3.0",
            "3 + 5, 8.0",
            "3 + 5 * 2, 13.0",
            "3 * 5 + 2, 17.0",
            "3 + 5 + 2, 10.0",
            "3 * 5 * 2, 30.0",
            "1 + 3 * 5 + 4, 20.0",
            "(1 + 3) * ((5 + 4) * 2 + 1), 76.0",
            "123.40 + 123.40, 246.80",
            "-8 * (1 + 3), -32.0",
            "5.6 * -(3 * 2 /4 + 16 - 13.4), -22.96"
    })
    void parse(String input, double expectedResult) throws IOException {
        // WHEN
        Object result = cut.parse(new StringReader(input));

        // THEN
        assertTrue(result instanceof Expression);
        assertTrue(Math.abs(expectedResult - ((Expression)result).eval()) < EPSILON);
    }

}