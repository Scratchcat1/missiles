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

    public Warhead[] update(double timeStep){

        for (Motor motor : this.motors){
            motor.setDutyCycle(this.targeting.getDutyCycle(motor, this.getPosition(), this.getVelocity()));
            motor.update(timeStep);
        }

        this.move();

        for (Motor motor : this.motors){
            motor.setPosition(this.getPosition());
        }

        Warhead[] launchingWarheads = new Warhead[this.warheads.length];
        int launchedWarheadsCount = 0;
        for (Warhead warhead : this.warheads){
            warhead.setPosition(this.getPosition());

            if (this.targeting.shouldLaunch(warhead)){
                launchingWarheads[launchedWarheadsCount] = warhead;
                launchedWarheadsCount += 1;
            }
        }

        return launchingWarheads;
    }

    
}