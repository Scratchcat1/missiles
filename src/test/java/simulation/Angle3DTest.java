package simulation;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

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

    protected void anglesUnmodified() {
        assertEquals("Vertical angle should not have been modified",
            Arrays.equals(vertical.toArray(), verticalArray), true);
        assertEquals("HorizontalX angle should not have been modified",
            Arrays.equals(horizontalX.toArray(), horizontalArrayX), true);
        assertEquals("HorizontalY angle should not have been modified",
            Arrays.equals(horizontalY.toArray(), horizontalArrayY), true);
        assertEquals("Equal angle should not have been modified",
            Arrays.equals(equalAngle.toArray(), equalAngleArray), true);
    }

    @Test
    public void testClone() {
        Angle3D clone = vertical.clone();
        
        assertEquals("Cloned angle and original operate on different arrays",
            vertical.toArray() != clone.toArray(), true);
        
        assertEquals("Cloned angle should be identical to original",
            vertical.equals(clone), true);
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
        assertEquals("An angle is equal to an identical angle",
            vertical.equals(vertical.clone()), true);
        
        assertEquals("An angle is not equal to a different angle",
            vertical.equals(horizontalX), false);
    }
}