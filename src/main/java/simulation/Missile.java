package simulation;
import java.util.ArrayList;

public class Missile extends Entity{
    private ArrayList<Warhead> warheads;
    private ArrayList<Motor> motors;
    private FlightController FlightController;

    public Missile(double missileMass, int health, int maxHealth, Angle3D rotationRateLimit, ArrayList<Warhead> warheads, ArrayList<Motor> motors, FlightController FlightController) {
        super(missileMass, health, maxHealth, rotationRateLimit);
        this.warheads = warheads;
        this.motors = motors;
        this.FlightController = FlightController;
    }

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

    public ArrayList<Warhead> launchWarheads() {
        ArrayList<Integer> launchingWarheadPositions = new ArrayList<Integer>();
        for (int i = 0; i < this.warheads.size(); i++) {
            Warhead warhead = this.warheads.get(i);
            warhead.setKinetics(this.getPosition(), this.getVelocity(), this.getAngle());

            if (this.getRemainingWarheads() > 0 && this.FlightController.shouldLaunch(warhead)) {
                launchingWarheadPositions.add(i);
            }
        }

        ArrayList<Warhead> launchingWarheads = new ArrayList<Warhead>();
        for (int i = 0; i < launchingWarheadPositions.size(); i++) {
            Warhead warhead = this.warheads.remove(launchingWarheadPositions.get(i) - i);
            launchingWarheads.add(warhead);
        }

        return launchingWarheads;
    }

    public int getRemainingWarheads() {
        return this.warheads.size();
    }

    @Override
    public double getTotalMass() {
        double totalMass = this.getMass();
        for (Warhead warhead : this.warheads) {
            totalMass += warhead.getTotalMass();
        }
        for (Motor motor : this.motors) {
            totalMass += motor.getTotalMass();
        }
        return totalMass;
    }

    @Override
    public void setKinetics(Vector position, Vector velocity, Angle3D angle) {
        this.setPosition(position);
        this.setVelocity(velocity);
        this.setAngle(angle);
        
        for (Motor motor : this.motors) {
            motor.setKinetics(position, velocity, angle);
        }

        for (Warhead warhead : this.warheads) {
            warhead.setKinetics(position, velocity, angle);
        }
    }
}