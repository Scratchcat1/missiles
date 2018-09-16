public class Explosion extends Entity{
    int damage;
    double detonationRadius;
    double detonationSpeed;
    double currentRadius;

    public Explosion(Vector position, int damage, double detonationRadius, double detonationSpeed){
        super(Double.POSITIVE_INFINITY, 1, 1);
        this.detonationRadius = detonationRadius;
        this.detonationSpeed = detonationSpeed;
    }

    public void update(double timeStep){
        this.currentRadius += this.detonationSpeed * timeStep;
        if (this.currentRadius >= this.detonationRadius){
            this.setHealth(0);
        }
    }
}