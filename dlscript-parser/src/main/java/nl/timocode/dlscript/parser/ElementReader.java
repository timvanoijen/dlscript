package nl.timocode.dlscript.parser;

import nl.timocode.dlscript.parser.primitives.*;

import java.io.IOException;
import java.io.Reader;
import java.util.Optional;
import java.util.Set;

public final class ElementReader {

    private static final Set<Character> IDENTIFIERCHARS = Set.of('_', '$');

    private enum Token { LONG, DOUBLE, IDENTIFIER, STRINGLITERAL, NON_AN_CHAR }

    private final Reader reader;

    private boolean initialized = false;
    private int currentCharInt;

    public ElementReader(Reader reader) {
        this.reader = reader;
    }

    public Optional<Element> read() throws IOException {

        ensureInitialized();
        StringBuilder s = new StringBuilder();
        Token curToken = null;
        while (currentCharInt != -1) {

            char currentChar = (char) currentCharInt;
            if (curToken == Token.LONG) {
                if (currentChar == '.') {
                    curToken = Token.DOUBLE;
                } else if (!Character.isDigit(currentChar)) {
                    break;
                }
                s.append(currentChar);
            } else if (curToken == Token.IDENTIFIER) {
                if (!Character.isLetterOrDigit(currentChar) && !IDENTIFIERCHARS.contains(currentChar)) {
                    break;
                }
                s.append(currentChar);
            } else if (curToken == Token.STRINGLITERAL) {
                if (currentChar == '"') {
                    currentCharInt = reader.read();
                    break;
                }
                s.append(currentChar);
            } else if (curToken == Token.DOUBLE) {
                if (!Character.isDigit(currentChar)) {
                    break;
                }
                s.append(currentChar);
            } else {
                // curToken is null
                if (Character.isDigit(currentChar)) {
                    curToken = Token.LONG;
                    s.append(currentChar);
                } else if (currentChar == '"') {
                    curToken = Token.STRINGLITERAL;
                } else if (Character.isLetter(currentChar) || IDENTIFIERCHARS.contains(currentChar)) {
                    curToken = Token.IDENTIFIER;
                    s.append(currentChar);
                } else if (!Character.isWhitespace(currentChar)) {
                    curToken = Token.NON_AN_CHAR;
                    s.append(currentChar);
                    currentCharInt = reader.read();
                    break;
                }
            }
            currentCharInt = reader.read();
        }

        return curToken == null ? Optional.empty() : Optional.of(switch(curToken) {
            case LONG -> new LongToken(Long.parseLong(s.toString()));
            case DOUBLE -> new DoubleToken(Double.parseDouble(s.toString()));
            case IDENTIFIER -> new IdentifierToken(s.toString());
            case NON_AN_CHAR -> new CharToken(s.charAt(0));
            case STRINGLITERAL -> new StringLiteralToken(s.toString());
        });
    }

    public boolean hasNext() throws IOException {
        ensureInitialized();
        return currentCharInt != -1;
    }

    public void reset() throws IOException {
        reader.reset();
        initialized = false;
    }

    private void ensureInitialized() throws IOException {
        if (initialized) {
            return;
        }
        currentCharInt = reader.read();
        initialized = true;
    }
}
