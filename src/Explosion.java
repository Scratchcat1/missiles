public class Explosion extends Entity{
    private int damage;
    private double detonationRadius;
    private double detonationSpeed;
    private double currentRadius;

    public Explosion(Vector position, int damage, double detonationRadius, double detonationSpeed){
        super(Double.POSITIVE_INFINITY, 1, 1, new Angle3D(new double[]{0, 0, 0}));
        this.setPosition(position);
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

    @Override
    public double getCollisionRadius(){
        return this.currentRadius;
    }
}