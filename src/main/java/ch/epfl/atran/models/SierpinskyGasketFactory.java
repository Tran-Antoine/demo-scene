package ch.epfl.atran.models;

import ch.epfl.atran.data.RawModel;
import ch.epfl.atran.math.Vector3f;
import org.lwjgl.opengl.GL30;

import java.util.Collections;
import java.util.Random;
import java.util.stream.IntStream;

public final class SierpinskyGasketFactory {

    public static RawModel generate(int nVertices, long seed, Vector3f initialP) {

        float[] positions = new float[nVertices * 3];

        Random random = new Random(seed);

        Vector3f v1 = new Vector3f(-1, -1, 0);
        Vector3f v2 = new Vector3f(1, -1, 0);
        Vector3f v3 = new Vector3f(0, 1, 0);

        Vector3f insidePoint = initialP;

        for(int i = 0; i < positions.length; i += 3) {
            Vector3f pick = pick(random, v1, v2, v3);
            insidePoint = pick
                    .interpolate(0.5f, insidePoint)
                    .writeTo(i, positions);
        }

        int[] indices = IntStream.range(0, nVertices).toArray();

        return new RawModel(positions, indices, GL30.GL_POINTS);
    }

    private static Vector3f pick(Random random, Vector3f... vectors) {
        return vectors[random.nextInt(vectors.length)];
    }
}
