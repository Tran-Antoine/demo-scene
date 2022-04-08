package ch.epfl.atran.models;

import ch.epfl.atran.data.RawModel;
import ch.epfl.atran.math.Vector3f;

public class SampleModels {

    public static RawModel sampleLSystem() {
        return LSystemFactory.generate(
                "F++F++F",
                new Vector3f(-0.75f, -0.45f, 0),
                7,
                c -> c == 'F' ? "F-F++F-F" : String.valueOf(c),
                new LSystemFactory.TurtleConfig(60, 0.0007f)
        );
    }

    public static RawModel sampleGasket() {
        return SierpinskyGasketFactory.generate(
                100000,
                789785678456L,
                new Vector3f(-0.4f, 0.3f, 0));
    }

    public static RawModel sampleRectangle() {

        float[] positions = {
                -1, -1, 0,
                -1, 1, 0,
                1, -1, 0,
                1, 1, 0
        };

        int[] indices = {0, 2, 3, 3, 1, 0};
        return new RawModel(positions, indices);
    }
}
