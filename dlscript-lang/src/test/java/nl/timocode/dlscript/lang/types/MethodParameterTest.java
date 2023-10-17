package nl.timocode.dlscript.lang.types;

import nl.timocode.dlscript.parser.Parser;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class MethodParameterTest {

    private final Parser parser = new Parser();

    @ParameterizedTest
    @CsvSource({
            "a: double = 123.45, a, double",
            "b: String, b, String"
    })
    void parse(String input, String expectedName, String expectedType) throws IOException {
        // WHEN
        Object result = parser.parse(new StringReader(input));

        // THEN
        assertTrue(result instanceof MethodParameter);
        MethodParameter methodParameter = (MethodParameter) result;
        assertEquals(expectedName, methodParameter.getName());
        assertEquals(expectedType, methodParameter.getType());
        if (methodParameter.getDefaultValue() != null) {
            assertEquals(methodParameter.getType(), methodParameter.getDefaultValue().getType());
        }
    }
}