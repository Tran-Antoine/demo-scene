package ch.epfl.atran.render;

import org.lwjgl.opengl.GL30;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

public class ShaderObject {

    private final int shaderId;

    private ShaderObject(int shaderId) {
        this.shaderId = shaderId;
    }

    public static ShaderObject fromPath(String sourcePath, int shaderType) {
        int id = GL30.glCreateShader(shaderType);
        try {
            String content = readFromPath(sourcePath);
            GL30.glShaderSource(id, content);
            GL30.glCompileShader(id);
            return new ShaderObject(id);
        } catch(IOException e) {
            System.err.println("Shader object could not be initialized: invalid path");
            return null;
        }
    }

    public int getShaderId() {
        return shaderId;
    }

    private static String readFromPath(String path) throws IOException {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(path))) {
            return fileReader
                    .lines()
                    .collect(Collectors.joining("\n"));
        }
    }
}
