package ch.epfl.atran.ecs;

import java.util.List;

/**
 * System handling entities, that is triggered by entity addition, deletion and update.
 */
public interface EntitySystem {

    /**
     * Defines the procedure to apply when an entity is added to the manager.
     *
     * The method is declared as default for convenience purposes, and its default implementation does nothing.
     *
     * @param entity the entity added
     * @param tpf the time per frame value, defined as the elapsed time in seconds between the last and current call.
     */
    default void onEntityAdded(Entity entity, float tpf) {}

    /**
     * Defines the procedure to apply when the component list of an entity is updated, with the requirement that
     * the system can still handle the entity even given the new component list.
     *
     * The method is declared as default for convenience purposes, and its default implementation does nothing.
     *
     * @param entity the entity whose component list was changed
     * @param tpf the time per frame value, defined as the elapsed time in seconds between the last and current call.
     */
    default void onEntityUpdated(Entity entity, float tpf) {}

    /**
     * Defines the procedure to apply when an entity is removed from the manager.
     *
     * The method is declared as default for convenience purposes, and its default implementation does nothing.
     *
     * @param entity the entity removed
     * @param tpf the time per frame value, defined as the elapsed time in seconds between the last and current call.
     */
    default void onEntityRemoved(Entity entity, float tpf) {}

    /**
     * Defines the procedure to apply everytime an entity in the list can be handled and the system manager is updated
     *
     * The method is declared as default for convenience purposes, and its default implementation does nothing.
     *
     * @param entity the entity which fulfills the requirements for the system (via {@link EntitySystem#canHandle(Entity)})
     * @param tpf the time per frame value, defined as the elapsed time in seconds between the last and current call.
     */
    default void onUpdate(Entity entity, float tpf) {}

    /**
     * Determines whether a given entity can be handled by the system, that is whether all
     * {@link EntitySystem#onEntityAdded(Entity, float)}, {@link EntitySystem#onEntityRemoved(Entity, float)},
     * {@link EntitySystem#onEntityUpdated(Entity, float)} and {@link EntitySystem#onUpdate(Entity, float)} can be called
     * with parameter {@code entity}.
     * @see EntitySystem#fromRequiredComponents(EntitySystem, List)
     * @param entity the entity tested
     * @return whether {@code entity} is supported by the system
     */
    default boolean canHandle(Entity entity) {
        return true;
    }

    /**
     * Constructs a new entity system overruling the {@link EntitySystem#canHandle(Entity)} method that the given {@code source}
     * has defined, replacing it by the following procedure. For a given entity {@code e},
     *  <ul>
     *     <li>
     *         If for every type {@code t} contained in {@code types}, {@code e} owns a component of type {@code t},
     *         {@code canHandle} returns {@code true} on {@code e}.
     *     </li>
     *     <li>
     *         Otherwise, {@code canHandle} returns {@code false} on {@code e}
     *     </li>
     * </ul>
     *
     * The system created can then safely assume that any entity its given contains at least all components specified by
     * {@code types}.
     * @param source the system source, whose @{code canHandle} method will be overruled
     * @param types the list of component types required for any entity to be supported by the system
     * @return a decorator over {@code source}, which has mimics its exact same behavior, except for {@code canHandle}.
     */
    static EntitySystem fromRequiredComponents(EntitySystem source, List<? extends Class<? extends EntityComponent>> types) {
        return new EntitySystem() {
            @Override
            public void onEntityAdded(Entity entity, float tpf) {
                source.onEntityAdded(entity, tpf);
            }

            @Override
            public void onEntityUpdated(Entity entity, float tpf) {
                source.onEntityUpdated(entity, tpf);
            }

            @Override
            public void onEntityRemoved(Entity entity, float tpf) {
                source.onEntityRemoved(entity, tpf);
            }

            @Override
            public boolean canHandle(Entity entity) {
                return types
                        .stream()
                        .allMatch(entity::hasComponent);
            }

            @Override
            public void onUpdate(Entity entity, float tpf) {
                source.onUpdate(entity, tpf);
            }
        };
    }
}
