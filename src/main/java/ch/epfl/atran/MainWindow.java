package ch.epfl.atran;

import ch.epfl.atran.data.RawModel;
import ch.epfl.atran.ecs.Entity;
import ch.epfl.atran.ecs.ShapeComponent;
import ch.epfl.atran.ecs.SystemManager;
import ch.epfl.atran.ecs.TransformComponent;
import ch.epfl.atran.models.SampleModels;
import ch.epfl.atran.render.*;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.IntBuffer;

public class MainWindow {

    private long window;

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.init();
        window.loop();
        window.cleanUp();
    }

    private void init() {

        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE); // the window will stay hidden after creation
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE); // the window will be resizable

        // Create the window
        window = GLFW.glfwCreateWindow(1200, 1200, "", MemoryUtil.NULL, MemoryUtil.NULL);
        if (window == MemoryUtil.NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = MemoryStack.stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            GLFW.glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

            // Center the window
            GLFW.glfwSetWindowPos(
                    window,
                    (vidMode.width() - pWidth.get(0)) / 2,
                    (vidMode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        GLFW.glfwMakeContextCurrent(window);
        // Enable v-sync
        GLFW.glfwSwapInterval(1);

        GLFW.glfwSetWindowSizeCallback(window, (w, width, height) -> GL30.glViewport(0, 0, width, height));
        // Make the window visible
        GLFW.glfwShowWindow(window);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        GL30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    private void loop() {

        SystemManager manager = new SystemManager();
        ShaderProgram shaderProgram = initShader();

        RawModel model = SampleModels.sampleGasket();
        manager.createEntity(new ShapeComponent(model), new TransformComponent());
        manager.addSystem(RendererManager.newRenderer(new WorldObjectRenderer(), shaderProgram));

        long previousTime = System.currentTimeMillis();

        while (!GLFW.glfwWindowShouldClose(window)) {

            long currentTime = System.currentTimeMillis();
            long tpf = currentTime - previousTime;
            previousTime = currentTime;

            manager.update(tpf / 1000f);
            GLFW.glfwSwapBuffers(window); // swap the color buffers
            GLFW.glfwPollEvents(); // Poll for window events
        }
    }

    private ShaderProgram initShader() {
        ShaderProgram shaderProgram = new ShaderProgram(
                "src/main/resources/base/basicVertex.glsl",
                "src/main/resources/base/basicFragment.glsl");
        shaderProgram.bindShaderInput(0, "position");
        return shaderProgram;
    }

    private void cleanUp() {
        // Free the window callbacks and destroy the window
        Callbacks.glfwFreeCallbacks(window);
        GLFW.glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }
}
