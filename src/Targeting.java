public class Targeting{
    Vector targetPosition;

    public Targeting(Vector targetPosition){
        this.targetPosition = targetPosition;
    }

    public boolean shouldLaunch(Warhead warhead){
        if (warhead.getPosition().get(2) > 7000){
            return true;
        }

        return false;
    }

    public boolean shouldLaunch(Launcher launcher){
        if (launcher.getHealth() < 10 && launcher.getRemainingMissiles() > 0){
            return true;
        }
        
        return true;
    }

    public double getDutyCycle(Motor motor, Vector position, Vector velocity){
        return 1.0;
    }
}