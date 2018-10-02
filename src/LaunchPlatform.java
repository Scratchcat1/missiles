import java.util.ArrayList;

public class LaunchPlatform extends Entity {
    private ArrayList<Launcher> launchers;
    private ArrayList<Motor> motors;
    private Targeting targeting;

    public LaunchPlatform(double launchPlatformMass, int health, int maxHealth, Angle3D rotationRateLimit, ArrayList<Launcher> launchers, ArrayList<Motor> motors, Targeting targeting){
        super(launchPlatformMass, health, maxHealth, rotationRateLimit);
        this.launchers = launchers;
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
        this.rotate(this.targeting.getRotationRate(this), timeStep);

        for (Motor motor : this.motors){
            motor.updateKinetics(this.getPosition(), this.getVelocity(), this.getAngle());
        }
    }

    public ArrayList<Missile> launchMissiles(){
        ArrayList<Missile> launchedMissiles = new ArrayList<>();
        for (Launcher launcher : this.launchers){
            launcher.updateKinetics(this.getPosition(), this.getVelocity(), this.getAngle());
            if (launcher.getRemainingMissiles() > 0 && this.targeting.shouldLaunch(launcher)){
                launchedMissiles.add(launcher.launchMissile(0));
            }
        }
        return launchedMissiles;
    }

    @Override
    public double getTotalMass(){
        double totalMass = this.getMass();
        for (Launcher launcher : this.launchers){
            totalMass += launcher.getTotalMass();
        }
        for (Motor motor : this.motors){
            totalMass += motor.getTotalMass();
        }
        return totalMass;
    }
}