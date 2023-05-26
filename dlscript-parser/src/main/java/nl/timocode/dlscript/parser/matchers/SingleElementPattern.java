package nl.timocode.dlscript.parser.matchers;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.timocode.dlscript.parser.Element;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public abstract class SingleElementPattern implements Pattern {

    private final Element element;

    public static abstract class Matcher<T extends SingleElementPattern> implements PatternMatcher {

        protected abstract T createPattern(Element element);

        protected abstract boolean matches(Element element);

        @Override
        public List<MatchResult> matches(List<? extends Element> elements, boolean fromStart) {
            List<MatchResult> matches = new ArrayList<>();
            for(int i = 0; i < elements.size(); i++) {
                Element element = elements.get(i);
                if (matches(element)) {
                    matches.add(MatchResult.full(i, i + 1, createPattern(element)));
                }
                if (fromStart) {
                    break;
                }
            }
            return matches;
        }
    }
}
