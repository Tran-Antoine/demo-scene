package ch.epfl.atran.data;

import ch.epfl.atran.ecs.EntityComponent;
import ch.epfl.atran.math.Vector3f;

public class TransformComponent implements EntityComponent {

    private Vector3f position;
    private Vector3f rotation;
    private Vector3f scale;

    public TransformComponent(Vector3f position, Vector3f rotation, Vector3f scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public static TransformComponent noTransform() {
        return new TransformComponent(Vector3f.ZERO, Vector3f.ZERO, Vector3f.ONE);
    }

    public Vector3f position() {
        return position;
    }

    public TransformComponent move(Vector3f displacement) {
        this.position = this.position.add(displacement);
        return this;
    }

    public TransformComponent setPosition(Vector3f position) {
        this.position = position;
        return this;
    }

    public Vector3f rotation() {
        return rotation;
    }

    public TransformComponent rotate(Vector3f rotation) {
        this.rotation = this.rotation.add(rotation);
        return this;
    }

    public TransformComponent setRotation(Vector3f rotation) {
        this.rotation = rotation;
        return this;
    }

    public Vector3f scale() {
        return scale;
    }

    public TransformComponent scale(float scalar) {
        return scale(new Vector3f(scalar, scalar, scalar));
    }

    public TransformComponent scale(Vector3f scaler) {
        this.scale = this.scale.mul(scaler);
        return this;
    }

    public TransformComponent setScale(Vector3f scale) {
        this.scale = scale;
        return this;
    }
}
