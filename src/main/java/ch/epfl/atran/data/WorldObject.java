package ch.epfl.atran.data;

import ch.epfl.atran.math.Vector3f;
import ch.epfl.atran.render.ShaderProgram;

public class WorldObject {

    private final RawModel model;
    private final ShaderProgram shader;
    private Vector3f position;
    private Vector3f rotation;
    private Vector3f scale;

    public WorldObject(RawModel model, ShaderProgram shader) {
        this(model, shader, Vector3f.ZERO, Vector3f.ZERO, Vector3f.ONE);
    }

    public WorldObject(RawModel model, ShaderProgram shader, Vector3f position, Vector3f rotation, Vector3f scale) {
        this.model = model;
        this.shader = shader;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public RawModel getModel() {
        return model;
    }

    public ShaderProgram getShader() {
        return shader;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void move(Vector3f displacement) {
        this.position = this.position.add(displacement);
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void rotate(Vector3f rotation) {
        this.rotation = this.rotation.add(rotation);
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void scale(float scalar) {
        scale(new Vector3f(scalar, scalar, scalar));
    }
    public void scale(Vector3f scaler) {
        this.scale = this.scale.mul(scaler);
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }
}
