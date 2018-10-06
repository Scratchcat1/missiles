package simulation;
public class City extends Entity{
    private static double GROWTH_RATE = 1.1;
    private double radius;

    public City(int health) {
        super(Double.POSITIVE_INFINITY, health, health, new Angle3D());
    }

    public void update(double timeStep) {
        int healthChange = (int) (this.getHealth() * City.GROWTH_RATE * timeStep);
        this.modifyHealth(healthChange);
    }

    @Override
    public double getCollisionRadius() {
        return this.radius;
    }
}