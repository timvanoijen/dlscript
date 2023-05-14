package nl.timocode.dlscript.parser.expressions;

import nl.timocode.dlscript.parser.ParseElement;
import nl.timocode.dlscript.parser.matchers.PatternMatcher;
import nl.timocode.dlscript.parser.matchers.SequencePatternMatcher;
import nl.timocode.dlscript.parser.matchers.TypePatternMatcher;
import nl.timocode.dlscript.parser.matchers.ValuePatternMatcher;
import nl.timocode.dlscript.parser.primitives.DoubleElement;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MultiplicationTest {

    @Test
    void eval() {
        // GIVEN
        Multiplication cut = new Multiplication(new DoubleElement(3), new DoubleElement(4));

        // WHEN
        double result = cut.eval();

        // THEN
        assertEquals(12, result);
    }

    @Test
    void typePatternMatcher() {
        // GIVEN
        Multiplication.Type cut = new Multiplication.Type();

        // WHEN
        PatternMatcher matcher = cut.patternMatcher();

        // THEN
        assertEquals(SequencePatternMatcher.of(
                new TypePatternMatcher(Expression.class),
                new ValuePatternMatcher<>("*"),
                new TypePatternMatcher(Expression.class)), matcher);
    }

    @Test
    void typeCreate() {
        // GIVEN
        Multiplication.Type cut = new Multiplication.Type();
        List<ParseElement> elements = List.of(
                new ParseElement(new DoubleElement(5), DoubleElement.class),
                new ParseElement("*", String.class),
                new ParseElement(new DoubleElement(6), DoubleElement.class));

        // WHEN
        Multiplication multiplication = cut.create(elements);

        // THEN
        assertEquals(30.0, multiplication.eval());
    }
}