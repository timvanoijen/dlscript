package nl.timocode.dlscript.lang.types;

import nl.timocode.dlscript.parser.Parser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class TypeTest {

    private final Parser parser = new Parser();

    @Test
    void testFull() throws IOException {
        // GIVEN
        String input = """
               type Car includes Vehicle, Entity {
                   var name: String
                   
                   fun break(a: double = 1.0): double {
                       var a : double = 123.45
                   }
                   
                   var color: Color
                   
                   fun accellerate(a: double) {
                   }
                   
                   fun honk() {
                   }
               }
                """;

        // WHEN
        Object result = parser.parse(new StringReader(input));

        // THEN

        // General assertions
        assertTrue(result instanceof Type);
        Type type = (Type) result;
        assertEquals("Car", type.getName());

        // Field assertions
        assertEquals(2, type.getFields().size());
        assertEquals("String", type.getFields().get(0).getType());
        assertEquals("name", type.getFields().get(0).getName());
        assertEquals("Color", type.getFields().get(1).getType());
        assertEquals("color", type.getFields().get(1).getName());

        // Include assertions
        assertEquals(2, type.getChildTypes().size());
        assertEquals("Vehicle", type.getChildTypes().get(0));
        assertEquals("Entity", type.getChildTypes().get(1));

        // Method assertions
        assertEquals(3, type.getMethods().size());
        assertEquals("break", type.getMethods().get(0).getName());
        assertEquals("accellerate", type.getMethods().get(1).getName());
        assertEquals("honk", type.getMethods().get(2).getName());
    }

    @Test
    void testMinimal() throws IOException {
        // GIVEN
        String input = "type Void {}";

        // WHEN
        Object result = parser.parse(new StringReader(input));

        // THEN

        assertTrue(result instanceof Type);
        Type type = (Type) result;
        assertEquals("Void", type.getName());
        assertEquals(0, type.getFields().size());
        assertEquals(0, type.getChildTypes().size());
    }
}