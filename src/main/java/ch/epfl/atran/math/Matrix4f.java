package ch.epfl.atran.math;

public class Matrix4f {

    private final float[][] elements;

    public Matrix4f() {
        this.elements = new float[4][4];
    }

    public Matrix4f(float[][] elements) {
        this.elements = elements;
    }

    public float get(int i, int j) {
        return elements[j][i];
    }

    public Matrix4f mul(Matrix4f other) {
        float[][] newElems = new float[4][4];

        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                newElems[i][j] = dot(i, j, other);
            }
        }

        return new Matrix4f(newElems);
    }

    public float dot(int thisRow, int thatColumn, Matrix4f that) {
        float total = 0;
        for(int i = 0; i < 4; i++) {
            total += elements[thisRow][i] * that.elements[i][thatColumn];
        }
        return total;
    }

    public float[] asFloatArray() {
        float[] array = new float[16];
        for(int i = 0; i < 4; i++) {
            System.arraycopy(elements[i], 0, array, 4 * i, 4);
        }
        return array;
    }
}
