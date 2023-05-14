package nl.timocode.dlscript.parser.matchers;

import nl.timocode.dlscript.parser.Element;

import java.util.ArrayList;
import java.util.List;

public abstract class SingleElementPatternMatcher implements PatternMatcher {

    protected abstract boolean matches(Element element);

    @Override
    public List<PatternMatch> matches(List<? extends Element> elements) {
        List<PatternMatch> matches = new ArrayList<>();
        for(int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            if (matches(element)) {
                matches.add(PatternMatch.fullMatch(i, i + 1));
            }
        }
        return matches;
    }
}
