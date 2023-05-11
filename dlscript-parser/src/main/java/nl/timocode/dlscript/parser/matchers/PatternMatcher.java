package nl.timocode.dlscript.parser.matchers;

import nl.timocode.dlscript.parser.ParseElement;

import java.util.List;

public interface PatternMatcher {
    List<PatternMatch> matches(List<? extends ParseElement> elements);
}
