import java.util.ArrayList;

public class World{
    ArrayList<City> cities;
    ArrayList<LaunchPlatform> launchPlatforms;
    ArrayList<Launcher> launchers;
    ArrayList<Missile> missiles;
    ArrayList<Warhead> warheads;
    ArrayList<Explosion> explosions;

    public void step(double timeStep){
        for (LaunchPlatform launchPlatform : this.launchPlatforms){
            this.missiles.addAll(launchPlatform.launchMissiles());
        }
        for (Missile missile : this.missiles){
            this.warheads.addAll(missile.launchWarheads());
        }

        for (LaunchPlatform launchPlatform : this.launchPlatforms){
            //update targeting
        }
        for (Missile missile : this.missiles){
            //update targeting
        }

        this.updateAll(timeStep);

        this.removeDestroyed();
    }

    public void updateAll(double timeStep){
        for (LaunchPlatform launchPlatform : this.launchPlatforms){
            launchPlatform.update(timeStep);
        }
        for (Missile missile : this.missiles){
            missile.update(timeStep);
        }
        for (Warhead warhead : this.warheads){
            warhead.update(timeStep);
        }
        for (Explosion explosion : this.explosions){
            explosion.update(timeStep);
        }
    }

    protected void removeDestroyed(){
        this.cities.removeIf(item -> item.getHealth() <= 0);
        this.launchPlatforms.removeIf(item -> item.getHealth() <= 0);
        this.missiles.removeIf(item -> item.getHealth() <= 0);
        this.warheads.removeIf(item -> item.getHealth() <= 0);
        this.explosions.removeIf(item -> item.getHealth() <= 0);
    }
}

