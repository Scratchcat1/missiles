import java.util.ArrayList;

public class Missile extends Entity{
    ArrayList<Warhead> warheads;
    ArrayList<Motor> motors;
    Targeting targeting;

    public Missile(double missileMass, int health, int maxHealth, ArrayList<Warhead> warheads, ArrayList<Motor> motors, Targeting targeting){
        super(missileMass, health, maxHealth);
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
        ArrayList<Warhead> launchingWarheads = new ArrayList<Warhead>();
        for (Warhead warhead : this.warheads){
            warhead.updateKinetics(this.getPosition(), this.getVelocity(), this.getAngle());

            if (this.targeting.shouldLaunch(warhead)){
                this.warheads.remove(warhead);
                launchingWarheads.add(warhead);
            }
        }
        return launchingWarheads;
    }

    public double getMass(){
        double totalMass = this.mass;
        for (Warhead warhead : this.warheads){
            totalMass += warhead.getMass();
        }
        for (Motor motor : this.motors){
            totalMass += motor.getMass();
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