package nl.timocode.dlscript.parser.expressions;

import nl.timocode.dlscript.parser.Element;

public interface Expression extends Element {
    double eval();
}
