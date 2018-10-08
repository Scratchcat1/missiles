package simulation;
import java.util.ArrayList;

public class Armory{
    public static ArrayList<Warhead> createBasicWarheads(int count) {
        ArrayList<Warhead> warheads = new ArrayList<Warhead>();
        for (int i = 0; i < count; i++) {
            warheads.add(new Warhead(10, 30, 30, new Angle3D(new double[]{0.1, 0.1, 0.1}), 100, 1000, 200, new Targeting(new Vector())));
        }
        return warheads;
    }

    public static ArrayList<Motor> createBasicMotors(int count) {
        ArrayList<Motor> motors = new ArrayList<Motor>();
        for (int i = 0; i < count; i++) {
            motors.add(new Motor(100, 50, 50, 0.01, 50, 10000, 0.5));
        }
        return motors;
    }

    public static ArrayList<Missile> createBasicMissiles(int count) {
        ArrayList<Missile> missiles = new ArrayList<Missile>();
        for (int i = 0; i < count; i++) {
            missiles.add(new Missile(300, 100, 100, new Angle3D(new double[]{0.1, 0.1, 0.1}), Armory.createBasicWarheads(3), Armory.createBasicMotors(1), new Targeting(new Vector())));
        }
        return missiles;
    }

    public static ArrayList<Launcher> createBasicLaunchers(int count) {
        ArrayList<Launcher> launchers = new ArrayList<Launcher>();
        for (int i = 0; i < count; i++) {
            Launcher launcher = new Launcher(200, 200, 200, new Angle3D(new double[]{0.1, 0.1, 0.1}), 10);
            for (Missile missile : Armory.createBasicMissiles(3)) {
                launcher.addMissile(missile);
            }
            launchers.add(launcher);

        }
        return launchers;
    }

    public static ArrayList<LaunchPlatform> createBasicLaunchPlatforms(int count) {
        ArrayList<LaunchPlatform> launchPlatforms = new ArrayList<LaunchPlatform>();
        for (int i = 0; i < count; i++) {
            LaunchPlatform launchPlatform = new LaunchPlatform(10000, 1000, 1000, new Angle3D(new double[]{0.1, 0.1, 0.1}), Armory.createBasicLaunchers(2), Armory.createBasicMotors(30), new Targeting(new Vector()));
            //launchPlatform.setAngle(new Angle3D(new double[]{0, 0, 0}));
            launchPlatforms.add(launchPlatform);
        }
        return launchPlatforms;
    }

    public static ArrayList<City> createBasicCities(int count) {
        ArrayList<City> cities = new ArrayList<City>();
        for (int i = 0; i < count; i++) {
            cities.add(new City(10000));
        }
        return cities;
    }
}