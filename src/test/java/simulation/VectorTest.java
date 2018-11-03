package simulation;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.Arrays;

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

    /** Ensures that the vectors have not been altered.
     *  Call when using a function which should not alter 
     *  the state of the Vector.
     */
    protected void vectorsUnmodified() {
        assertEquals("Zero Vector should not have been modified by the operation", true,
            Arrays.equals(zeroVector.toArray(), new double[]{0, 0, 0}));
        assertEquals("Non zero Vector should not have been modified by the operation", true,
            Arrays.equals(nonZeroVector.toArray(), nonZeroArray));
        assertEquals("Negative Vector should not have been modified by the operation", true,
            Arrays.equals(negativeVector.toArray(), negativeArray));
    }

    @Test
    public void instantiation() {
        assertEquals("Vector created without arguments should be zeroed", true,
            Arrays.equals(zeroVector.toArray(), new double[]{0, 0, 0}));

        assertEquals("Vector should have same values as the array passed in", true,
            Arrays.equals(nonZeroVector.toArray(), nonZeroArray));
    }

    @Test
    public void testClone() {
        Vector clone = nonZeroVector.clone();
    
        assertEquals("Cloned Vector should work on a different array to the original", true,
            nonZeroVector.toArray() != clone.toArray());

        assertEquals("Cloned Vector should be identical to the original", true,
            nonZeroVector.equals(clone));
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
            zeroVector.set(i, nonZeroArray[i]);
            assertEquals(zeroVector.get(i), nonZeroArray[i], 0);
        }
    }

    @Test
    public void equals() {
        assertEquals("A Vector is equal to an identical vector", true,
            nonZeroVector.equals(nonZeroVector.clone()));

        assertEquals("A Vector is not equal to a non identical vector", false,
            nonZeroVector.equals(zeroVector));
    }

    @Test
    public void add() {
        assertEquals("Vectors added should result in a correct third vector", true,
            Arrays.equals(nonZeroVector.add(negativeVector).toArray(), new double[]{-1, 1, 1}));
        
        vectorsUnmodified();
    }

    @Test
    public void minus() {
        assertEquals("Vectors should subtract", true,
            Arrays.equals(nonZeroVector.minus(negativeVector).toArray(), new double[]{3, 3, 9}));
        vectorsUnmodified();
    }

    @Test
    public void mult() {
        assertEquals("Vector multiplied by scalar", true,
            Arrays.equals(nonZeroVector.mult(3).toArray(), new double[]{3, 6, 15}));

        assertEquals("Vector multiplied by Vector result in dot product",
            nonZeroVector.mult(negativeVector), -24);

        assertEquals("Vector multiplied by zero vector results in 0",
            nonZeroVector.mult(zeroVector), 0);
        
        vectorsUnmodified();
    }

    @Test
    public void negative() {
        assertEquals("Vector negative should be -1 * Vector", true,
            Arrays.equals(nonZeroVector.negative().toArray(), new double[]{-1, -2, -5}));
            
        vectorsUnmodified();
    }

    @Test
    public void distance() {
        assertEquals(nonZeroVector.distance(negativeVector), Math.sqrt(99), 0.01);
    }
}