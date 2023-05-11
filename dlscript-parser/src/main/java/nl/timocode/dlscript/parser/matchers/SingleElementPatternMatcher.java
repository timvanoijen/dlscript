package nl.timocode.dlscript.parser.matchers;

import nl.timocode.dlscript.parser.ParseElement;

import java.util.ArrayList;
import java.util.List;


public abstract class SingleElementPatternMatcher implements PatternMatcher {

    protected abstract boolean matches(ParseElement<?> element);

    @Override
    public List<PatternMatch> matches(List<? extends ParseElement<?>> elements) {
        List<PatternMatch> matches = new ArrayList<>();
        for(int i = 0; i < elements.size(); i++) {
            ParseElement<?> element = elements.get(i);
            if (matches(element)) {
                matches.add(PatternMatch.fullMatch(i, i + 1));
            }
        }
        return matches;
    }
}
