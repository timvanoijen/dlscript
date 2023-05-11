package nl.timocode.dlscript.parser;

import java.io.IOException;
import java.io.Reader;
import java.util.Objects;
import java.util.Optional;

public final class ParseElementReader {

    private final Reader reader;

    private boolean initialized = false;
    private int currentChar;

    public ParseElementReader(Reader reader) {
        this.reader = reader;
    }

    public Optional<ParseElement<?>> read() throws IOException {

        ensureInitialized();
        StringBuilder s = new StringBuilder();
        Boolean isDigit = null;
        while(currentChar != -1) {
            if (Character.isWhitespace(currentChar)) {
                if (isDigit != null) {
                    break;
                }
            } else if (!Character.isLetterOrDigit(currentChar)) {
                // Special character: return as string per single character
                if (isDigit == null) {
                    isDigit = false;
                    s.append((char)currentChar);
                    currentChar = reader.read();
                }
                break;
            } else {
                // Letter of digit: return as string or long per group
                boolean isAlpha = !Character.isDigit(currentChar);
                if (Objects.equals(isAlpha, isDigit)) {
                    break;
                }
                isDigit = !isAlpha;
                s.append((char)currentChar);
            }
            currentChar = reader.read();
        }
        return isDigit == null
                ? Optional.empty()
                : Optional.of(isDigit
                    ? new ParseElement<>(Long.parseLong(s.toString()), Long.class)
                    : new ParseElement<>(s.toString(), String.class));
    }

    public boolean hasNext() throws IOException {
        ensureInitialized();
        return currentChar != -1;
    }

    public void reset() throws IOException {
        reader.reset();
        initialized = false;
    }

    private void ensureInitialized() throws IOException {
        if (initialized) {
            return;
        }
        currentChar = reader.read();
        initialized = true;
    }
}
