public class Motor extends Entity{
    double remainingFuel;
    double fuelConsumption;
    double fuelMass;
    double force;
    double dutyCycle;
    double minDutyCycle;

    public Motor(double mass, int health, int maxHealth, double fuelConsumption, double fuelMass, double force, double minDutyCycle){
        super(mass, health, maxHealth);
        this.fuelConsumption = fuelConsumption;
        this.fuelMass = fuelMass;
        this.force = force;
        this.minDutyCycle = minDutyCycle;
    }

    public void setDutyCycle(double newDutyCycle){
        this.dutyCycle = Math.max(newDutyCycle, this.minDutyCycle);
    }

    public Vector getForce(){
        double force = 0;
        if (this.remainingFuel > 0){
            force = this.force * this.dutyCycle;
        }
        return this.getAngle().toVector(force);
    }

    public void update(double timeStep){
        this.remainingFuel -= this.fuelConsumption * timeStep;
    }

    public double getMass(){
        return this.mass + this.remainingFuel * this.fuelMass;
    }
}