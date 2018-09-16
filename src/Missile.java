public class Missile extends Entity{
    Warhead[] warheads;
    Motor[] motors;
    Targeting targeting;

    public Missile(double missileMass, int health, int maxHealth, Warhead[] warheads, Motor[] motors, Targeting targeting){
        super(Missile.getTotalMass(missileMass, warheads, motors), health, maxHealth);
        this.warheads = warheads;
        this.motors = motors;
        this.targeting = targeting;
    }

    public static double getTotalMass(double missileMass, Warhead[] warheads, Motor[] motors){
        double totalMass = missileMass;
        for (int i = 0; i < warheads.length; i++){
            totalMass += warheads[i].getMass();
        }
        for (int i = 0; i < motors.length; i++){
            totalMass += motors[i].getMass();
        }
        return totalMass;
    }

    public void update(double timeStep){
        
    }
}