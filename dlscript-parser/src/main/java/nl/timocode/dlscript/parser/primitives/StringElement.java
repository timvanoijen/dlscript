package nl.timocode.dlscript.parser.primitives;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import nl.timocode.dlscript.parser.Element;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class StringElement implements Element {

    private final String value;
}
