package ch.epfl.atran.math;

import static java.lang.Math.*;

public class Turtle2D {

    private Vector3f position;
    private int angle;

    public Turtle2D(Vector3f position, int angle) {
        this.position = position;
        this.angle = angle;
    }

    public void turn2D(int angle) {
        this.angle = floorMod(this.angle + angle, 360);
    }

    public void move(double distance) {
        float x1 = (float) (position.x() + distance * cos(toRadians(angle)));
        float y1 = (float) (position.y() + distance * sin(toRadians(angle)));
        this.position = new Vector3f(x1, y1, position.z());
    }

    public void reset(Vector3f position, int angle) {
        this.position = position;
        this.angle = angle;
    }

    public Vector3f getPosition() {
        return position;
    }

    public int getAngle() {
        return angle;
    }
}
