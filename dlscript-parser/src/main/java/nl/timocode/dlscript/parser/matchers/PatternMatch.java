package nl.timocode.dlscript.parser.matchers;

public record PatternMatch(boolean fullMatch, int startElement, int endElement) {

    public static PatternMatch fullMatch(int startElement, int endElement) {
        return new PatternMatch(true, startElement, endElement);
    }

    public static PatternMatch partialMatch(int startElement) {
        return new PatternMatch(false, startElement, Integer.MAX_VALUE);
    }
}
