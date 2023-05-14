package nl.timocode.dlscript.parser.matchers;

import nl.timocode.dlscript.parser.Element;

import java.util.List;

public interface PatternMatcher {
    List<PatternMatch> matches(List<? extends Element> elements);
}
