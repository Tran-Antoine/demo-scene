package ch.epfl.atran.ecs;

import ch.epfl.atran.data.RawModel;

public class ShapeComponent implements Component {

    private final RawModel model;

    public ShapeComponent(RawModel model) {
        this.model = model;
    }

    public RawModel getModel() {
        return model;
    }
}
