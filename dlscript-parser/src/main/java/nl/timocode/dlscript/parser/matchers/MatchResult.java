package nl.timocode.dlscript.parser.matchers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Getter
public class MatchResult {

    private final boolean isFullMatch;
    private final int startElementIdx;
    private final int endElementIdx;
    private final Match match;

    static MatchResult partial(int startElementIdx) {
        return new MatchResult(false, startElementIdx, Integer.MAX_VALUE, null);
    }

    static MatchResult full(int startElementIdx, int endElementIdx, Match match) {
        return new MatchResult(true, startElementIdx, endElementIdx, match);
    }
}
