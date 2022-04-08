package ch.epfl.atran.render;

import ch.epfl.atran.math.Matrix4f;
import org.lwjgl.opengl.GL30;

public class ShaderProgram {

    private final int programId;

    public ShaderProgram(String vertexPath, String fragmentPath) {
        this.programId = GL30.glCreateProgram();
        ShaderObject vertexObj = ShaderObject.fromPath(vertexPath, GL30.GL_VERTEX_SHADER);
        ShaderObject fragmentObj = ShaderObject.fromPath(fragmentPath, GL30.GL_FRAGMENT_SHADER);

        if(vertexObj == null || fragmentObj == null) {
            throw new IllegalArgumentException("Invalid path given");
        }

        attach(vertexObj);
        attach(fragmentObj);
        GL30.glLinkProgram(programId);
        GL30.glValidateProgram(programId);
    }

    public void setUniform1f(String name, float value) {
        GL30.glUniform1f(GL30.glGetUniformLocation(programId, name), value);
    }

    public void setUniformMatrix4f(String name, Matrix4f matrix) {
        float[] array = matrix.asFloatArray();
        GL30.glUniformMatrix4fv(GL30.glGetUniformLocation(programId, name), true, array);
    }

    private void attach(ShaderObject object) {
        GL30.glAttachShader(programId, object.getShaderId());
    }

    public void start() {
        GL30.glUseProgram(programId);
    }

    public void stop() {
        GL30.glUseProgram(0);
    }

    public void bindShaderInput(int attributeIndex, String name) {
        GL30.glBindAttribLocation(programId, attributeIndex, name);
    }
}
