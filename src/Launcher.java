import java.util.ArrayList;

public class Launcher extends Entity{
    int maxCapacity;
    ArrayList<Missile> missiles;
    
    public Launcher(double launcherMass, int health, int maxHealth, int maxCapacity){
        super(launcherMass, health, maxHealth);
        this.maxCapacity = maxCapacity;
        this.missiles = new ArrayList<>();
    }

    public boolean addMissile(Missile missile){
        if (this.missiles.size() != this.maxCapacity){
            this.missiles.add(missile);
            return true;
        } else {
            return false;
        }
    }

    public Missile launchMissile(int index){
        Missile missile = this.missiles.remove(index);
        return missile;
    }

    public int getRemainingMissiles(){
        return this.missiles.size();
    }

    public double getMass(){
        double totalMass = this.mass;
        for (Missile missile : this.missiles){
            totalMass += missile.getMass();
        }
        return totalMass;
    }

    public void updateKinetics(Vector position, Vector velocity, Vector angle){
        this.setPosition(position);
        this.setVelocity(velocity);
        this.setAngle(angle);

        for (Missile missile : this.missiles){
            missile.updateKinetics(position, velocity, angle);
        }
    }
}