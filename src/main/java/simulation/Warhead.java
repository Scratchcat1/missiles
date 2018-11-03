package simulation;
import java.util.ArrayList;

public class Warhead extends Entity{
    private int damage;
    private double detonationRadius;
    private double detonationSpeed;
    private FlightController FlightController;
    private static double ACCIDENTAL_DETONATION_PROBABILITY = 0.01;

    public Warhead(double mass, int health, int maxHealth, Angle3D rotationRateLimit, int damage, double detonationRadius, double detonationSpeed, FlightController FlightController) {
        super(mass, health, maxHealth, rotationRateLimit);
        this.damage = damage;
        this.detonationRadius = detonationRadius;
        this.detonationSpeed = detonationSpeed;
        this.FlightController = FlightController;
    }

    public void update(double airResistance, double gravAccel, double timeStep) {
        this.updateKinetics(new Vector(), airResistance, gravAccel, timeStep);
        this.rotate(this.FlightController.getRotationRate(this), timeStep);
    }

    public ArrayList<Explosion> launchExplosions() {
        ArrayList<Explosion> explosions = new ArrayList<Explosion>();
        if (this.FlightController.shouldDetonate(this)) {
            this.setHealth(0);
            explosions.add(new Explosion(this.getPosition(), this.damage, this.detonationRadius, this.detonationSpeed));
        }
        return explosions;
    }
}