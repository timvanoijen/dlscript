package nl.timocode.dlscript.parser;

@FunctionalInterface
public interface ElementBuilder<E extends Element> {
    E build();
    default <T> void noop(T t) {}
}
