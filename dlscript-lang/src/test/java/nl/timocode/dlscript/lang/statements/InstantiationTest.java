package nl.timocode.dlscript.lang.statements;

import nl.timocode.dlscript.parser.Parser;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class InstantiationTest {

    private final Parser parser = new Parser();

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
            Foo(a = 132.14, field2 = 20)| Foo | a | field2
            Dog()                       | Dog |   |
    """)
    void parse(String input, String expectedType, String expectedField1, String expectedField2) throws IOException {
        // WHEN
        Object result = parser.parse(new StringReader(input));

        // THEN
        assertTrue(result instanceof Instantiation);
        assertEquals(expectedType, ((Instantiation) result).getType());
        if (expectedField1 != null) {
            assertTrue(((Instantiation) result).getFieldValues().containsKey(expectedField1));
        }
        if (expectedField2 != null) {
            assertTrue(((Instantiation) result).getFieldValues().containsKey(expectedField2));
        }
    }
}