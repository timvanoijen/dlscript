package nl.timocode.dlscript.parser.expressions;

import nl.timocode.dlscript.parser.ParseElement;
import nl.timocode.dlscript.parser.matchers.PatternMatcher;
import nl.timocode.dlscript.parser.matchers.SequencePatternMatcher;
import nl.timocode.dlscript.parser.matchers.TypePatternMatcher;
import nl.timocode.dlscript.parser.matchers.ValuePatternMatcher;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MultiplicationTest {

    @Test
    void eval() {
        // GIVEN
        Multiplication cut = new Multiplication(new Constant(3), new Constant(4));

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
        List<ParseElement<?>> elements = List.of(
                new ParseElement<>(new Constant(5), Constant.class),
                new ParseElement<>("*", String.class),
                new ParseElement<>(new Constant(6), Constant.class));

        // WHEN
        Multiplication multiplication = cut.create(elements);

        // THEN
        assertEquals(30.0, multiplication.eval());
    }
}