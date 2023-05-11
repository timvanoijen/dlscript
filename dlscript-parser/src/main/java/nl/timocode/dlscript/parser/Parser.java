package nl.timocode.dlscript.parser;

import nl.timocode.dlscript.parser.expressions.Constant;
import nl.timocode.dlscript.parser.expressions.Multiplication;
import nl.timocode.dlscript.parser.expressions.SubExpression;
import nl.timocode.dlscript.parser.expressions.Sum;
import nl.timocode.dlscript.parser.matchers.PatternMatch;


import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Parser {

    record TypeWithMatch<T>(Parsable<T> type, PatternMatch match) {}

    private static final List<? extends Parsable<?>> ELEMENT_TYPES = List.of(
            new Constant.Type(),
            new SubExpression.Type(),
            new Sum.Type(),
            new Multiplication.Type()
    );

    public Object parse(Reader reader) throws IOException {
        ParseElementReader elementReader = new ParseElementReader(reader);

        Optional<ParseElement<?>> elementOpt;
        List<ParseElement<?>> stack = new ArrayList<>();

        while((elementOpt = elementReader.read()).isPresent()) {

            // Add new element to stack
            ParseElement<?> element = elementOpt.get();
            stack.add(element);

            while(true) {
                List<ParseElement<?>> finalStack = stack;

                // Find types that match a pattern in the stack
                List<TypeWithMatch> matches = ELEMENT_TYPES.stream()
                        .flatMap(type ->
                                type.patternMatcher().matches(finalStack).stream()
                                        .map(match -> new TypeWithMatch(type, match)))
                        .collect(Collectors.toCollection(ArrayList::new));

                // Order matches on highest end element and then on lowest start element
                matches.sort(Comparator.<TypeWithMatch>comparingInt(twm -> -twm.match().endElement())
                        .thenComparing(twm -> twm.match().startElement()));

                // Find first full match
                OptionalInt firstFullMatchIdxOpt = IntStream.range(0, matches.size())
                        .filter(i -> matches.get(i).match().fullMatch()).findFirst();
                if (firstFullMatchIdxOpt.isEmpty()) {
                    break;
                }

                int firstFullMatchIdx = firstFullMatchIdxOpt.getAsInt();
                TypeWithMatch selected = matches.get(firstFullMatchIdx);

                // Find out if there is a partial match with a higher priority
                boolean potentialBetterCandidate = elementReader.hasNext() &&
                        IntStream.range(0, firstFullMatchIdx)
                            .mapToObj(i -> matches.get(i).type())
                            .anyMatch(partialMatchType -> partialMatchType.parsePriority() > selected.type().parsePriority());

                if (potentialBetterCandidate) {
                    break;
                }

                // If not, replace the full match with the new type
                ParseElement<?> newElement = createParseElementFromTypeWithMatch(selected, stack);
                List<ParseElement<?>> newStack = new ArrayList<>();
                newStack.addAll(stack.subList(0, selected.match().startElement()));
                newStack.add(newElement);
                newStack.addAll(stack.subList(selected.match().endElement(), stack.size()));
                stack = newStack;
            }
        }

        if (stack.size() != 1) {
            return new ParseException("Cannot parse input", -1);
        }

        return stack.get(0).value();
    }

    private ParseElement<Object> createParseElementFromTypeWithMatch(TypeWithMatch<Object> typeWithMatch,
                                                                    List<ParseElement<?>> stack) {
        Object element = typeWithMatch.type().create(stack.subList(
                typeWithMatch.match().startElement(), typeWithMatch.match().endElement()));
        return new ParseElement<>(element, element.getClass());
    }
}
