package nl.timocode.dlscript.parser.testparsables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.*;
import nl.timocode.dlscript.parser.primitives.IdentifierToken;
import nl.timocode.dlscript.parser.testelements.ClassFieldTest;

@AllArgsConstructor
@Getter
public class ClassFieldTestType implements Parsable<ClassFieldTest, ClassFieldTest.Builder> {

    @Override
    public PatternMatcher<ClassFieldTest.Builder> patternMatcher() {
        return SequencePatternMatcher.of(
                OptionalPatternMatcher.of(
                        AnyPatternMatcher.of(
                                ValuePatternMatcher.of(new IdentifierToken("private"), ClassFieldTest.Builder::setAccessibility),
                                ValuePatternMatcher.of(new IdentifierToken("protected"), ClassFieldTest.Builder::setAccessibility),
                                ValuePatternMatcher.of(new IdentifierToken("public"), ClassFieldTest.Builder::setAccessibility)
                        )
                ),
                ValuePatternMatcher.of(new IdentifierToken("var")),
                TypePatternMatcher.of(IdentifierToken.class, ClassFieldTest.Builder::setType),
                TypePatternMatcher.of(IdentifierToken.class, ClassFieldTest.Builder::setName)
        );
    }

    @Override
    public ClassFieldTest.Builder createBuilder() {
        return new ClassFieldTest.Builder();
    }
}
