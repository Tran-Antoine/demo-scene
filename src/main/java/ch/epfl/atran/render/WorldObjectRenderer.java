package ch.epfl.atran.render;

import ch.epfl.atran.data.RawModel;
import ch.epfl.atran.ecs.Entity;
import ch.epfl.atran.ecs.ShapeComponent;
import ch.epfl.atran.ecs.TransformComponent;
import ch.epfl.atran.math.Transforms;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class WorldObjectRenderer implements Renderer<Entity> {

    @Override
    public void prepare(Entity obj) {
        int vaoId = obj
                .get(ShapeComponent.class)
                .model()
                .vaoId();
        GL30.glBindVertexArray(vaoId);
        GL30.glEnableVertexAttribArray(0);
    }

    @Override
    public void render(Entity obj, ShaderProgram shader) {

        TransformComponent transform = obj.get(TransformComponent.class);

        GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        shader.setUniformMatrix4f("rotx",        Transforms.rotationXMatrix(transform));
        shader.setUniformMatrix4f("roty",        Transforms.rotationYMatrix(transform));
        shader.setUniformMatrix4f("rotz",        Transforms.rotationZMatrix(transform));
        shader.setUniformMatrix4f("scale",       Transforms.scalingMatrix(transform));
        shader.setUniformMatrix4f("translation", Transforms.translationMatrix(transform));

        RawModel model = obj.get(ShapeComponent.class).model();
        GL30.glDrawElements(model.drawType(), model.vertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL30.glFlush();
    }

    @Override
    public void cleanUp(Entity obj) {
        GL30.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }
}
