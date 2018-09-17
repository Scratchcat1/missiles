import java.util.ArrayList;

public class LaunchPlatform extends Entity {
    Launcher[] launchers;
    Motor[] motors;
    Targeting targeting;

    public LaunchPlatform(double launchPlatformMass, int health, int maxHealth, Launcher[] launchers, Motor[] motors, Targeting targeting){
        super(launchPlatformMass, health, maxHealth);
        this.launchers = launchers;
        this.motors = motors;
        this.targeting = targeting;
    }

    public Missile[] update(double timeStep){
        for (Motor motor : this.motors){
            motor.setDutyCycle(this.targeting.getDutyCycle(motor, this.getPosition(), this.getVelocity()));
            motor.update(timeStep);
        }

        this.move(timeStep);

        for (Motor motor : this.motors){
            motor.updateKinetics(this.getPosition(), this.getVelocity(), this.getAngle());
        }

        ArrayList<Missile> launchedMissiles = new ArrayList<>();
        for (Launcher launcher : this.launchers){
            launcher.updateKinetics(this.getPosition(), this.getVelocity(), this.getAngle());
            if (targeting.shouldLaunch(launcher)){
                launchedMissiles.add(launcher.launchMissile(0));
            }
        }
        return launchedMissiles.toArray(new Missile[launchedMissiles.size()]);
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