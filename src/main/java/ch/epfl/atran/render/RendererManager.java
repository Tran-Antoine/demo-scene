package ch.epfl.atran.render;

import ch.epfl.atran.ecs.*;

import java.util.List;

/**
 * Bridge connecting the rendering pipeline to the Entity Component System.
 *
 * The Renderer manager is a system associated with a renderer. Upon system update, the manager will delegate the
 * rendering preparation, rendering and rendering clean-up, while taking care of shader start / stop.
 */
public class RendererManager implements EntitySystem {

    private final Renderer<Entity> renderer;
    private final ShaderProgram shader;

    private RendererManager(Renderer<Entity> renderer, ShaderProgram shader) {
        this.renderer = renderer;
        this.shader = shader;
    }

    /**
     * Constructs a new renderer manager associated with a renderer and a shader program.
     *
     * @param renderer the renderer to which rendering task will be delegated
     * @param shader the shader program to use throughout the rendering process
     * @param requiredComponents a minimal list of required components for entities. Should be such that the renderer
     *                           can safely assume that all the components in the list can be retrieved without any
     *                           error
     * @return a new renderer manager
     */
    public static EntitySystem newRenderer(
            Renderer<Entity> renderer, ShaderProgram shader,
            List<? extends Class<? extends EntityComponent>> requiredComponents) {
        
        return EntitySystem.fromRequiredComponents(
                new RendererManager(renderer, shader),
                requiredComponents
        );
    }

    @Override
    public void onEntityAdded(Entity entity, float tpf) { }

    @Override
    public void onEntityUpdated(Entity entity, float tpf) { }

    @Override
    public void onEntityRemoved(Entity entity, float tpf) { }

    @Override
    public void onUpdate(Entity entity, float tpf) {
        renderer.prepare(entity);
        shader.start();
        renderer.render(entity, shader);
        shader.stop();
        renderer.cleanUp(entity);
    }
}
