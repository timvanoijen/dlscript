package nl.timocode.dlscript.parser.primitives;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.timocode.dlscript.parser.Element;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class StringElement implements Element {

    private final String value;
}
