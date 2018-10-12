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

    public static void main(String[] args) {
        World world = new World();  
        world.setupBasic();      
        
        for (int i = 0; i < 10000; i++) {
            long startTime = System.nanoTime();
            world.step(1);
            //world.debugOutput(i);
            try {
                TimeUnit.MILLISECONDS.sleep(0);
            } catch (Exception e) {
            }
            long endTime = System.nanoTime();
            System.out.println("Step " + String.format("%5d", i) + " took " + (endTime - startTime) + " nanoseconds");
        }

    }

    public void setupBasic() {
        Vector minPosition = new Vector();
        Vector maxPosition = new Vector(new double[]{10000, 10000, 10000});

        for (City city : Armory.createBasicCities(10)) {
            city.randomisePosition(minPosition, maxPosition);
            this.addCity(city);
        }

        for (LaunchPlatform launchPlatform : Armory.createBasicLaunchPlatforms(100)) {
            launchPlatform.randomisePosition(minPosition, maxPosition);
            this.addLaunchPlatform(launchPlatform);
        }
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

    // public Entity[] getState() {
    //     Entity[] state = new Entity[this.cities.size() + this.launchPlatforms.size() + this.explosions.size() + this.missiles.size() + this.warheads.size()]; 
    //     int pos = 0;
        
    //     for (City city : this.cities) {
    //         state[pos++] = city;
    //     }
    //     for (LaunchPlatform launchPlatform : this.launchPlatforms) {
    //         state[pos++] = launchPlatform;
    //     }
    //     for (Missile missile : this.missiles) {
    //         state[pos++] = missile;
    //     }
    //     for (Warhead warhead : this.warheads) {
    //         state[pos++] = warhead;
    //     }
    //     for (Explosion explosion : this.explosions) {
    //         state[pos++] = explosion;
    //     }
        
    //     return state;
    // }

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