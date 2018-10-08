package viewer;

import simulation.Vector;

public class Camera{
    private Vector position = new Vector();
    private double zoom = 1;

    /** Converts a target Vector into a screen position of int[] (x,y) with the top left as 0,0
     *  mode represents the disabled axis i.e. if "z" then as if being viewed from above
     */
    public int[] convertCoordinates(Vector target, String mode){
        Vector screenVector = target.minus(this.position).mult(1 / this.zoom);
        double[] screenPosDouble = screenVector.toArray();

        int[] screenPos = new int[2];
        switch (mode) {
            case "x":
                
                break;
        
            default:
                break;
        }
    }
}