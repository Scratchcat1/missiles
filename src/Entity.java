public class Entity{
    Vector position = new Vector(3);
    Vector velocity = new Vector(3);
    Angle3D angle = new Angle3D();
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

    public void randomisePosition(Vector minPosition, Vector maxPosition){
        for (int i = 0; i < this.position.length(); i++){
            this.position.set(i, Math.random() * (maxPosition.get(i) - minPosition.get(i)) + minPosition.get(i));
        }
    }

    public void setVelocity(Vector newVelocity){
        this.velocity = newVelocity;
    }

    public Vector getVelocity(){
        return this.velocity;
    }

    public void setAngle(Angle3D newAngle){
        this.angle = newAngle;
    }

    public Angle3D getAngle(){
        return this.angle;
    }

    public void rotate(Angle3D rotationAngle){
        this.angle.rotate(rotationAngle);   
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
    public void updateKinetics(Vector position, Vector velocity, Angle3D angle){
        this.setPosition(position);
        this.setVelocity(velocity);
        this.setAngle(angle);
    }

    public void applyForce(Vector motorForce, double airResistance, double gravAccel, double timeStep){
        Vector acceleration = motorForce.mult( 1 / this.getMass());
        acceleration.set(2, gravAccel + acceleration.get(2));

        Vector airResistForce = new Vector(3);
        for (int i = 0; i < airResistForce.length(); i++){
            airResistForce.set(i, this.velocity.get(i) * Math.abs(this.velocity.get(i)) * airResistance * Math.pow(Math.E, -this.position.get(2)/15000));
        }
        acceleration = acceleration.add(airResistForce.negation());

        this.velocity = this.velocity.add(acceleration.mult(timeStep));
    }

    public void update(double timeStep){
        this.move(timeStep);
    }

    /** Return the distance from the position vector to the most distance point of the entity. Default is to assume the entity is a point object */
    public double getCollisionRadius(){
        return 1;
    }

    public void status(){
        System.out.print("Pos");
        this.position.print();
        System.out.print("Vel");
        this.velocity.print();
        System.out.print("Ang");
        this.angle.toVector(1).print();
    }
}
