import java.util.Arrays;

public class Vector{
    double[] vector = new double[3];

    public Vector(){}
    public Vector(double[] initialVector){
        if (initialVector.length == 3){
            this.vector = initialVector;
        } else {
            // throw new Exception("Incorrect initial vector size of " + initialVector.length);
        }
    }



    public double get(int i){
        return this.vector[i];
    }

    public void set(int i, double value){
        this.vector[i] = value;
    } 
    
    public boolean equals(Vector otherVector){
        boolean valid = true;
        for (int i = 0; i < this.vector.length; i++){
            if (this.vector[i] != otherVector.get(i)){
                valid = false;
                break;
            }
        }
        return valid;
    }

    public Vector add(Vector otherVector){
        Vector newVector = new Vector();
        for (int i = 0; i < this.vector.length; i++){
            double newValue = this.vector[i] + otherVector.get(i);
            newVector.set(i, newValue);
        }
        return newVector;
    }

    public Vector minus(Vector otherVector){
        return this.add(otherVector.negation());
    }

    public Vector mult(double k){
        Vector newVector = new Vector();
        for (int i = 0; i < this.vector.length; i++){
            newVector.set(i, this.vector[i] * k);
        }
        return newVector;
    }

    public int mult(Vector otherVector){
        int dotProduct = 0;
        for (int i = 0; i < this.vector.length; i++){
            dotProduct += this.vector[i] * otherVector.get(i);
        }
        return dotProduct;
    }

    public Vector negation(){
        return this.mult(-1);
    }

    public double distance(Vector otherVector){
        int sum = 0;
        for (int i = 0; i < this.vector.length; i++){
            sum += Math.pow(Math.abs(this.vector[i] - otherVector.get(i)), 2);
        }
        return Math.sqrt(sum);
    }

    public void print(){
        System.out.print(Arrays.toString(this.vector));
    }
}