package nl.timocode.dlscript.parser.matchers;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.timocode.dlscript.parser.Element;

import java.util.ArrayList;
import java.util.List;

public abstract class SingleElementPatternMatcher implements PatternMatcher {

    protected abstract boolean matches(Element element);

    @Override
    public List<MatchResult> matches(List<? extends Element> elements, boolean fromStart) {
        List<MatchResult> matches = new ArrayList<>();
        for(int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            if (matches(element)) {
                matches.add(MatchResult.full(i, i + 1, new Match(element)));
            }
            if (fromStart) {
                break;
            }
        }
        return matches;
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    public static class Match implements nl.timocode.dlscript.parser.matchers.Match {
        private final Element element;
    }
}
