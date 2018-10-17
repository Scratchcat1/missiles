package simulation;
import java.util.Arrays;

public class Vector{
    private double[] vector;

    public Vector() {
        this.vector = new double[3];
    }
    public Vector(double[] initialVector) {
        this.vector = initialVector.clone();
    }

    public Vector clone() {
        return new Vector(this.vector);
    }

    public int length() {
        return 3;
    }

    public double get(int i) {
        return this.vector[i];
    }

    public void set(int i, double value) {
        this.vector[i] = value;
    } 
    
    public boolean equals(Vector otherVector) {
        return Arrays.equals(this.vector, otherVector.toArray());
    }

    public Vector add(Vector otherVector) {
        Vector newVector = new Vector();
        for (int i = 0; i < 3; i++) {
            double newValue = this.vector[i] + otherVector.get(i);
            newVector.set(i, newValue);
        }
        return newVector;
    }

    public Vector minus(Vector otherVector) {
        return this.add(otherVector.negative());
    }

    public Vector mult(double k) {
        Vector newVector = new Vector();
        for (int i = 0; i < 3; i++) {
            newVector.set(i, this.vector[i] * k);
        }
        return newVector;
    }

    public int mult(Vector otherVector) {
        int dotProduct = 0;
        for (int i = 0; i < 3; i++) {
            dotProduct += this.vector[i] * otherVector.get(i);
        }
        return dotProduct;
    }

    public Vector negative() {
        return this.mult(-1);
    }

    public double distance(Vector otherVector) {
        int sum = 0;
        for (int i = 0; i < 3; i++) {
            sum += Math.pow(Math.abs(this.vector[i] - otherVector.get(i)), 2);
        }
        return Math.sqrt(sum);
    }

    public String toString() {
        return "Vector:" + Arrays.toString(this.vector);
    }

    public double[] toArray() {
        return this.vector;
    }
}