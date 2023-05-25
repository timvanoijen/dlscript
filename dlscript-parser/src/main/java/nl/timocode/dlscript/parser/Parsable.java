package nl.timocode.dlscript.parser;

import nl.timocode.dlscript.parser.matchers.Match;
import nl.timocode.dlscript.parser.matchers.PatternMatcher;

public interface Parsable<T extends Element> {

    PatternMatcher patternMatcher();

    T create(Match match);

    default int parsePriority() {
        return Integer.MAX_VALUE;
    }
}
