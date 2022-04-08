package ch.epfl.atran.ecs;

import ch.epfl.atran.event.EntityObserver;

import java.util.*;

public class DefaultEntity implements Entity {

    private static long count = 0;

    private final Set<Component> components;
    private final Set<EntityObserver> observers;
    private final long id;

    public DefaultEntity() {
        this.components = new HashSet<>();
        this.observers = new HashSet<>();
        this.id = count++;
    }

    @Override
    public long id() {
        return id;
    }

    @Override
    public void addComponent(Component component) {
        components.add(component);
        notifyObservers();
    }

    @Override
    public void addComponents(Component... components) {
        this.components.addAll(Arrays.asList(components));
        notifyObservers();
    }

    @Override
    public void removeComponent(Component component) {
        components.remove(component);
        notifyObservers();
    }

    @Override
    public Set<? extends Component> components() {
        return Collections.unmodifiableSet(components);
    }

    @Override
    public Set<EntityObserver> observers() {
        return Collections.unmodifiableSet(observers);
    }

    @Override
    public void addObserver(EntityObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(EntityObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for(EntityObserver observer : observers) {
            observer.onNotify(this);
        }
    }

    @Override
    public String toString() {
        return "DefaultEntity{%s}".formatted(components);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultEntity that = (DefaultEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
