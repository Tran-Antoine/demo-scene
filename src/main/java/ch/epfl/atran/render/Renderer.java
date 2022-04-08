package ch.epfl.atran.render;

public interface Renderer<T> {

    void prepare(T obj);
    void render(T obj, ShaderProgram program);
    void cleanUp(T obj);

    default void prepare(Iterable<? extends T> coll) {
        coll.forEach(this::prepare);
    }

    default void render(Iterable<? extends T> coll, ShaderProgram shader) {
        coll.forEach(e -> render(e, shader));
    }

    default void cleanUp(Iterable<? extends T> coll) {
        coll.forEach(this::cleanUp);
    }
}
