package ch.epfl.atran.render;

import ch.epfl.atran.data.RawModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

/**
 * Basic rendering pipeline used for debugging purposes. All models will be drawn on screen without any
 * transformation applied. Model coordinates should be in the range [-1, 1] to appear on screen.
 */
public class NoClippingRenderer implements Renderer<RawModel> {

    /**
     * Prepares the model for future rendering.
     * The method applies the following procedure:
     *
     * - Bind the VAO to make it active
     * - Enable attribute 0 of the VAO attribute list (the positions)
     * @param obj the model to prepare for future rendering
     */
    @Override
    public void prepare(RawModel obj) {
        GL30.glBindVertexArray(obj.vaoId());
        GL30.glEnableVertexAttribArray(0);
    }

    /**
     * Renders a raw model.
     * Triangles are rendered from the VAO's first VBO (positions) and an index buffer.
     * The method applies the following procedure:
     *
     * - Draw using element mode (i.e. drawing with the help of the index buffer)
     * - Flush to guarantee immediate effect
     * @param model the model to render
     */
    @Override
    public void render(RawModel model, ShaderProgram program) {
        GL30.glDrawElements(model.drawType(), model.vertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL30.glFlush();
    }

    /**
     * Unbinds both the VAO and attribute 0 of the VAO
     * @param obj the model previously rendered
     */
    @Override
    public void cleanUp(RawModel obj) {
        GL30.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }
}
