package ch.epfl.atran.data;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static ch.epfl.atran.data.RawModels.*;

public class RawModel {

    private final int vaoId;
    private final int size;
    private final int drawType;

    public RawModel(float[] positions, int[] indices) {
        this(positions, indices, GL30.GL_TRIANGLES);
    }

    public RawModel(float[] positions, int[] indices, int drawType) {
        this.vaoId = createVAO(positions, indices);
        this.size = indices.length;
        this.drawType = drawType;
    }

    public int drawType() {
        return drawType;
    }

    private static int createVAO(float[] positions, int[] indices) {

        int id = GL30.glGenVertexArrays(); // gen a new vao ID
        GL30.glBindVertexArray(id); // make the vao active

        addPositions(positions);
        addIndices(indices);

        GL30.glDisableVertexAttribArray(id);
        return id;
    }


    private static void addPositions(float[] positions) {

        int vboId = GL30.glGenBuffers();
        var positionsBuffer = toBuffer(positions);

        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboId); // make vbo the target for the next instructions
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, positionsBuffer, GL30.GL_STATIC_DRAW); // initialize buffer structure

        GL30.glVertexAttribPointer(
                POSITION_INDEX,
                COMPONENTS_PER_POSITION,
                GL30.GL_FLOAT,
                NO_NORMALIZATION,
                OFFSET_BETWEEN_VALUES,
                STARTING_OFFSET);
    }

    private static void addIndices(int[] indices) {

        int id = GL30.glGenBuffers();
        var indicesBuffer = toBuffer(indices);

        GL30.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, id);
        // adding it to the attribute list is not necessary for indices
        GL30.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
    }

    private static FloatBuffer toBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip(); // crucial to fix the size, otherwise the buffer reading overflows
        return buffer;
    }

    private static IntBuffer toBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip(); // crucial to fix the size, otherwise the buffer reading overflows
        return buffer;
    }

    public int vertexCount() {
        return size;
    }

    public int vaoId() {
        return vaoId;
    }
}
