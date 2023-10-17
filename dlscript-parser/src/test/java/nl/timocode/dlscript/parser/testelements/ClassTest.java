package nl.timocode.dlscript.parser.testelements;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import nl.timocode.dlscript.parser.Element;
import nl.timocode.dlscript.parser.ElementBuilder;
import nl.timocode.dlscript.parser.primitives.IdentifierToken;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class ClassTest implements Element {

    private final List<ClassFieldTest> fields;
    private final String name;

    @Data
    public static class Builder implements ElementBuilder<ClassTest> {

        private final List<ClassFieldTest> fields = new ArrayList<>();
        private IdentifierToken name;

        public void addField(ClassFieldTest field) {
            fields.add(field);
        }

        @Override
        public ClassTest build() {
            return new ClassTest(List.copyOf(fields), name.getValue());
        }
    }
}
