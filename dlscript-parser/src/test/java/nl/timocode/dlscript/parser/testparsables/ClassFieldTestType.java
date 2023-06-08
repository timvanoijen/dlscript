package nl.timocode.dlscript.parser.testparsables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.*;
import nl.timocode.dlscript.parser.primitives.StringElement;
import nl.timocode.dlscript.parser.testelements.ClassFieldTest;

@AllArgsConstructor
@Getter
public class ClassFieldTestType implements Parsable<ClassFieldTest, ClassFieldTest.Builder> {

    @Override
    public PatternMatcher<ClassFieldTest.Builder> patternMatcher() {
        return SequencePatternMatcher.of(
                OptionalPatternMatcher.of(
                        AnyPatternMatcher.of(
                                ValuePatternMatcher.of(new StringElement("private"), ClassFieldTest.Builder::setAccessibility),
                                ValuePatternMatcher.of(new StringElement("protected"), ClassFieldTest.Builder::setAccessibility),
                                ValuePatternMatcher.of(new StringElement("public"), ClassFieldTest.Builder::setAccessibility)
                        )
                ),
                ValuePatternMatcher.of(new StringElement("var")),
                TypePatternMatcher.of(StringElement.class, ClassFieldTest.Builder::setType),
                TypePatternMatcher.of(StringElement.class, ClassFieldTest.Builder::setName)
        );
    }

    @Override
    public ClassFieldTest.Builder createBuilder() {
        return new ClassFieldTest.Builder();
    }
}
