package simulation;
import java.util.ArrayList;

public class LaunchPlatform extends Entity {
    private ArrayList<Launcher> launchers;
    private ArrayList<Motor> motors;
    private FlightController FlightController;

    public LaunchPlatform(double launchPlatformMass, int health, int maxHealth, Angle3D rotationRateLimit, ArrayList<Launcher> launchers, ArrayList<Motor> motors, FlightController FlightController) {
        super(launchPlatformMass, health, maxHealth, rotationRateLimit);
        this.launchers = launchers;
        this.motors = motors;
        this.FlightController = FlightController;
    }

    @Override
    public void update(double airResistance, double gravAccel, double timeStep) {
        Vector motorForce = new Vector();
        for (Motor motor : this.motors) {
            motor.setDutyCycle(this.FlightController.getDutyCycle(motor));
            motor.update(timeStep);
            motorForce = motorForce.add(motor.getForce());
        }

        this.updateKinetics(motorForce, airResistance, gravAccel, timeStep);
        this.rotate(this.FlightController.getRotationRate(this), timeStep);

        for (Motor motor : this.motors) {
            motor.setKinetics(this.getPosition(), this.getVelocity(), this.getAngle());
        }
    }

    public ArrayList<Missile> launchMissiles() {
        ArrayList<Missile> launchedMissiles = new ArrayList<>();
        for (Launcher launcher : this.launchers) {
            launcher.setKinetics(this.getPosition(), this.getVelocity());
            if (launcher.getRemainingMissiles() > 0 && this.FlightController.shouldLaunch(launcher)) {
                launchedMissiles.add(launcher.launchMissile(0));
            }
        }
        return launchedMissiles;
    }

    @Override
    public double getTotalMass() {
        double totalMass = this.getMass();
        for (Launcher launcher : this.launchers) {
            totalMass += launcher.getTotalMass();
        }
        for (Motor motor : this.motors) {
            totalMass += motor.getTotalMass();
        }
        return totalMass;
    }
}