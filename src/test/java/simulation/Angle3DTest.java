package simulation;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.beans.Transient;
import java.util.Arrays;

public class Angle3DTest {
    private double[] verticalArray;
    private double[] horizontalArrayX;
    private double[] horizontalArrayY;
    private double[] equalAngleArray;
    private Angle3D vertical;
    private Angle3D horizontalX;
    private Angle3D horizontalY;
    private Angle3D equalAngle;

    @Before
    public void setUp() {
        verticalArray = new double[]{Math.PI/2, 0, 0};
        horizontalArrayX = new double[]{0, 0, 0};
        horizontalArrayY = new double[]{0, Math.PI/2, 0};
        equalAngleArray = new double[]{Math.PI/4, Math.PI/4, Math.PI/4};
        
        vertical = new Angle3D(verticalArray);
        horizontalX = new Angle3D(horizontalArrayX);
        horizontalY = new Angle3D(horizontalArrayY);
        equalAngle = new Angle3D(equalAngleArray);
    }

    /** Verify that the angles were not altered. */
    private void anglesUnmodified() {
        assertEquals("Vertical angle should not have been modified", true,
            Arrays.equals(vertical.toArray(), verticalArray));
        assertEquals("HorizontalX angle should not have been modified", true,
            Arrays.equals(horizontalX.toArray(), horizontalArrayX));
        assertEquals("HorizontalY angle should not have been modified", true,
            Arrays.equals(horizontalY.toArray(), horizontalArrayY));
        assertEquals("Equal angle should not have been modified", true,
            Arrays.equals(equalAngle.toArray(), equalAngleArray));
    }

    @Test
    public void testClone() {
        Angle3D clone = vertical.clone();
        
        assertEquals("Cloned angle and original operate on different arrays", true,
            vertical.toArray() != clone.toArray());
        
        assertEquals("Cloned angle should be identical to original", true,
            vertical.equals(clone));
    }

    @Test
    public void get() {
        for (int i = 0; i < 3; i++) {
            // Ensures that the value returned from the angle is the same in the initial array
            assertEquals(vertical.get(i), verticalArray[i], 0);
        }
    }

    @Test
    public void set() {
        for (int i = 0; i < 3; i++) {
            vertical.set(i, horizontalArrayX[i]);
            assertEquals(vertical.get(i), horizontalArrayX[i], 0);
        }
    }

    @Test
    public void equals() {
        assertEquals("An angle is equal to an identical angle", true,
            vertical.equals(vertical.clone()));
        
        assertEquals("An angle is not equal to a different angle", false,
            vertical.equals(horizontalX));
    }

    @Test
    public void rotate() {
        Angle3D rotationAngleA = new Angle3D(new double[]{0, Math.PI, 0});
        Angle3D rotationAngleB = new Angle3D(new double[]{0, Math.PI, Math.PI});
        Angle3D rotationAngleC = new Angle3D(new double[]{-Math.PI/2, Math.PI, -Math.PI/2});

        Angle3D rotated;

        rotated = vertical.rotate(rotationAngleA);
        assertEquals("Vector should rotate", true,
            Arrays.equals(rotated.toArray(), new double[]{Math.PI/2, Math.PI, 0}));

        rotated = horizontalX.rotate(rotationAngleB);
        assertEquals("Vector should rotate", true,
            Arrays.equals(rotated.toArray(), new double[]{0, Math.PI, Math.PI}));

        rotated = vertical.rotate(rotationAngleC);
        assertEquals("Vector should rotate", true,
            Arrays.equals(rotated.toArray(), new double[]{0, Math.PI, -Math.PI/2}));

        anglesUnmodified();
    }

    @Test
    public void angularDifference() {
        assertEquals("Vector should return correct angular difference", true,
            Arrays.equals(vertical.angularDifference(horizontalX).toArray(), new double[]{-Math.PI/2, 0, 0}));

        assertEquals("Vector should return correct angular difference", true,
            Arrays.equals(vertical.angularDifference(equalAngle).toArray(), new double[]{-Math.PI/4, Math.PI/4, Math.PI/4}));
    }

    @Test
    public void fromVector() {
        Vector testVector;

        testVector = new Vector(new double[]{0, 0, 0});
        assertEquals("All zero vector should return all zero angle", true,
            Arrays.equals(new Angle3D(testVector).toArray(), new double[]{0, 0, 0}));
        
        testVector = new Vector(new double[]{0, 0, 123});
        assertEquals("Zero x, y with positive z should return correct array", true,
            Arrays.equals(new Angle3D(testVector).toArray(), new double[]{Math.PI / 2, 0, 0}));

        testVector = new Vector(new double[]{0, 0, -123});
        assertEquals("Zero x, y with negative z should return correct array", true,
            Arrays.equals(new Angle3D(testVector).toArray(), new double[]{-Math.PI / 2, 0, 0}));

        testVector = new Vector(new double[]{0, 111, 0});
        assertEquals("Zero x, z with positive y should return correct array", true,
            Arrays.equals(new Angle3D(testVector).toArray(), new double[]{0, Math.PI / 2, 0}));
            
        testVector = new Vector(new double[]{0, -111, 0});
        assertEquals("Zero x, z with negative y should return correct array", true,
            Arrays.equals(new Angle3D(testVector).toArray(), new double[]{0, -Math.PI / 2, 0}));

        System.out.println(new Angle3D(new Vector(new double[]{1,1,1})).toString());
        testVector = new Vector(new double[]{1, 1, 1});
        assertEquals("Vector should be correctly converted", true,
            Arrays.equals(new Angle3D(testVector).toArray(), new double[]{0.6154797086703873, Math.PI/4, 0}));
    }

    @Test
    public void toVector() {
        // Each axis should be approximately what is expected
        // the test loops through each an verifies they are approximately correct
        Vector expectedVector;
        double acceptableError = Math.pow(10, -5);

        // vertical - only z
        expectedVector = new Vector(new double[]{0.0, 0.0, 100.0});
        for (int i = 0; i < 3; i++) {
            assertEquals(expectedVector.get(i), vertical.toVector(100).get(i), acceptableError);
        }

        // horizontalX - only x
        expectedVector = new Vector(new double[]{200.0, 0.0, 0.0});
        for (int i = 0; i < 3; i++) {
            assertEquals(expectedVector.get(i), horizontalX.toVector(200).get(i), acceptableError);
        }

        // horizontalY - only y
        expectedVector = new Vector(new double[]{0.0, 300.0, 0.0});
        for (int i = 0; i < 3; i++) {
            assertEquals(expectedVector.get(i), horizontalY.toVector(300).get(i), acceptableError);
        }

        // equal - z > x = y
        expectedVector = new Vector(new double[]{199.99999999999994, 199.99999999999994, 282.84271247461896});
        for (int i = 0; i < 3; i++) {
            assertEquals(expectedVector.get(i), equalAngle.toVector(400).get(i), acceptableError);
        }
        
        anglesUnmodified();
    }
}