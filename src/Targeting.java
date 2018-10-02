public class Targeting{
    private Vector targetPosition;

    public Targeting(Vector targetPosition){
        this.targetPosition = targetPosition;
    }

    public boolean shouldDetonate(Warhead warhead){
        if (warhead.getPosition().get(2) < 200){
            return true;
        } else {
            return false;
        }
    }

    public boolean shouldLaunch(Warhead warhead){
        if (warhead.getPosition().get(2) > 4000){
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