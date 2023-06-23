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
                   var color: Color
               }
                """;

        // WHEN
        Object result = parser.parse(new StringReader(input));

        // THEN

        // General assertions
        assertTrue(result instanceof Type);
        assertEquals("Car", ((Type) result).getName());

        // Field assertions
        assertEquals(2, ((Type) result).getFields().size());
        assertEquals("String", ((Type) result).getFields().get(0).getType());
        assertEquals("name", ((Type) result).getFields().get(0).getName());
        assertEquals("Color", ((Type) result).getFields().get(1).getType());
        assertEquals("color", ((Type) result).getFields().get(1).getName());

        // Include assertions
        assertEquals(2, ((Type) result).getChildTypes().size());
        assertEquals("Vehicle", ((Type) result).getChildTypes().get(0));
        assertEquals("Entity", ((Type) result).getChildTypes().get(1));
    }

    @Test
    void testMinimal() throws IOException {
        // GIVEN
        String input = "type Void {}";

        // WHEN
        Object result = parser.parse(new StringReader(input));

        // THEN

        assertTrue(result instanceof Type);
        assertEquals("Void", ((Type) result).getName());
        assertEquals(0, ((Type) result).getFields().size());
        assertEquals(0, ((Type) result).getChildTypes().size());
    }
}