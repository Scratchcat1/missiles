import java.util.ArrayList;

public class Missile extends Entity{
    private ArrayList<Warhead> warheads;
    private ArrayList<Motor> motors;
    private Targeting targeting;

    public Missile(double missileMass, int health, int maxHealth, Angle3D rotationRateLimit, ArrayList<Warhead> warheads, ArrayList<Motor> motors, Targeting targeting){
        super(missileMass, health, maxHealth, rotationRateLimit);
        this.warheads = warheads;
        this.motors = motors;
        this.targeting = targeting;
    }

    public void update(double airResistance, double gravAccel, double timeStep){
        Vector motorForce = new Vector(3);
        for (Motor motor : this.motors){
            motor.setDutyCycle(this.targeting.getDutyCycle(motor, this.getPosition(), this.getVelocity()));
            motor.update(timeStep);
            motorForce = motorForce.add(motor.getForce());
        }

        this.applyForce(motorForce, airResistance, gravAccel, timeStep);
        this.move(timeStep);

        for (Motor motor : this.motors){
            motor.updateKinetics(this.getPosition(), this.getVelocity(), this.getAngle());
        }
    }

    public ArrayList<Warhead> launchWarheads(){
        ArrayList<Integer> launchingWarheadPositions = new ArrayList<Integer>();
        for (int i = 0; i < this.warheads.size(); i++){
            Warhead warhead = this.warheads.get(i);
            warhead.updateKinetics(this.getPosition(), this.getVelocity(), this.getAngle());

            if (this.getRemainingWarheads() > 0 && this.targeting.shouldLaunch(warhead)){
                launchingWarheadPositions.add(i);
            }
        }

        ArrayList<Warhead> launchingWarheads = new ArrayList<Warhead>();
        for (int i = 0; i < launchingWarheadPositions.size(); i++){
            Warhead warhead = this.warheads.remove(launchingWarheadPositions.get(i) - i);
            launchingWarheads.add(warhead);
        }

        return launchingWarheads;
    }

    public int getRemainingWarheads(){
        return this.warheads.size();
    }

    public double getTotalMass(){
        double totalMass = this.getMass();
        for (Warhead warhead : this.warheads){
            totalMass += warhead.getTotalMass();
        }
        for (Motor motor : this.motors){
            totalMass += motor.getTotalMass();
        }
        return totalMass;
    }

    public void updateKinetics(Vector position, Vector velocity, Angle3D angle){
        this.setPosition(position);
        this.setVelocity(velocity);
        this.setAngle(angle);
        
        for (Motor motor : this.motors){
            motor.updateKinetics(position, velocity, angle);
        }

        for (Warhead warhead : this.warheads){
            warhead.updateKinetics(position, velocity, angle);
        }
    }
}