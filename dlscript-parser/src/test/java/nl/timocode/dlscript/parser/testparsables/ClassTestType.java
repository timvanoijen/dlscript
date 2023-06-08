package nl.timocode.dlscript.parser.testparsables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.*;
import nl.timocode.dlscript.parser.primitives.StringElement;
import nl.timocode.dlscript.parser.testelements.ClassFieldTest;
import nl.timocode.dlscript.parser.testelements.ClassTest;

@AllArgsConstructor
@Getter
public class ClassTestType implements Parsable<ClassTest, ClassTest.Builder> {

    @Override
    public PatternMatcher<ClassTest.Builder> patternMatcher() {
        return SequencePatternMatcher.of(
                ValuePatternMatcher.of(new StringElement("class")),
                TypePatternMatcher.of(StringElement.class, ClassTest.Builder::setName),
                ValuePatternMatcher.of(new StringElement("{")),
                RepeatingPatternMatcher.of(
                        TypePatternMatcher.of(ClassFieldTest.class, ClassTest.Builder::addField)
                ),
                ValuePatternMatcher.of(new StringElement("}"))
        );
    }

    @Override
    public ClassTest.Builder createBuilder() {
        return new ClassTest.Builder();
    }
}
