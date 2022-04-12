package ch.epfl.atran.ecs;

import ch.epfl.atran.event.EntityObserver;

import java.util.Optional;
import java.util.Set;

/**
 * Container of {@link EntityComponent}s handled by {@link EntitySystem}s.
 */
public interface Entity {

    /**
     * @return the unique ID of the entity
     */
    long id();

    /**
     * Adds a component to the entity. This may lead to {@link EntitySystem#onEntityUpdated(Entity, float)} calls
     * by entity systems that can handle the entity, in the next system manager update.
     * @param component the component to add
     */
    void addComponent(EntityComponent component);

    /**
     * Adds multiple components, but triggering at most one {@link EntitySystem#onEntityUpdated(Entity, float)} call
     * per system.
     *
     * @see Entity#addComponent(EntityComponent)
     * @param components the components to add
     */
    void addComponents(EntityComponent... components);

    /**
     * Removes a component from the entity. This may lead to {@link EntitySystem#onEntityUpdated(Entity, float)} calls
     * by entity systems that can handle the entity, in the next system manager update.
     * @param component the component to remove
     */
    void removeComponent(EntityComponent component);

    /**
     * Retrieves the component list of the entity.
     * @return a set containing the components of the entity
     */
    Set<? extends EntityComponent> components();

    /**
     * @return the set of observers that the entity triggers when updated
     */
    Set<EntityObserver> observers();

    /**
     * Adds an observer to the observer list.
     * @param observer the observer to add
     */
    void addObserver(EntityObserver observer);

    /**
     * Removes an observer from the observer list.
     * @param observer the observer to remove
     */
    void removeObserver(EntityObserver observer);

    /**
     * Retrieves the component of the given type. If no component is found, a {@link java.util.NoSuchElementException}
     * is thrown.
     * @param clazz The class object describing {@code T}
     * @param <T> the type of the component
     * @return the component matching the given type
     */
    default <T extends EntityComponent> T get(Class<T> clazz) {
        return getIfPresent(clazz).get();
    }

    /**
     * Retrieves the component of the given type. Differs from {@link Entity#get} only in that it returns an empty
     * optional instead of throwing an error if the component cannot be found.
     * @param clazz The class object describing {@code T}
     * @param <T> the type of the component
     * @return the component matching the given type wrapped in an optional if present, otherwise an empty optional
     */
    @SuppressWarnings("unchecked")
    default <T extends EntityComponent> Optional<T> getIfPresent(Class<T> clazz) {
        return (Optional<T>) components()
                .stream()
                .filter(c -> c.getClass().equals(clazz))
                .findAny();
    }

    /**
     * @param clazz
     * @return whether the entity contains a component of type {@code clazz}
     */
    default boolean hasComponent(Class<? extends EntityComponent> clazz) {
        return components()
                .stream()
                .anyMatch(c -> c.getClass().equals(clazz));
    }
}
