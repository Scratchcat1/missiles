import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.Arrays;

import simulation.Vector;

public class VectorTest {
    private Vector zeroVector;
    private double[] nonZeroArray;
    private Vector nonZeroVector;
    private double[] negativeArray;
    private Vector negativeVector;

    @Before
    public void setUp() {
        zeroVector = new Vector();
        nonZeroArray = new double[]{1, 2, 5};
        nonZeroVector = new Vector(nonZeroArray);
        negativeArray = new double[]{-2, -1, -4};
        negativeVector = new Vector(negativeArray);
    }

    protected void arraysUnmodified() {
        assertEquals("Zero Vector should not have been modified by the operation",
            Arrays.equals(zeroVector.toArray(), zeroArray), true);
        assertEquals("Non zero Vector should not have been modified by the operation",
            Arrays.equals(nonZeroVector.toArray(), nonZeroArray), true);
        assertEquals("Negative Vector should not have been modified by the operation",
            Arrays.equals(negativeVector.toArray(), negativeArray), true);
    }

    @Test
    public void instantiation() {
        assertEquals("Vector created without arguments should be zeroed",
            Arrays.equals(zeroVector.toArray(), new double[]{0, 0, 0}), true);

        assertEquals("Vector should have same values as the array passed in",
            Arrays.equals(nonZeroVector.toArray(), nonZeroArray), true);
    }

    @Test
    public void testClone() {
        Vector clone = nonZeroVector.clone();
    
        assertEquals("Cloned Vector should work on a different array to the original",
            nonZeroVector.toArray() != clone.toArray(), true);
    }

    @Test
    public void get() {
        for (int i = 0; i < 3; i++) {
            // Ensures that the value returned from the vector is the same in the initial array
            assertEquals(nonZeroVector.get(i), nonZeroArray[i], 0);
        }
    }

    @Test
    public void set() {
        for (int i = 0; i < 3; i++) {
            // Ensures that the value returned from the vector is the same in the initial array
            zeroVector.set(i, nonZeroArray[i]);
            assertEquals(zeroVector.get(i), nonZeroArray[i], 0);
        }
    }

    @Test
    public void equals() {
        assertEquals("A Vector is equal to an identical vector",
            nonZeroVector.equals(nonZeroVector.clone()), true);

        assertEquals("A Vector is not equal to a non identical vector",
            nonZeroVector.equals(zeroVector), false);
    }

    @Test
    public void add() {
        assertEquals("Vectors added should result in a correct third vector",
            Arrays.equals(nonZeroVector.add(negativeVector).toArray(), new double[]{-1, 1, 1}), true);
    }

    public void minus() {
        assertEquals("Vectors should subtract",
            Arrays.equals(nonZeroVector.minus(negativeVector).toArray(), new double[]{3, 3, 9}), true);
    }
}