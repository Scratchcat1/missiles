public class Explosion extends Entity{
    int damage;
    double detonationRadius;
    double detonationSpeed;
    double currentRadius;

    public Explosion(Vector position, int damage, double detonationRadius, double detonationSpeed){
        super(Double.POSITIVE_INFINITY, 1, 1);
        this.position = position;
        this.damage = damage;
        this.detonationRadius = detonationRadius;
        this.detonationSpeed = detonationSpeed;
    }

    public int getPointDamage(Vector position){
        double distance = this.getPosition().distance(position);
        int damage = (int) (this.damage / Math.pow(1 - (distance / this.detonationRadius), 2));
        return damage;
    }

    public void update(double timeStep){
        this.currentRadius += this.detonationSpeed * timeStep;
        if (this.currentRadius >= this.detonationRadius){
            this.setHealth(0);
        }
    }

    public double getCollisionRadius(){
        return this.currentRadius;
    }
}