package simulation;
public class Entity{
    private Vector position = new Vector();
    private Vector velocity = new Vector();
    private Angle3D angle = new Angle3D();
    private Angle3D rotationRateLimit = new Angle3D();
    private double mass;
    private int health;
    private int maxHealth;

    public Entity(double mass, int health, int maxHealth, Angle3D rotationRateLimit) {
        this.mass = mass;
        this.health = health;
        this.maxHealth = maxHealth;
        this.rotationRateLimit = rotationRateLimit;
    }

    public void setPosition(Vector newPosition) {
        this.position = newPosition;
    }

    public Vector getPosition() {
        return this.position;
    }

    public void randomisePosition(Vector minPosition, Vector maxPosition) {
        for (int i = 0; i < this.position.length(); i++) {
            this.position.set(i, Math.random() * (maxPosition.get(i) - minPosition.get(i)) + minPosition.get(i));
        }
    }

    public void setVelocity(Vector newVelocity) {
        this.velocity = newVelocity;
    }

    public Vector getVelocity() {
        return this.velocity;
    }

    public void setAngle(Angle3D newAngle) {
        this.angle = newAngle;
    }

    public Angle3D getAngle() {
        return this.angle;
    }

    public void rotate(Angle3D rotationVelocity, double timeStep) {
        Angle3D rotationAngle = new Angle3D();
        for (int i = 0; i < 3; i++) {
            double rotationLimit = this.rotationRateLimit.get(i); // Disabled for now. / this.getTotalMass();
            rotationAngle.set(i, timeStep * Math.max(-rotationLimit, Math.min(rotationLimit, rotationVelocity.get(i))));
        }
        this.angle = this.angle.rotate(rotationAngle);   
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getMass() {
        return this.mass;
    }

    //** Return the mass of the object plus any child objects */
    public double getTotalMass() {
        return this.getMass();
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void modifyHealth(int healthChange) {
        int newHealth = this.health + healthChange;
        this.health = Math.min(newHealth, this.maxHealth);
    }

    public int getHealth() {
        return this.health;
    }

    public Vector getAcceleration(Vector motorForce, double airResistance, double gravAccel, double timeStep) {
        Vector acceleration = motorForce.mult( 1 / this.getTotalMass());
        acceleration.set(2, gravAccel + acceleration.get(2));

        Vector airResistForce = new Vector();
        for (int i = 0; i < airResistForce.length(); i++) {
            airResistForce.set(i, this.velocity.get(i) * Math.abs(this.velocity.get(i)) * airResistance * Math.pow(Math.E, -this.position.get(2)/15000));
        }
        acceleration = acceleration.minus(airResistForce);

        return acceleration;
    }

    /** Updates kinetic properties of entity while attached to another object. Override to pass information to children */
    public void setKinetics(Vector position, Vector velocity, Angle3D angle) {
        this.setPosition(position);
        this.setVelocity(velocity);
        this.setAngle(angle);
    }

    public void updateKinetics(Vector motorForce, double airResistance, double gravAccel, double timeStep) {
        Vector acceleration = this.getAcceleration(motorForce, airResistance, gravAccel, timeStep);

        this.position = this.position.add(this.velocity.mult(timeStep).add(acceleration.mult(0.5 * Math.pow(timeStep, 2))));  // p' = p + (ut + (a * 0.5 * t^2))
        if (this.position.get(2) < 0) {
            this.position.set(2, 0);
            this.velocity.set(2, 0);
        }

        this.velocity = this.velocity.add(acceleration.mult(timeStep));
    }

    public void update(double airResistance, double gravAccel, double timeStep) {
        this.updateKinetics(new Vector(), airResistance, gravAccel, timeStep);
    }

    /** Return the distance from the position vector to the most distance point of the entity. Default is to assume the entity is a point object */
    public double getCollisionRadius() {
        return 0;
    }

    public boolean isTouching(Entity otherEntity) {
        double radius = Math.max(this.getCollisionRadius(), otherEntity.getCollisionRadius());
        if (this.getPosition().distance(otherEntity.getPosition()) <= radius) {
            return true;
        } else {
            return false;
        }
    }

    public void status() {
        System.out.println("Pos:      " + this.position.toString());
        System.out.println("Vel:      " + this.velocity.toString());
        System.out.println("Ang:      " + this.angle.toString());
        System.out.println("Ang(vec): " + this.angle.toVector(1).toString());
        System.out.println("Health:   " + this.health);
    }
}
