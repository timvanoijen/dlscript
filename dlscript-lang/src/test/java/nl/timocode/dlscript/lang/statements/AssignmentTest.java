package nl.timocode.dlscript.lang.statements;

import nl.timocode.dlscript.parser.Parser;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class AssignmentTest {

    private final Parser parser = new Parser();

    @ParameterizedTest
    @CsvSource({
            "var a : double = 123.45, a, double",
            "foo = 14, foo, "
    })
    void parse(String input, String expectedName, String expectedType) throws IOException {
        // WHEN
        Object result = parser.parse(new StringReader(input));

        // THEN
        assertTrue(result instanceof Assignment);
        assertEquals(expectedName, ((Assignment) result).getName());
        assertEquals(expectedType, ((Assignment) result).getType());
        assertEquals(expectedType != null, ((Assignment) result).isInitialisation());
        assertEquals(expectedType != null ? Assignment.Instantiation.class : Assignment.NoInstantiation.class, result.getClass());
    }
}