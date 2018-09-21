import java.util.ArrayList;

public class Armory{
    public static ArrayList<Warhead> createBasicWarheads(int count){
        ArrayList<Warhead> warheads = new ArrayList<Warhead>();
        for (int i = 0; i < count; i++){
            warheads.add(new Warhead(10, 30, 30, 1000, 100));
        }
        return warheads;
    }

    public static ArrayList<Motor> createBasicMotors(int count){
        ArrayList<Motor> motors = new ArrayList<Motor>();
        for (int i = 0; i < count; i++){
            motors.add(new Motor(50, 50, 50, 0.01, 50, 10000, 0.5));
        }
        return motors;
    }

    public static ArrayList<Missile> createBasicMissiles(int count){
        ArrayList<Missile> missiles = new ArrayList<Missile>();
        for (int i = 0; i < count; i++){
            missiles.add(new Missile(100, 100, 100, Armory.createBasicWarheads(3), Armory.createBasicMotors(3), new Targeting(new Vector(3))));
        }
        return missiles;
    }

    public static ArrayList<Launcher> createBasicLaunchers(int count){
        ArrayList<Launcher> launchers = new ArrayList<Launcher>();
        for (int i = 0; i < count; i++){
            Launcher launcher = new Launcher(200, 200, 200, 10);
            for (Missile missile : Armory.createBasicMissiles(3)){
                launcher.addMissile(missile);
            }
            launchers.add(launcher);

        }
        return launchers;
    }

    public static ArrayList<LaunchPlatform> createBasicLaunchPlatforms(int count){
        ArrayList<LaunchPlatform> launchPlatforms = new ArrayList<LaunchPlatform>();
        for (int i = 0; i < count; i++){
            launchPlatforms.add(new LaunchPlatform(1000, 1000, 1000, Armory.createBasicLaunchers(10), Armory.createBasicMotors(10), new Targeting(new Vector(3))));
        }
        return launchPlatforms;
    }

    public static ArrayList<City> createBasicCities(int count){
        ArrayList<City> cities = new ArrayList<City>();
        for (int i = 0; i < count; i++){
            cities.add(new City(10000));
        }
        return cities;
    }

    public static ArrayList<Entity> randomisePosition(ArrayList<Entity> entities, Vector minPosition, Vector maxPosition){
        for (Entity entity : entities){
            Vector newPosition = new Vector(3);
            for (int i = 0; i < newPosition.length(); i++){
                newPosition.set(i, Math.random() * (maxPosition.get(i) - minPosition.get(i)) + minPosition.get(i));
            }
            entity.setPosition(newPosition);
        }
        return entities;
    }
}