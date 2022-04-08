package ch.epfl.atran.math;

public class Vector2f {

    public static final Vector2f ZERO   = new Vector2f(0, 0);
    public static final Vector2f ONE    = new Vector2f(1, 1);
    public static final Vector2f UNIT_X = new Vector2f(1, 0);
    public static final Vector2f UNIT_Y = new Vector2f(0, 1);

    private final float x;
    private final float y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public Vector2f sub(Vector2f v) {
        return new Vector2f(x - v.x, y - v.y);
    }

    public Vector2f add(Vector2f v) {
        return new Vector2f(x + v.x, y + v.y);
    }

    public Vector2f mul(float scalar) {
        return new Vector2f(scalar * x, scalar * y);
    }

    /**
     * Component-wise multiplication.
     */
    public Vector2f mul(Vector2f v) {
        return new Vector2f(x * v.x, y * v.y);
    }

    public Vector2f interpolate(float x, Vector2f dest) {
        Vector2f dir = dest.sub(this);
        return this.add(dir.mul(x));
    }

    public float dot(Vector2f v) {
        return x*v.x + y*v.y;
    }

    public float squaredLength() {
        return x*x + y*y;
    }

    public float length() {
        return (float) Math.sqrt(squaredLength());
    }

    public Vector3f withXAxis() {
        return withXAxis(0);
    }

    public Vector3f withXAxis(float x) {
        return new Vector3f(x, this.x, y);
    }

    public Vector3f withYAxis() {
        return withYAxis(0);
    }

    public Vector3f withYAxis(float y) {
        return new Vector3f(x, y, this.y);
    }

    public Vector3f withZAxis() {
        return withZAxis(0);
    }

    public Vector3f withZAxis(float z) {
        return new Vector3f(x, y, z);
    }

    public Vector2f writeTo(int index, float[] array) {
        if(index + 2 > array.length) {
            throw new IllegalArgumentException("Cannot add 2 floats starting from given index");
        }
        array[index] = x;
        array[index + 1] = y;
        return this;
    }
}
