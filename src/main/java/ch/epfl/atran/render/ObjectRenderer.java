package ch.epfl.atran.render;

import ch.epfl.atran.data.RawModel;
import ch.epfl.atran.data.WorldObject;
import ch.epfl.atran.math.Transforms;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class ObjectRenderer implements Renderer<WorldObject> {

    @Override
    public void prepare(WorldObject obj) {
        RawModel model = obj.getModel();
        ShaderProgram shader = obj.getShader();

        GL30.glBindVertexArray(model.vaoId());
        GL30.glEnableVertexAttribArray(0);

        shader.start();
    }

    @Override
    public void render(WorldObject obj) {
        RawModel model = obj.getModel();
        ShaderProgram shader = obj.getShader();

        GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        shader.setUniformMatrix4f("rotx", Transforms.rotationXMatrix(obj.getRotation().x()));
        shader.setUniformMatrix4f("roty", Transforms.rotationYMatrix(obj.getRotation().y()));
        shader.setUniformMatrix4f("rotz", Transforms.rotationZMatrix(obj.getRotation().z()));
        shader.setUniformMatrix4f("scale", Transforms.scalingMatrix(obj.getScale()));
        shader.setUniformMatrix4f("translation", Transforms.translationMatrix(obj.getPosition()));

        GL30.glDrawElements(model.drawType(), model.vertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL30.glFlush();
    }

    @Override
    public void cleanUp(WorldObject obj) {
        GL30.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        obj.getShader().stop();
    }
}
