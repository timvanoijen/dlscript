package nl.timocode.dlscript.lang.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import nl.timocode.dlscript.parser.Element;
import nl.timocode.dlscript.parser.ElementBuilder;
import nl.timocode.dlscript.parser.Parsable;
import nl.timocode.dlscript.parser.matchers.*;
import nl.timocode.dlscript.parser.primitives.CharToken;
import nl.timocode.dlscript.parser.primitives.IdentifierToken;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public final class Type implements Element {

    private final String name;
    private final List<Variable> fields;
    private final List<String> childTypes;
    private final List<Method> methods;

    @Data
    public static class Builder implements ElementBuilder<Type> {

        private IdentifierToken nameElement;
        private final List<Variable> fields = new ArrayList<>();
        private final List<String> childTypes = new ArrayList<>();
        private final List<Method> methods = new ArrayList<>();

        public void addField(Variable field) {
            fields.add(field);
        }

        public void addChildType(IdentifierToken typeNameElement) {
            childTypes.add(typeNameElement.getValue());
        }

        public void addMethod(Method method) {
            methods.add(method);
        }

        @Override
        public Type build() {
            return new Type(nameElement.getValue(), List.copyOf(fields), List.copyOf(childTypes), List.copyOf(methods));
        }
    }

    public static class _Type implements Parsable<Type, Type.Builder> {

        @Override
        public PatternMatcher<Builder> patternMatcher() {
            return SequencePatternMatcher.of(
                    // Header
                    ValuePatternMatcher.of(new IdentifierToken("type")),
                    TypePatternMatcher.of(IdentifierToken.class, Builder::setNameElement),
                    OptionalPatternMatcher.of(
                            SequencePatternMatcher.of(
                                    ValuePatternMatcher.of(new IdentifierToken("includes")),
                                    RepeatingPatternMatcher.of(
                                            TypePatternMatcher.of(IdentifierToken.class, Builder::addChildType)
                                    ).withDelimiter(',')
                            )
                    ),
                    // Fields and methods
                    ValuePatternMatcher.of(new CharToken('{')),
                    OptionalPatternMatcher.of(
                        RepeatingPatternMatcher.of(
                                AnyPatternMatcher.of(
                                    TypePatternMatcher.of(Variable.class, Builder::addField),
                                    TypePatternMatcher.of(Method.class, Builder::addMethod)
                                )
                        )
                    ),
                    ValuePatternMatcher.of(new CharToken('}'))
            );
        }

        @Override
        public Builder createBuilder() {
            return new Builder();
        }
    }
}
