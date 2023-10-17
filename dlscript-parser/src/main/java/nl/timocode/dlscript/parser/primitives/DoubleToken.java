package nl.timocode.dlscript.parser.primitives;

import lombok.Data;
import nl.timocode.dlscript.parser.Element;

@Data
public class DoubleToken implements Element {
    private final double value;
}
