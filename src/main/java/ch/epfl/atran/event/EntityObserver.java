package ch.epfl.atran.event;

import ch.epfl.atran.ecs.Entity;

public interface EntityObserver {

    void onNotify(Entity observed);
}
