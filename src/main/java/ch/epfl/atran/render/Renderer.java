package ch.epfl.atran.render;

public interface Renderer<T> {

    void prepare(T obj);
    void render(T obj);
    void cleanUp(T obj);

    default void prepare(Iterable<? extends T> coll) {
        coll.forEach(this::prepare);
    }

    default void render(Iterable<? extends T> coll) {
        coll.forEach(this::render);
    }

    default void cleanUp(Iterable<? extends T> coll) {
        coll.forEach(this::cleanUp);
    }
}
