public class Targeting{
    private Vector targetPosition;

    public Targeting(Vector targetPosition){
        this.targetPosition = targetPosition;
    }

    public boolean shouldDetonate(Warhead warhead){
        if (warhead.getPosition().get(2) < 200){
            return true;
        }
        return false;
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

    public Angle3D getRotationRate(Entity entity){
        return new Angle3D(new double[]{0.0, 0.0, 0.0});
    }

    public double getDutyCycle(Motor motor){
        return 1.0;
    }
}