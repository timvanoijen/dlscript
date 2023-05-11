package nl.timocode.dlscript.parser;

import nl.timocode.dlscript.parser.matchers.PatternMatcher;

import java.util.List;

public interface Parsable<T> {

    PatternMatcher patternMatcher();

    T create(List<ParseElement> elements);

    default int parsePriority() {
        return Integer.MAX_VALUE;
    }
}
