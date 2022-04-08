package ch.epfl.atran.data;

import ch.epfl.atran.ecs.EntityComponent;

/**
 * Simple wrapper to observe raw models as components of the ECS.
 */
public record ShapeComponent(RawModel model) implements EntityComponent { }
