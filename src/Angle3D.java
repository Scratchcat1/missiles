public class Angle3D{
    double[] angle;
    // [Pitch, Yaw, Roll]

    public Angle3D(){
        this.angle = new double[3];
    }
    public Angle3D(double[] initialVector){
        this.angle = initialVector.clone();
    }

    public Angle3D clone(){
        return new Angle3D(this.angle);
    }

    public double get(int i){
        return this.angle[i];
    }

    public void set(int i, double value){
        this.angle[i] = value;
    } 

    public Vector toVector(double scalar){
        Vector vector = new Vector(3);
        vector.set(2, scalar * Math.sin(this.angle[0]));
        double xyScalar = scalar * Math.abs(Math.cos(this.angle[0]));
        vector.set(0, xyScalar * Math.cos(this.angle[1]));
        vector.set(1, xyScalar * Math.sin(this.angle[1]));
        return vector;
    }

    public Angle3D rotate(Angle3D rotationAngle){
        Angle3D newAngle = new Angle3D();
        for (int i = 0; i < 3; i++){
            newAngle.set(i, (this.angle[i] + rotationAngle.get(i)) % (2 * Math.PI));
        }
        return newAngle;
    }
}