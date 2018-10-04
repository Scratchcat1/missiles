import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class World{
    static double airResistance = 0.001;
    static double gravAccel = -9.8;
    private ArrayList<City> cities;
    private ArrayList<LaunchPlatform> launchPlatforms;
    // ArrayList<Launcher> launchers;
    private ArrayList<Missile> missiles;
    private ArrayList<Warhead> warheads;
    private ArrayList<Explosion> explosions;

    public World(){
        this.reset();
    }

    public static void main(String[] args){
        World world = new World();

        Vector minPosition = new Vector(3);
        Vector maxPosition = new Vector(new double[]{10000, 10000, 10000});

        for (City city : Armory.createBasicCities(10)){
            city.randomisePosition(minPosition, maxPosition);
            world.addCity(city);
        }

        for (LaunchPlatform launchPlatform : Armory.createBasicLaunchPlatforms(1000)){
            // launchPlatform.randomisePosition(minPosition, maxPosition);
            world.addLaunchPlatform(launchPlatform);
        }
        
        
        for (int i = 0; i < 10000; i++){
            long startTime = System.nanoTime();
            world.step(1);
            //world.debugOutput(i);
            try{
                TimeUnit.MILLISECONDS.sleep(0);
            } catch (Exception e) {}
            long endTime = System.nanoTime();
            System.out.println("Step " + String.format("%5d", i) + " took " + (endTime - startTime) + " nanoseconds");
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
        for (Warhead warhead : this.warheads){
            this.explosions.addAll(warhead.launchExplosions());
        }

        for (LaunchPlatform launchPlatform : this.launchPlatforms){
            //update targeting
        }
        for (Missile missile : this.missiles){
            //update targeting
        }

        this.updateAll(timeStep);

        this.runCollisions();

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

    public void runCollisions(){
        SpatialHasher spatialHasher = new SpatialHasher(new double[]{1000, 1000, 1000});

        for (LaunchPlatform launchPlatform : this.launchPlatforms){
            spatialHasher.add(launchPlatform);
        }
        for (Missile missile : this.missiles){
            spatialHasher.add(missile);
        }
        for (Warhead warhead : this.warheads){
            spatialHasher.add(warhead);
        }
        
        for (Explosion explosion : this.explosions){
            ArrayList<Entity> nearbyEntities = spatialHasher.get(explosion);
            for (Entity entity : nearbyEntities){
                if (explosion.isTouching(entity)){
                    int damage = explosion.getPointDamage(entity.getPosition());
                    entity.modifyHealth(-damage);
                }
            }
        }
    }

    protected void removeDestroyed(){
        this.cities.removeIf(item -> item.getHealth() <= 0);
        this.launchPlatforms.removeIf(item -> item.getHealth() <= 0);
        this.missiles.removeIf(item -> item.getHealth() <= 0);
        this.warheads.removeIf(item -> item.getHealth() <= 0);
        this.explosions.removeIf(item -> item.getHealth() <= 0);
    }

    public void debugOutput(int step){
        System.out.println("-----------STEP" + step);
        System.out.println("LPS");
        for (LaunchPlatform launchPlatform : this.launchPlatforms){
            launchPlatform.status();
        }

        System.out.println("Missiles");
        for (Missile missile : this.missiles){
            missile.status();
        }

        System.out.println("Warheads");
        for (Warhead warhead : this.warheads){
            warhead.status();
        }
        System.out.println("Explosions");
        for (Explosion explosion : this.explosions){
            explosion.status();
        }
    }
}