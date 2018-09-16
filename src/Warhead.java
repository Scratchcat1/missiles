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

    public Explosion detonate(){
        return new Explosion(this.getPosition(), this.damage, this.detonationRadius, this.detonationSpeed);
    }
}