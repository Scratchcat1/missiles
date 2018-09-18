import java.util.ArrayList;

public class LaunchPlatform extends Entity {
    ArrayList<Launcher> launchers;
    ArrayList<Motor> motors;
    Targeting targeting;

    public LaunchPlatform(double launchPlatformMass, int health, int maxHealth, ArrayList<Launcher> launchers, ArrayList<Motor> motors, Targeting targeting){
        super(launchPlatformMass, health, maxHealth);
        this.launchers = launchers;
        this.motors = motors;
        this.targeting = targeting;
    }

    public void update(double timeStep){
        for (Motor motor : this.motors){
            motor.setDutyCycle(this.targeting.getDutyCycle(motor, this.getPosition(), this.getVelocity()));
            motor.update(timeStep);
        }

        this.move(timeStep);

        for (Motor motor : this.motors){
            motor.updateKinetics(this.getPosition(), this.getVelocity(), this.getAngle());
        }
    }

    public ArrayList<Missile> launchMissiles(){
        ArrayList<Missile> launchedMissiles = new ArrayList<>();
        for (Launcher launcher : this.launchers){
            launcher.updateKinetics(this.getPosition(), this.getVelocity(), this.getAngle());
            if (targeting.shouldLaunch(launcher)){
                launchedMissiles.add(launcher.launchMissile(0));
            }
        }
        return launchedMissiles;
    }


    public double getMass(){
        double totalMass = this.mass;
        for (Launcher launcher : this.launchers){
            totalMass += launcher.getMass();
        }
        for (Motor motor : this.motors){
            totalMass += motor.getMass();
        }
        return totalMass;
    }
}