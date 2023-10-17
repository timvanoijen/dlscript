package nl.timocode.dlscript.parser.primitives;

import lombok.Data;
import nl.timocode.dlscript.parser.Element;

@Data
public class CharToken implements Element {
    private final char value;
}
