import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SpatialHasher{
    HashMap<String,ArrayList<Entity>> buckets;
    double[] bucketSize;

    public SpatialHasher(double[] bucketSize){
        this.buckets = new HashMap<String,ArrayList<Entity>>();
        this.bucketSize = bucketSize;
    }

    public ArrayList<String> getKeys(Entity entity){
        Vector position = entity.getPosition();
        Vector minPosition = position.clone();
        Vector maxPosition = position.clone();

        for (int i = 0; i < position.length(); i++){
            minPosition.set(i, minPosition.get(i) - entity.getCollisionRadius());
            maxPosition.set(i, maxPosition.get(i) + entity.getCollisionRadius());
        }

        int[] minBucket = new int[position.length()];
        int[] maxBucket = new int[position.length()];
        for (int i = 0; i < position.length(); i++){
            minBucket[i] = (int) Math.floor(minPosition.get(i) / this.bucketSize[i]);
            maxBucket[i] = (int) Math.floor(maxPosition.get(i) / this.bucketSize[i]);
        }

        boolean complete = false;
        int[] counterBucket = minBucket.clone();
        ArrayList<String> keys = new ArrayList<String>();

        if (entity.getCollisionRadius() == 0){
            keys.add(Arrays.toString(minBucket));
        } else {
            while (!complete){
                keys.add(Arrays.toString(counterBucket));
                counterBucket[0] += 1;
                for (int i = 0; i < counterBucket.length; i++){
                    if (counterBucket[i] == maxBucket[i] + 1){
                        counterBucket[i] = minBucket[i];
                        if (i != position.length() - 1){
                            counterBucket[i + 1] += 1;
                        } else {
                            complete = true;
                        }
                    }
                }
            }
        }
        
        return keys;
    }

    public void add(Entity entity){
        for (String key : this.getKeys(entity)){
            if (!this.buckets.containsKey(key)){
                this.buckets.put(key, new ArrayList<Entity>());
            }
            this.buckets.get(key).add(entity);
        }
    }

    public ArrayList<Entity> get(Entity entity){
        ArrayList<Entity> nearbyEntities = new ArrayList<Entity>();
        for (String key : this.getKeys(entity)){
            if (this.buckets.containsKey(key)){
                nearbyEntities.addAll(this.buckets.get(key));
            }
        }
        return nearbyEntities;
    }


}