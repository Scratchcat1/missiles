package simulation;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class World {
    static double airResistance = 0.001;
    static double gravAccel = -9.8;
    private ArrayList<City> cities;
    private ArrayList<LaunchPlatform> launchPlatforms;
    // ArrayList<Launcher> launchers;
    private ArrayList<Missile> missiles;
    private ArrayList<Warhead> warheads;
    private ArrayList<Explosion> explosions;

    public World() {
        this.reset();
    }

    public void reset() {
        this.cities = new ArrayList<City>();
        this.launchPlatforms = new ArrayList<LaunchPlatform>();
        this.missiles = new ArrayList<Missile>();
        this.warheads = new ArrayList<Warhead>();
        this.explosions = new ArrayList<Explosion>();
    }

    public void addCity(City city) {
        this.cities.add(city);
    }

    public ArrayList<City> getCities() {
        return this.cities;
    }

    public void addLaunchPlatform(LaunchPlatform launchPlatform) {
        this.launchPlatforms.add(launchPlatform);
    }

    public ArrayList<LaunchPlatform> getLaunchPlatforms() {
        return this.launchPlatforms;
    }

    public void addMissiles(Missile missile) {
        this.missiles.add(missile);
    }

    public ArrayList<Missile> getMissiles() {
        return this.missiles;
    }

    public void addWarheads(Warhead warhead) {
        this.warheads.add(warhead);
    }

    public ArrayList<Warhead> getWarheads() {
        return this.warheads;
    }

    public ArrayList<Explosion> getExplosions() {
        return this.explosions;
    }

    /** Move one step through the simulation for the given time step */
    public void step(double timeStep) {
        for (LaunchPlatform launchPlatform : this.launchPlatforms) {
            this.missiles.addAll(launchPlatform.launchMissiles());
        }
        for (Missile missile : this.missiles) {
            this.warheads.addAll(missile.launchWarheads());
        }
        for (Warhead warhead : this.warheads) {
            this.explosions.addAll(warhead.launchExplosions());
        }

        for (LaunchPlatform launchPlatform : this.launchPlatforms) {
            //update targeting
        }
        for (Missile missile : this.missiles) {
            //update targeting
        }

        this.updateAll(timeStep);

        this.runCollisions();

        this.removeDestroyed();
    }

    public void updateAll(double timeStep) {
        for (LaunchPlatform launchPlatform : this.launchPlatforms) {
            launchPlatform.update(World.airResistance, World.gravAccel, timeStep);
        }
        for (Missile missile : this.missiles) {
            missile.update(World.airResistance, World.gravAccel, timeStep);
        }
        for (Warhead warhead : this.warheads) {
            warhead.update(World.airResistance, World.gravAccel, timeStep);
        }
        for (Explosion explosion : this.explosions) {
            explosion.update(timeStep);
        }
    }

    public void runCollisions() {
        SpatialHasher spatialHasher = new SpatialHasher(new double[]{1000, 1000, 1000});

        for (LaunchPlatform launchPlatform : this.launchPlatforms) {
            spatialHasher.add(launchPlatform);
        }
        for (Missile missile : this.missiles) {
            spatialHasher.add(missile);
        }
        for (Warhead warhead : this.warheads) {
            spatialHasher.add(warhead);
        }
        
        for (Explosion explosion : this.explosions) {
            ArrayList<Entity> nearbyEntities = spatialHasher.get(explosion);
            for (Entity entity : nearbyEntities) {
                if (explosion.isTouching(entity)) {
                    int damage = explosion.getPointDamage(entity.getPosition());
                    entity.modifyHealth(-damage);
                }
            }
        }
    }

    protected void removeDestroyed() {
        this.cities.removeIf(item -> item.getHealth() <= 0);
        this.launchPlatforms.removeIf(item -> item.getHealth() <= 0);
        this.missiles.removeIf(item -> item.getHealth() <= 0);
        this.warheads.removeIf(item -> item.getHealth() <= 0);
        this.explosions.removeIf(item -> item.getHealth() <= 0);
    }

    public void debugOutput(int step) {
        System.out.println("-----------STEP" + step);
        System.out.println("LPS");
        for (LaunchPlatform launchPlatform : this.launchPlatforms) {
            launchPlatform.status();
        }

        System.out.println("Missiles");
        for (Missile missile : this.missiles) {
            missile.status();
        }

        System.out.println("Warheads");
        for (Warhead warhead : this.warheads) {
            warhead.status();
        }
        System.out.println("Explosions");
        for (Explosion explosion : this.explosions) {
            explosion.status();
        }
    }
}