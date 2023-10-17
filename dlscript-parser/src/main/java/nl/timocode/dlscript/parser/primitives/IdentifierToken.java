package nl.timocode.dlscript.parser.primitives;

import lombok.Data;
import nl.timocode.dlscript.parser.Element;

@Data
public class IdentifierToken implements Element {
    private final String value;
}
