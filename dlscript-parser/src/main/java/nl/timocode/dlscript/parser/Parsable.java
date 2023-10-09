package nl.timocode.dlscript.parser;

import nl.timocode.dlscript.parser.matchers.PatternMatcher;

public interface Parsable<E extends Element, B extends ElementBuilder<E>> {

    PatternMatcher<B> patternMatcher();
    B createBuilder();

    default int parsePriority() {
        return 0;
    }
}
