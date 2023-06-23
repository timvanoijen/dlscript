package nl.timocode.dlscript.lang.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import nl.timocode.dlscript.parser.Element;
import nl.timocode.dlscript.parser.ElementBuilder;
import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.*;
import nl.timocode.dlscript.parser.primitives.StringElement;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public final class Type implements Element {

    private final String name;
    private final List<Variable> fields;
    private final List<String> childTypes;

    @Data
    public static class Builder implements ElementBuilder<Type> {

        private StringElement nameElement;
        private final List<Variable> fields = new ArrayList<>();
        private final List<String> childTypes = new ArrayList<>();

        public void addField(Variable field) {
            fields.add(field);
        }

        public void addChildType(StringElement typeNameElement) {
            childTypes.add(typeNameElement.getValue());
        }

        @Override
        public Type build() {
            return new Type(nameElement.getValue(), List.copyOf(fields), List.copyOf(childTypes));
        }
    }

    public static class _Type implements Parsable<Type, Type.Builder> {

        @Override
        public PatternMatcher<Builder> patternMatcher() {
            return SequencePatternMatcher.of(
                    // Header
                    ValuePatternMatcher.of(new StringElement("type")),
                    TypePatternMatcher.of(StringElement.class, Builder::setNameElement),
                    OptionalPatternMatcher.of(
                            SequencePatternMatcher.of(
                                ValuePatternMatcher.of(new StringElement("includes")),
                                TypePatternMatcher.of(StringElement.class, Builder::addChildType),
                                OptionalPatternMatcher.of(
                                    RepeatingPatternMatcher.of(
                                            SequencePatternMatcher.of(
                                                    ValuePatternMatcher.of(new StringElement(",")),
                                                    TypePatternMatcher.of(StringElement.class, Builder::addChildType)
                                            )
                                    )
                                )
                            )
                    ),
                    // Fields
                    ValuePatternMatcher.of(new StringElement("{")),
                    OptionalPatternMatcher.of(
                        RepeatingPatternMatcher.of(
                                TypePatternMatcher.of(Variable.class, Builder::addField)
                        )
                    ),
                    ValuePatternMatcher.of(new StringElement("}"))
            );
        }

        @Override
        public Builder createBuilder() {
            return new Builder();
        }
    }

}
