package ch.epfl.atran.ecs;

import ch.epfl.atran.event.EntityObserver;

import java.util.Optional;
import java.util.Set;

public interface Entity {

    long id();
    void addComponent(EntityComponent component);
    void addComponents(EntityComponent... components);
    void removeComponent(EntityComponent component);
    Set<? extends EntityComponent> components();
    Set<EntityObserver> observers();
    void addObserver(EntityObserver observer);
    void removeObserver(EntityObserver observer);

    default <T extends EntityComponent> T get(Class<T> clazz) {
        return getIfPresent(clazz).get();
    }

    @SuppressWarnings("unchecked")
    default <T extends EntityComponent> Optional<T> getIfPresent(Class<T> clazz) {
        return (Optional<T>) components()
                .stream()
                .filter(c -> c.getClass().equals(clazz))
                .findAny();
    }

    default boolean hasComponent(Class<? extends EntityComponent> clazz) {
        return components()
                .stream()
                .anyMatch(c -> c.getClass().equals(clazz));
    }
}
