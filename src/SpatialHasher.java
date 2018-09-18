import java.util.ArrayList;
import java.util.HashMap;

public class SpatialHasher{
    HashMap<int[],Entity> buckets;
    double[] bucketSize;

    public SpatialHasher(double[] bucketSize){
        this.buckets = new HashMap<int[],Entity>();
        this.bucketSize = bucketSize;
    }

    public ArrayList<int[]> getKeys(Entity entity){
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
            maxBucket[i] = (int) Math.floor(minPosition.get(i) / this.bucketSize[i]);
        }

        boolean complete = false;
        int[] counterBucket = new int[position.length()];
        ArrayList<int[]> keys = new ArrayList<int[]>();
        while (!complete){
            // Incrementing from 0 size
            counterBucket[0] += 1;
            for (int i = 0; i < position.length(); i++){
                if (counterBucket[i] == maxBucket[i]){
                    counterBucket[i] = minBucket[i];
                    if (i != position.length() - 1){
                        counterBucket[i + 1] += 1;
                    } else {
                        complete = true;
                    }
                }
            }
        }

        return keys;
    }

    public void add(Entity entity){
        for (int[] key : this.getKeys(entity)){
           this.buckets.put(key, entity);
        }
    }
}