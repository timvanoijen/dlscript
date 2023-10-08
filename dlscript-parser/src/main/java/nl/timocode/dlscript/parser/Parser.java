package nl.timocode.dlscript.parser;

import nl.timocode.dlscript.parser.matchers.MatchResult;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Parser {

    private final List<Parsable<?,?>> elementTypes;

    public Parser() {
        elementTypes = new ArrayList<>();
        ServiceLoader.load(Parsable.class).iterator().forEachRemaining(elementTypes::add);
    }

    record TypeWithMatch<E extends Element, B extends ElementBuilder<E>>(
            Parsable<E, B> type,
            MatchResult<B> matchResult) {}

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

                // Find types that matchResult a pattern in the stack and sort on highest end element and then on lowest
                // start element

                // Type safety is guaranteed but Java's type inference is not smart enough, therefore we have to fall
                // back to some raw types instead of wildcard types.
                //noinspection unchecked, rawtypes
                List<TypeWithMatch> matches = elementTypes.stream()
                        .flatMap(type ->
                                type.patternMatcher().matches(finalStack, false).stream()
                                        .map(match -> new TypeWithMatch(type, match)))
                        .sorted(Comparator.<TypeWithMatch>comparingInt(twm -> -twm.matchResult().getEndElementIdx())
                                .thenComparing(twm -> twm.matchResult().getStartElementIdx()))
                        .collect(Collectors.toCollection(ArrayList::new));

                // Find first full matchResult
                OptionalInt firstFullMatchIdxOpt = IntStream.range(0, matches.size())
                        .filter(i -> matches.get(i).matchResult().isFullMatch()).findFirst();
                if (firstFullMatchIdxOpt.isEmpty()) {
                    break;
                }

                int firstFullMatchIdx = firstFullMatchIdxOpt.getAsInt();
                TypeWithMatch<?,?> selected = matches.get(firstFullMatchIdx);

                // Find out if there is a partial matchResult with a higher priority that overlaps with the first full
                // match.
                boolean potentialBetterCandidate = elementReader.hasNext() &&
                        IntStream.range(0, firstFullMatchIdx)
                            .mapToObj(matches::get)
                            .anyMatch(partialMt ->
                                partialMt.matchResult().getStartElementIdx() < selected.matchResult().getEndElementIdx() &&
                                partialMt.type().parsePriority() > selected.type().parsePriority()
                            );

                if (potentialBetterCandidate) {
                    break;
                }

                // If not, replace the full matchResult with the new type
                Element newElement = process(selected);
                List<Element> newStack = new ArrayList<>(stack.subList(0, selected.matchResult().getStartElementIdx()));
                newStack.add(newElement);
                newStack.addAll(stack.subList(selected.matchResult().getEndElementIdx(), stack.size()));
                stack = newStack;
            }
        }

        if (stack.size() != 1) {
            return new ParseException("Cannot parse input", -1);
        }

        return stack.get(0);
    }

    private <E extends Element, B extends ElementBuilder<E>> E process(TypeWithMatch<E, B> typeWithMatch) {
        B builder = typeWithMatch.type().createBuilder();
        typeWithMatch.matchResult.consume(builder);
        return builder.build();
    }
}
