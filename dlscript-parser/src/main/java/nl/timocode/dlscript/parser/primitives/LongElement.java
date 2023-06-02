package nl.timocode.dlscript.parser.primitives;

import lombok.Data;
import nl.timocode.dlscript.parser.Element;

@Data
public class LongElement implements Element {

    private final long value;
}
