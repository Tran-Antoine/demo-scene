package ch.epfl.atran.render;

import ch.epfl.atran.data.RawModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class StaticRenderer {

    /**
     * Renders a raw model.
     * Triangles are rendered from the VAO's first VBO (positions) and an index buffer.
     * The method applies the following procedure:
     * - Bind the VAO to make it active
     * - Enable attribute 0 of the VAO attribute list (the positions)
     * - Draw using element mode (i.e. drawing with the help of the index buffer)
     * - Unbind both the VAO and attribute 0 of the VAO
     * - Flush to guarantee immediate effect
     * @param model the model to render
     */
    public static void render(RawModel model) {

        GL30.glBindVertexArray(model.vaoId());
        GL30.glEnableVertexAttribArray(0);

        //GL30.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_POINT);
        GL30.glPointSize(1);
        GL30.glDrawElements(model.drawType(), model.vertexCount(), GL11.GL_UNSIGNED_INT, 0);

        GL30.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        GL30.glFlush();
    }
}
