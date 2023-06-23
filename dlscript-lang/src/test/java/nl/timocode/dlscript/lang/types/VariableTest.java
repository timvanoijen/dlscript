package nl.timocode.dlscript.lang.types;

import nl.timocode.dlscript.parser.Parser;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class VariableTest {

    private final Parser parser = new Parser();

    @ParameterizedTest
    @CsvSource({
            "var abc: string, abc, string",
            "var cow: animal, cow, animal"
    })
    void testFull(String input, String expectedName, String expectedType) throws IOException {
        // WHEN
        Object result = parser.parse(new StringReader(input));

        // THEN
        assertTrue(result instanceof Variable);
        assertEquals(expectedName, ((Variable) result).getName());
        assertEquals(expectedType, ((Variable) result).getType());
    }
}