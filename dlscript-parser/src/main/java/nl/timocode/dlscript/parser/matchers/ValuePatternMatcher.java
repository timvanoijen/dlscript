package nl.timocode.dlscript.parser.matchers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import nl.timocode.dlscript.parser.Element;
import nl.timocode.dlscript.parser.utils.OptionalBiConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class ValuePatternMatcher<E extends Element, C> implements PatternMatcher<C> {

    private final E value;
    private final OptionalBiConsumer<C, E> biConsumer;

    public static <E extends Element, C> ValuePatternMatcher<E, C> of(E value, BiConsumer<C, E> biConsumer) {
        return new ValuePatternMatcher<>(value, OptionalBiConsumer.of(biConsumer));
    }

    public static <E extends Element, C> ValuePatternMatcher<E, C> of(E value) {
        return new ValuePatternMatcher<>(value, OptionalBiConsumer.empty());
    }

    @Override
    public List<MatchResult<C>> matches(List<? extends Element> elements, boolean fromStart) {
        List<MatchResult<C>> matches = new ArrayList<>();
        for(int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            if (value.getClass().isAssignableFrom(element.getClass()) && value.equals(element)) {
                //noinspection unchecked
                matches.add(MatchResult.full(i, i + 1, biConsumer.applySecond((E) element)));
            }
            if (fromStart) {
                break;
            }
        }
        return matches;
    }
}