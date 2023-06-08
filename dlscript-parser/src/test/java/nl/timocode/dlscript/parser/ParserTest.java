package nl.timocode.dlscript.parser;

import nl.timocode.dlscript.parser.primitives.DoubleElement;
import nl.timocode.dlscript.parser.primitives.LongElement;
import nl.timocode.dlscript.parser.testelements.ClassFieldTest;
import nl.timocode.dlscript.parser.testelements.ClassTest;
import nl.timocode.dlscript.parser.testparsables.*;
import org.junit.jupiter.api.Test;
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
            new MaxTestType(),
            new ClassTestType(),
            new ClassFieldTestType()
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

    @ParameterizedTest
    @CsvSource({
            "private var String a, private, String, a",
            "public var Animal animal, public, Animal, animal",
            "var Object o, private, Object, o",
    })
    void parseWithAllTypesOfPatternMatcher(String input, String expectedAccessibility, String expectedType,
                                           String expectedName) throws IOException {
        // WHEN
        Object result = cut.parse(new StringReader(input));

        // THEN
        assertTrue(result instanceof ClassFieldTest);
        assertEquals(expectedAccessibility, ((ClassFieldTest) result).getAccessibility());
        assertEquals(expectedType, ((ClassFieldTest) result).getType());
        assertEquals(expectedName, ((ClassFieldTest) result).getName());
    }

    @Test
    void parseWithRepeatingPatternMatcher() throws IOException {
        // GIVEN
        String input = """
               class MyType {
                   private var String name
                   private var Integer value
                   public var Foo f
                   var Animal a
               }
                """;

        // WHEN
        Object result = cut.parse(new StringReader(input));

        // THEN
        assertTrue(result instanceof ClassTest);
        assertEquals("name", ((ClassTest) result).getName());
        assertEquals(4, ((ClassTest) result).getFields().size());
    }
}