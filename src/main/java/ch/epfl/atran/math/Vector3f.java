package ch.epfl.atran.math;

public class Vector3f {

    public static final Vector3f ZERO   = new Vector3f(0, 0, 0);
    public static final Vector3f ONE    = new Vector3f(1, 1, 1);
    public static final Vector3f UNIT_X = new Vector3f(1, 0, 0);
    public static final Vector3f UNIT_Y = new Vector3f(0, 1, 0);
    public static final Vector3f UNIT_Z = new Vector3f(0, 0, 1);

    private final float x;
    private final float y;
    private final float z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public float z() {
        return z;
    }

    public Vector3f sub(Vector3f v) {
        return new Vector3f(x - v.x, y - v.y, z - v.z);
    }

    public Vector3f add(Vector3f v) {
        return new Vector3f(x + v.x, y + v.y, z + v.z);
    }

    public Vector3f mul(float scalar) {
        return new Vector3f(scalar * x, scalar * y, scalar * z);
    }

    /**
     * Component-wise multiplication.
     */
    public Vector3f mul(Vector3f v) {
        return new Vector3f(x * v.x, y * v.y, z * v.z);
    }

    public Vector3f interpolate(float x, Vector3f dest) {
        Vector3f dir = dest.sub(this);
        return this.add(dir.mul(x));
    }

    public float dot(Vector3f v) {
        return x*v.x + y*v.y + z*v.z;
    }

    public Vector3f cross(Vector3f v) {
        return new Vector3f(y*v.z - v.y*z, z*v.x - x*v.z, x*v.y - y*v.x);
    }

    public float squaredLength() {
        return x*x + y*y + z*z;
    }

    public float length() {
        return (float) Math.sqrt(squaredLength());
    }

    public Vector2f flattenY() {
        return new Vector2f(x, z);
    }

    public Vector2f flattenX() {
        return new Vector2f(y, z);
    }

    public Vector2f flattenZ() {
        return new Vector2f(x, y);
    }

    public Vector3f writeTo(int index, float[] array) {
        if(index + 3 > array.length) {
            throw new IllegalArgumentException("Cannot add 3 floats starting from given index");
        }
        array[index] = x;
        array[index + 1] = y;
        array[index + 2] = z;
        return this;
    }
}
