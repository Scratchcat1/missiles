public class Warhead extends Entity{
    private int damage;
    private double detonationRadius;
    private double detonationSpeed;
    private static double ACCIDENTAL_DETONATION_PROBABILITY = 0.01;

    public Warhead(double mass, int health, int maxHealth, int damage, double detonationRadius, double detonationSpeed){
        super(mass, health, maxHealth);
        this.damage = damage;
        this.detonationRadius = detonationRadius;
        this.detonationSpeed = detonationSpeed;
    }

    public void update(double airResistance, double gravAccel, double timeStep){
        this.applyForce(new Vector(3), airResistance, gravAccel, timeStep);
        this.move(timeStep);
    }

    public Explosion detonate(){
        this.setHealth(0);
        return new Explosion(this.getPosition(), this.damage, this.detonationRadius, this.detonationSpeed);
    }
}