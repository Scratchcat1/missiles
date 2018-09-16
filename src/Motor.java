public class Motor extends Entity{
    double remainingFuel;
    double fuelConsumption;
    double force;
    double dutyCycle;
    double minDutyCycle;

    public Motor(double mass, int health, int maxHealth, double fuelConsumption, double force, double minDutyCycle){
        super(mass, health, maxHealth);
        this.fuelConsumption = fuelConsumption;
        this.force = force;
        this.minDutyCycle = minDutyCycle;
    }

    public void setDutyCycle(double newDutyCycle){
        this.dutyCycle = Math.max(newDutyCycle, this.minDutyCycle);
    }

    public double getForce(){
        if (remainingFuel > 0){
            return this.force * this.dutyCycle;
        } else {
            return 0;
        }
    }

    public void update(double timeStep){
        this.remainingFuel -= this.fuelConsumption * timeStep;
    }
}