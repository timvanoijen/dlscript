package nl.timocode.dlscript.parser.testelements;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import nl.timocode.dlscript.parser.Element;
import nl.timocode.dlscript.parser.ElementBuilder;
import nl.timocode.dlscript.parser.primitives.StringElement;

@AllArgsConstructor
@Getter
@ToString
public class ClassFieldTest implements Element {
    private final String accessibility;
    private final String type;
    private final String name;

    @Data
    public static class Builder implements ElementBuilder<ClassFieldTest> {

        private StringElement accessibility = new StringElement("private");
        private StringElement type;
        private StringElement name;

        @Override
        public ClassFieldTest build() {
            return new ClassFieldTest(accessibility.getValue(), type.getValue(), name.getValue());
        }
    }
}
