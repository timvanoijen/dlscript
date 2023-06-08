package nl.timocode.dlscript.parser.matchers;

import nl.timocode.dlscript.parser.Element;

import java.util.ArrayList;
import java.util.List;

public class EmptyPatternMatcher<C> implements PatternMatcher<C> {

    @Override
    public List<MatchResult<C>> matches(List<? extends Element> elements, boolean fromStart) {

        List<MatchResult<C>> result = new ArrayList<>();
        for(int i = 0; i < (fromStart ? 1 : elements.size()); i++) {
            result.add(MatchResult.full(i, i, c -> {}));
            if (fromStart) {
                break;
            }
        }
        return result;
    }
}
