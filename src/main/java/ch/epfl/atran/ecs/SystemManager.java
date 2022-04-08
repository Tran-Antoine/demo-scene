package ch.epfl.atran.ecs;

import ch.epfl.atran.event.EntityObserver;
import ch.epfl.atran.event.FrameUpdatable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

public class SystemManager implements FrameUpdatable, EntityObserver {

    private final Set<Entity> entities;
    private final Set<EntitySystem> systems;

    private final Set<Entity> newlyAdded;
    private final Set<Entity> newlyUpdated;
    private final Set<Entity> newlyRemoved;

    public SystemManager() {
        this(new EntitySystem[0]);
    }

    public SystemManager(EntitySystem... systems) {
        this.entities = new HashSet<>();
        this.systems = new HashSet<>(Arrays.asList(systems));
        this.newlyAdded = new HashSet<>();
        this.newlyUpdated = new HashSet<>();
        this.newlyRemoved = new HashSet<>();
    }

    public void addEntity(Entity entity) {
        newlyAdded.add(entity);
        entity.addObserver(this);
    }

    public void removeEntity(Entity entity) {
        newlyRemoved.add(entity);
        entity.removeObserver(this);
    }

    public Entity createEntity() {
        Entity entity = new DefaultEntity();
        addEntity(entity);
        return entity;
    }

    public Entity createEntity(Component... components) {
        Entity entity = new DefaultEntity();
        entity.addComponents(components);
        addEntity(entity);
        return entity;
    }

    public EntitySystem addSystem(EntitySystem system) {
        systems.add(system);
        return system;
    }

    public EntitySystem removeSystem(EntitySystem system) {
        systems.remove(system);
        return system;
    }

    @Override
    public void update(float tpf) {
        handleUnchangedEntities(tpf);

        handleNewEntities(tpf);
        handleUpdatedEntities(tpf);
        handleRemovedEntities(tpf);
    }

    private void handleNewEntities(float tpf) {
        handle((system, entity) -> system.onEntityAdded(entity, tpf), newlyAdded);
        entities.addAll(newlyAdded);
        newlyAdded.clear();
    }

    private void handleUpdatedEntities(float tpf) {
        handle((system, entity) -> system.onEntityUpdated(entity, tpf), newlyUpdated);
        newlyUpdated.clear();
    }


    private void handleRemovedEntities(float tpf) {
        handle((system, entity) -> system.onEntityRemoved(entity, tpf), newlyRemoved);
        entities.removeAll(newlyRemoved);
        newlyRemoved.clear();
    }

    private void handleUnchangedEntities(float tpf) {
        handle((system, entity) -> system.onUpdate(entity, tpf), entities);
    }

    private void handle(BiConsumer<EntitySystem, Entity> ifHandle, Iterable<Entity> coll) {
        for(EntitySystem system : systems) {
            for(Entity entity : coll) {
                if(system.canHandle(entity)) {
                    ifHandle.accept(system, entity);
                }
            }
        }
    }

    @Override
    public void onNotify(Entity observed) {
        newlyUpdated.add(observed);
    }
}
