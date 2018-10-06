package simulation;
import java.util.ArrayList;

public class Warhead extends Entity{
    private int damage;
    private double detonationRadius;
    private double detonationSpeed;
    private Targeting targeting;
    private static double ACCIDENTAL_DETONATION_PROBABILITY = 0.01;

    public Warhead(double mass, int health, int maxHealth, Angle3D rotationRateLimit, int damage, double detonationRadius, double detonationSpeed, Targeting targeting) {
        super(mass, health, maxHealth, rotationRateLimit);
        this.damage = damage;
        this.detonationRadius = detonationRadius;
        this.detonationSpeed = detonationSpeed;
        this.targeting = targeting;
    }

    public void update(double airResistance, double gravAccel, double timeStep) {
        this.updateKinetics(new Vector(3), airResistance, gravAccel, timeStep);
        this.rotate(this.targeting.getRotationRate(this), timeStep);
    }

    public ArrayList<Explosion> launchExplosions() {
        ArrayList<Explosion> explosions = new ArrayList<Explosion>();
        if (this.targeting.shouldDetonate(this)) {
            this.setHealth(0);
            explosions.add(new Explosion(this.getPosition(), this.damage, this.detonationRadius, this.detonationSpeed));
        }
        return explosions;
    }
}