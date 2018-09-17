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

    public int remainingMissiles(){
        return this.missiles.size();
    }
}