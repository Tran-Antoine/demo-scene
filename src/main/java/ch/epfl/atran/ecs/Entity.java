package ch.epfl.atran.ecs;

import ch.epfl.atran.event.EntityObserver;

import java.util.Optional;
import java.util.Set;

public interface Entity {

    long id();
    void addComponent(Component component);
    void addComponents(Component... components);
    void removeComponent(Component component);
    Set<? extends Component> components();
    Set<EntityObserver> observers();
    void addObserver(EntityObserver observer);
    void removeObserver(EntityObserver observer);

    default <T extends Component> T get(Class<T> clazz) {
        return getIfPresent(clazz).get();
    }

    @SuppressWarnings("unchecked")
    default <T extends Component> Optional<T> getIfPresent(Class<T> clazz) {
        return (Optional<T>) components()
                .stream()
                .filter(c -> c.getClass().equals(clazz))
                .findAny();
    }

    default boolean hasComponent(Class<? extends Component> clazz) {
        return components()
                .stream()
                .anyMatch(c -> c.getClass().equals(clazz));
    }
}
