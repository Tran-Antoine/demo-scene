package ch.epfl.atran.ecs;

import java.util.concurrent.atomic.AtomicReference;

public class SimpleDemo {

    public static void main(String[] args) {

        SystemManager manager = new SystemManager(new DemoSystem());
        AtomicReference<Entity> entity = new AtomicReference<>();

        Runnable[] actions = {
                () -> entity.set(manager.createEntity()),
                () -> entity.get().addComponent(new MockComponent()),
                () -> manager.removeEntity(entity.get())
        };

        for (Runnable action : actions) {
            action.run();
            manager.update(0);
            System.out.println("----------------------");
        }
    }

    private static class DemoSystem implements EntitySystem {

        @Override
        public void onEntityAdded(Entity entity, float tpf) {
            System.out.println("Entity was added: " + entity);
        }

        @Override
        public void onEntityUpdated(Entity entity, float tpf) {
            System.out.println("Entity was updated: " + entity);
        }

        @Override
        public void onEntityRemoved(Entity entity, float tpf) {
            System.out.println("Entity was removed: " + entity);
        }

        @Override
        public void onUpdate(Entity entity, float tpf) {
            System.out.println("Entity is present: " + entity);
        }
    }

    private static class MockComponent implements Component {

        @Override
        public String toString() {
            return "MockComponent";
        }
    }
}
