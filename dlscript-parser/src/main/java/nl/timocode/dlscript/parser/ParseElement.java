package nl.timocode.dlscript.parser;

public record ParseElement<T>(T value, Class<? extends T> type) {
}
