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

    public void move(){
        this.position = this.position.add(this.velocity);
    }

    public void applyForce(Vector force){
        Vector acceleration = force.mult(this.getMass());
        this.velocity = this.velocity.add(acceleration);
    }

}
