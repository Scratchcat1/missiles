public class City extends Entity{
    static double GROWTH_RATE = 1.1;
    double radius;

    public City(int health){
        super(Double.POSITIVE_INFINITY, health, health);
    }

    public void update(double timeStep){
        int healthChange = (int) (this.getHealth() * City.GROWTH_RATE * timeStep);
        this.modifyHealth(healthChange);
    }
}