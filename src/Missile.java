import java.util.ArrayList;

public class Missile extends Entity{
    Warhead[] warheads;
    Motor[] motors;
    Targeting targeting;

    public Missile(double missileMass, int health, int maxHealth, Warhead[] warheads, Motor[] motors, Targeting targeting){
        super(missileMass, health, maxHealth);
        this.warheads = warheads;
        this.motors = motors;
        this.targeting = targeting;
    }

    public Warhead[] update(double timeStep){

        for (Motor motor : this.motors){
            motor.setDutyCycle(this.targeting.getDutyCycle(motor, this.getPosition(), this.getVelocity()));
            motor.update(timeStep);
        }

        this.move(timeStep);

        for (Motor motor : this.motors){
            motor.updateKinetics(this.getPosition(), this.getVelocity(), this.getAngle());
        }

        ArrayList<Warhead> launchingWarheads = new ArrayList<Warhead>();
        for (Warhead warhead : this.warheads){
            warhead.updateKinetics(this.getPosition(), this.getVelocity(), this.getAngle());

            if (this.targeting.shouldLaunch(warhead)){
                launchingWarheads.add(warhead);
            }
        }
        return launchingWarheads.toArray(new Warhead[launchingWarheads.size()]);
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

    public void updateKinetics(Vector position, Vector velocity, Vector angle){
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