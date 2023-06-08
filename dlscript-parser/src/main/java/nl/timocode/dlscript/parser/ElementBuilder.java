package nl.timocode.dlscript.parser;

@FunctionalInterface
public interface ElementBuilder<E extends Element> {
    E build();
}
