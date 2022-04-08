package ch.epfl.atran.math;

import ch.epfl.atran.ecs.TransformComponent;

public class Transforms {

    public static Matrix4f translationMatrix(TransformComponent transform) {
        return translationMatrix(transform.position());
    }
    public static Matrix4f translationMatrix(Vector3f pos) {
        return translationMatrix(pos.x(), pos.y(), pos.z());
    }

    public static Matrix4f translationMatrix(float tx, float ty, float tz) {
        float[][] elements = {
                {1, 0, 0, tx},
                {0, 1, 0, ty},
                {0, 0, 1, tz},
                {0, 0, 0, 1}
        };
        return new Matrix4f(elements);
    }

    public static Matrix4f scalingMatrix(TransformComponent transform) {
        return scalingMatrix(transform.scale());
    }

    public static Matrix4f scalingMatrix(Vector3f scale) {
        return scalingMatrix(scale.x(), scale.y(), scale.z());
    }

    public static Matrix4f scalingMatrix(float sx, float sy, float sz) {
        float[][] elements = {
                {sx, 0, 0, 0},
                {0, sy, 0, 0},
                {0, 0, sz, 0},
                {0, 0, 0,  1}
        };
        return new Matrix4f(elements);
    }

    public static Matrix4f rotationXMatrix(TransformComponent transform) {
        return rotationXMatrix(transform.rotation().x());
    }

    public static Matrix4f rotationXMatrix(float angle) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);

        float[][] elements = {
                {1, 0, 0, 0},
                {0, cos, -sin, 0},
                {0, sin, cos, 0},
                {0, 0, 0,  1}
        };
        return new Matrix4f(elements);
    }

    public static Matrix4f rotationYMatrix(TransformComponent transform) {
        return rotationYMatrix(transform.rotation().y());
    }

    public static Matrix4f rotationYMatrix(float angle) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);

        float[][] elements = {
                {cos,  0, sin, 0},
                {0,    1, 0,   0},
                {-sin, 0, cos, 0},
                {0,    0, 0,   1}
        };
        return new Matrix4f(elements);
    }

    public static Matrix4f rotationZMatrix(TransformComponent transform) {
        return rotationYMatrix(transform.rotation().z());
    }

    public static Matrix4f rotationZMatrix(float angle) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);

        float[][] elements = {
                {cos, -sin, 0, 0},
                {sin, cos,  0, 0},
                {0,   0,    1, 0},
                {0,   0,    0, 1}
        };
        return new Matrix4f(elements);
    }

    public static Matrix4f rotationMatrix(Vector3f rot) {
        return rotationMatrix(rot.x(), rot.y(), rot.z());
    }

    public static Matrix4f rotationMatrix(float rx, float ry, float rz) {
        return rotationXMatrix(rx).mul(rotationYMatrix(ry)).mul(rotationZMatrix(rz));
    }

}
