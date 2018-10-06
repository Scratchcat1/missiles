import java.util.ArrayList;

public class Launcher extends Entity{
    private int maxCapacity;
    private ArrayList<Missile> missiles;
    
    public Launcher(double launcherMass, int health, int maxHealth, Angle3D rotationRateLimit, int maxCapacity) {
        super(launcherMass, health, maxHealth, rotationRateLimit);
        this.maxCapacity = maxCapacity;
        this.missiles = new ArrayList<>();
    }

    public boolean addMissile(Missile missile) {
        if (this.missiles.size() != this.maxCapacity) {
            this.missiles.add(missile);
            return true;
        } else {
            return false;
        }
    }

    public Missile launchMissile(int index) {
        Missile missile = this.missiles.remove(index);
        return missile;
    }
    
    public int getRemainingMissiles() {
        return this.missiles.size();
    }

    @Override
    public double getTotalMass() {
        double totalMass = this.getMass();
        for (Missile missile : this.missiles) {
            totalMass += missile.getTotalMass();
        }
        return totalMass;
    }

    public void setKinetics(Vector position, Vector velocity) {
        this.setPosition(position);
        this.setVelocity(velocity);

        for (Missile missile : this.missiles) {
            missile.setKinetics(position, velocity, this.getAngle());
        }
    }
}