package ch.epfl.atran.ecs;

import java.util.List;

public interface EntitySystem {

    void onEntityAdded(Entity entity, float tpf);
    void onEntityUpdated(Entity entity, float tpf);
    void onEntityRemoved(Entity entity, float tpf);
    void onUpdate(Entity entity, float tpf);

    default boolean canHandle(Entity entity) {
        return true;
    }

    static EntitySystem fromRequiredComponents(EntitySystem source, List<? extends Class<? extends Component>> types) {
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
