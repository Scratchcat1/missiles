public class Motor extends Entity{
    private double remainingFuel = 1;
    private double fuelConsumption;
    private double fuelMass;
    private double force;
    private double dutyCycle;
    private double minDutyCycle;

    public Motor(double mass, int health, int maxHealth, double fuelConsumption, double fuelMass, double force, double minDutyCycle){
        super(mass, health, maxHealth, new Angle3D(new double[]{0, 0, 0}));
        this.fuelConsumption = fuelConsumption;
        this.fuelMass = fuelMass;
        this.force = force;
        this.minDutyCycle = minDutyCycle;
    }

    public void setDutyCycle(double newDutyCycle){
        this.dutyCycle = Math.min(Math.max(newDutyCycle, this.minDutyCycle), 1.0);
    }

    public Vector getForce(){
        double force = 0;
        if (this.remainingFuel > 0){
            force = this.force * this.dutyCycle;
        }
        return this.getAngle().toVector(force);
    }

    @Override
    public void update(double timeStep){
        this.remainingFuel -= this.fuelConsumption * timeStep;
    }

    @Override
    public double getTotalMass(){
        return this.getMass() + this.remainingFuel * this.fuelMass;
    }
}