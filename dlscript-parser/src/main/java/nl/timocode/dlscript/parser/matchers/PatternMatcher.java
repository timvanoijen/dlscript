package nl.timocode.dlscript.parser.matchers;

import nl.timocode.dlscript.parser.Element;

import java.util.List;

public interface PatternMatcher {
    List<MatchResult> matches(List<? extends Element> elements, boolean fromStart);
}
