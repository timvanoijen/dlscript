package nl.timocode.dlscript.parser.primitives;

import lombok.Data;
import nl.timocode.dlscript.parser.Element;

@Data
public class LongToken implements Element {
    private final long value;
}
