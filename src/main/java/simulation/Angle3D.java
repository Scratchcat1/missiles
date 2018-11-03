package simulation;
import java.util.Arrays;

public class Angle3D {
    private double[] angle;
    // [Pitch, Yaw, Roll]

    public Angle3D() {
        this.angle = new double[]{Math.PI/2 - 0.1, 0, 0};   // start mostly vertical
    }

    public Angle3D(double[] initialAngle) {
        this.angle = initialAngle.clone();
    }

    /** Create angle from vector. */
    public Angle3D(Vector initialVector) {
        angle = new double[3];
        fromVector(initialVector);
    }

    public Angle3D clone() {
        return new Angle3D(angle);
    }

    public double get(int i) {
        return angle[i];
    }

    public void set(int i, double value) {
        angle[i] = value;
    }

    public boolean equals(Angle3D otherAngle) {
        return Arrays.equals(angle, otherAngle.toArray());
    }

    /** Return a new angle representing this angle rotated by the passed rotationAngle parameter. */
    public Angle3D rotate(Angle3D rotationAngle) {
        Angle3D newAngle = new Angle3D();
        for (int i = 0; i < 3; i++) {
            newAngle.set(i, (angle[i] + rotationAngle.get(i)) % (2 * Math.PI));
        }
        return newAngle;
    }

    /** Returns the angle seperating two angles relative to the first. */
    public Angle3D angularDifference(Angle3D otherAngle) {
        Angle3D angularDifference = new Angle3D();

        for (int i = 0; i < 3; i++) {
            double difference = otherAngle.get(i) - angle[i];
            double smallestDifference = (difference - Math.PI) % (2 * Math.PI) + Math.PI;

            angularDifference.set(i, smallestDifference);
        }
        return angularDifference;
    }

    /** Import angle sizes using a vector. */
    public void fromVector(Vector vector) {
        angle[2] = 0;   // we can't determine roll
        angle[1] = Math.atan(vector.get(1) / vector.get(0));    // get yaw

        double squaredXYScalar = Math.pow(vector.get(0), 2) + Math.pow(vector.get(1), 2);
        double xyScalar = Math.sqrt(squaredXYScalar);

        angle[0] = Math.atan(vector.get(2) / xyScalar);
    }

    /** Returns vector representation of the angle, multiplied by the scalar. */
    public Vector toVector(double scalar) {
        Vector vector = new Vector();
        vector.set(2, scalar * Math.sin(angle[0]));
        double xyScalar = scalar * Math.cos(angle[0]);
        vector.set(0, xyScalar * Math.cos(angle[1]));
        vector.set(1, xyScalar * Math.sin(angle[1]));
        return vector;
    }

    public String toString() {
        return "Angle3D:" + Arrays.toString(angle);
    }

    public double[] toArray() {
        return angle;
    }
}