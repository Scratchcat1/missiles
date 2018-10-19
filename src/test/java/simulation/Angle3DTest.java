package simulation;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.Arrays;

public class Angle3DTest {
    private Angle3D vertical;
    private Angle3D horizontalX;
    private Angle3D horizontalY;
    private Angle3D equalAngle;

    @Before
    public void setUp() {
        Angle3D vertical = new Angle3D(new double[]{Math.PI/2, 0, 0});
        Angle3D horizontalX = new Angle3D(new double[]{0, 0, 0});
        Angle3D horizontalY = new Angle3D(new double[]{0, Math.PI/2, 0});
        Angle3D equalAngle = new Angle3D(new double[]{Math.PI/4, Math.PI/4, Math.PI/4});
    }

    @Test
    public void blank() {
        int x = 1;
    }
}