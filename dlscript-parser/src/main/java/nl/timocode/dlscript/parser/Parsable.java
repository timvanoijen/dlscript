package nl.timocode.dlscript.parser;

import nl.timocode.dlscript.parser.matchers.Pattern;
import nl.timocode.dlscript.parser.matchers.PatternMatcher;

public interface Parsable<T extends Element> {

    PatternMatcher patternMatcher();

    T create(Pattern pattern);

    default int parsePriority() {
        return Integer.MAX_VALUE;
    }
}
