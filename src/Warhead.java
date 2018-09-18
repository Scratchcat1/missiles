public class Warhead extends Entity{
    int damage;
    double detonationRadius;
    double detonationSpeed;
    static double ACCIDENTAL_DETONATION_PROBABILITY = 0.01;

    public Warhead(double mass, int health, int maxHealth, double detonationRadius, double detonationSpeed){
        super(mass, health, maxHealth);
        this.detonationRadius = detonationRadius;
        this.detonationSpeed = detonationSpeed;
    }

    public void update(double timeStep){
        this.move(timeStep);
    }

    public Explosion detonate(){
        this.setHealth(0);
        return new Explosion(this.getPosition(), this.damage, this.detonationRadius, this.detonationSpeed);
    }
}