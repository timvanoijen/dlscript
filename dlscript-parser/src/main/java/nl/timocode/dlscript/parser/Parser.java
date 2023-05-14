package nl.timocode.dlscript.parser;

import nl.timocode.dlscript.parser.expressions.*;
import nl.timocode.dlscript.parser.primitives.DoubleElement;
import nl.timocode.dlscript.parser.matchers.PatternMatch;
import nl.timocode.dlscript.parser.primitives.LongElement;
import nl.timocode.dlscript.parser.primitives.StringElement;


import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Parser {

    record TypeWithMatch<T extends Element>(Parsable<T> type, PatternMatch match) {}

    private static final List<? extends Parsable<?>> ELEMENT_TYPES = List.of(
            new LongElement.Type(),
            new StringElement.Type(),
            new DoubleElement.Type(),
            new SubExpression.Type(),
            new Sum.Type(),
            new Subtraction.Type(),
            new Multiplication.Type(),
            new Division.Type(),
            new Negation.Type()
    );

    public Object parse(Reader reader) throws IOException {
        ElementReader elementReader = new ElementReader(reader);

        Optional<Element> elementOpt;
        List<Element> stack = new ArrayList<>();

        while((elementOpt = elementReader.read()).isPresent()) {

            // Add new element to stack
            Element element = elementOpt.get();
            stack.add(element);

            while(true) {
                List<Element> finalStack = stack;

                // Find types that match a pattern in the stack and sort on highest end element and then on lowest
                // start element
                List<? extends TypeWithMatch<?>> matches = ELEMENT_TYPES.stream()
                        .flatMap(type ->
                                type.patternMatcher().matches(finalStack).stream()
                                        .map(match -> new TypeWithMatch<>(type, match)))
                        .sorted(Comparator.<TypeWithMatch<?>>comparingInt(twm -> -twm.match().endElement())
                                .thenComparing(twm -> twm.match().startElement()))
                        .collect(Collectors.toCollection(ArrayList::new));

                // Find first full match
                OptionalInt firstFullMatchIdxOpt = IntStream.range(0, matches.size())
                        .filter(i -> matches.get(i).match().fullMatch()).findFirst();
                if (firstFullMatchIdxOpt.isEmpty()) {
                    break;
                }

                int firstFullMatchIdx = firstFullMatchIdxOpt.getAsInt();
                TypeWithMatch<?> selected = matches.get(firstFullMatchIdx);

                // Find out if there is a partial match with a higher priority
                boolean potentialBetterCandidate = elementReader.hasNext() &&
                        IntStream.range(0, firstFullMatchIdx)
                            .mapToObj(i -> matches.get(i).type())
                            .anyMatch(partialMatchType -> partialMatchType.parsePriority() > selected.type().parsePriority());

                if (potentialBetterCandidate) {
                    break;
                }

                // If not, replace the full match with the new type
                Element newElement = createElementFromTypeWithMatch(selected, stack);
                List<Element> newStack = new ArrayList<>();
                newStack.addAll(stack.subList(0, selected.match().startElement()));
                newStack.add(newElement);
                newStack.addAll(stack.subList(selected.match().endElement(), stack.size()));
                stack = newStack;
            }
        }

        if (stack.size() != 1) {
            return new ParseException("Cannot parse input", -1);
        }

        return stack.get(0);
    }

    private Element createElementFromTypeWithMatch(TypeWithMatch<?> typeWithMatch,
                                                   List<Element> stack) {
        return typeWithMatch.type().create(stack.subList(
                typeWithMatch.match().startElement(), typeWithMatch.match().endElement()));
    }
}
