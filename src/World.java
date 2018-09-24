import java.util.ArrayList;

public class World{
    static double airResistance = 1;
    static double gravAccel = -9.8;
    ArrayList<City> cities;
    ArrayList<LaunchPlatform> launchPlatforms;
    // ArrayList<Launcher> launchers;
    ArrayList<Missile> missiles;
    ArrayList<Warhead> warheads;
    ArrayList<Explosion> explosions;

    public World(){
        this.reset();
    }

    public static void main(String[] args){
        World world = new World();
        double[] maxPositionArray = {10000, 10000, 10000};
        Vector minPosition = new Vector(3);
        Vector maxPosition = new Vector(maxPositionArray);

        for (City city : Armory.createBasicCities(10)){
            city.randomisePosition(minPosition, maxPosition);
            world.addCity(city);
        }

        for (LaunchPlatform launchPlatform : Armory.createBasicLaunchPlatforms(10)){
            launchPlatform.randomisePosition(minPosition, maxPosition);
            world.addLaunchPlatform(launchPlatform);
        }

        for (int i = 0; i < 10000; i++){
            world.step(1);
            System.out.println("step");
        }
    }

    public void reset(){
        this.cities = new ArrayList<City>();
        this.launchPlatforms = new ArrayList<LaunchPlatform>();
        this.missiles = new ArrayList<Missile>();
        this.warheads = new ArrayList<Warhead>();
        this.explosions = new ArrayList<Explosion>();
    }

    public void addCity(City city){
        this.cities.add(city);
    }

    public void addLaunchPlatform(LaunchPlatform launchPlatform){
        this.launchPlatforms.add(launchPlatform);
    }

    public void addMissiles(Missile missile){
        this.missiles.add(missile);
    }

    public void addWarheads(Warhead warhead){
        this.warheads.add(warhead);
    }

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
            launchPlatform.update(World.airResistance, World.gravAccel, timeStep);
        }
        for (Missile missile : this.missiles){
            missile.update(World.airResistance, World.gravAccel, timeStep);
        }
        for (Warhead warhead : this.warheads){
            warhead.update(World.airResistance, World.gravAccel, timeStep);
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