package nl.timocode.dlscript.lang.types;

import nl.timocode.dlscript.parser.Parser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class MethodTest {

    private final Parser parser = new Parser();

    @Test
    void testFull() throws IOException {
        // GIVEN
        String input = """
               fun foo(a: MyType, b: int = 5, c : String = "animal"): double {
                    var a : double = 123.45
                    var b : String = "hoihoi"
               }
                """;

        // WHEN
        Object result = parser.parse(new StringReader(input));

        // THEN

        // General assertions
        assertTrue(result instanceof Method);
        Method method = (Method) result;
        assertEquals("foo", method.getName());
        assertEquals("double", method.getReturnType());

        // Parameters
        assertEquals(3, method.getParameters().size());

        // Statements
        assertEquals(2, method.getStatements().size());
    }


}