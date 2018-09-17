public class Entity{
    Vector position = new Vector();
    Vector velocity = new Vector();
    Vector angle = new Vector();
    double mass;
    int health;
    int maxHealth;

    public Entity(double mass, int health, int maxHealth){
        this.mass = mass;
        this.health = health;
        this.health = maxHealth;
    }

    public void setPosition(Vector newPosition){
        this.position = newPosition;
    }

    public Vector getPosition(){
        return this.position;
    }

    public void setVelocity(Vector newVelocity){
        this.velocity = newVelocity;
    }

    public Vector getVelocity(){
        return this.velocity;
    }

    public void setAngle(Vector newAngle){
        this.angle = newAngle;
    }

    public Vector getAngle(){
        return this.angle;
    }

    public void setMass(double mass){
        this.mass = mass;
    }

    public double getMass(){
        return this.mass;
    }

    public void setHealth(int health){
        this.health = health;
    }

    public void modifyHealth(int healthChange){
        int newHealth = this.health + healthChange;
        this.health = Math.min(newHealth, this.maxHealth);
    }

    public int getHealth(){
        return this.health;
    }

    public void move(double timeStep){
        this.position = this.position.add(this.velocity.mult(timeStep));
    }

    /** Updates kinetic properties of entity while attached to another object. Override to pass information to children */
    public void updateKinetics(Vector position, Vector velocity, Vector angle){
        this.setPosition(position);
        this.setVelocity(velocity);
        this.setAngle(angle);
    }

    public void applyForce(Vector force, double timeStep){
        Vector acceleration = force.mult(this.getMass());
        this.velocity = this.velocity.add(acceleration.mult(timeStep));
    }

}
