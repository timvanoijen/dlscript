package nl.timocode.dlscript.parser.matchers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.function.Consumer;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Getter
public class MatchResult<C> {

    private final boolean isFullMatch;
    private final int startElementIdx;
    private final int endElementIdx;
    private final Consumer<C> acceptResultCollector;

    static <C> MatchResult<C> partial(int startElementIdx) {
        return new MatchResult<>(false, startElementIdx, Integer.MAX_VALUE, null);
    }

    static <C> MatchResult<C> full(int startElementIdx, int endElementIdx, Consumer<C> consumeFn) {
        return new MatchResult<>(true, startElementIdx, endElementIdx, consumeFn);
    }

    public void consume(C consumer) {
        acceptResultCollector.accept(consumer);
    }

    public MatchResult<C> followedBy(MatchResult<C> next) {
        assert next.startElementIdx == this.endElementIdx;
        return new MatchResult<>(next.isFullMatch, this.startElementIdx, next.endElementIdx, c -> {
            this.consume(c);
            next.consume(c);
        });
    }

    public MatchResult<C> shiftElementIndices(int n) {
        return new MatchResult<>(isFullMatch, this.startElementIdx + n, this.endElementIdx + n,
                acceptResultCollector);
    }

    @Override
    public String toString() {
        return "MatchResult{" +
                "isFullMatch=" + isFullMatch +
                ", startElementIdx=" + startElementIdx +
                ", endElementIdx=" + endElementIdx +
                '}';
    }
}
