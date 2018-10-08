package viewer;

import simulation.Vector;

public class Camera {
    private Vector position = new Vector();
    private double zoom = 1;

    /** Converts a target Vector into a screen position of int[] (x,y) with the top left as 0,0
     *  mode represents the disabled axis i.e. if "z" then as if being viewed from above
     */
    public int[] convertCoordinates(Vector target, int yPixels, String mode) {
        Vector screenVector = target.minus(this.position).mult(this.zoom);
        double[] screenPosDouble = screenVector.toArray();

        int[] screenPos = new int[2];
        switch (mode) {
            default:
                screenPos[0] = (int) screenPosDouble[0];
                screenPos[1] = (int) screenPosDouble[2];
                break;
        }

        screenPos[1] = yPixels - screenPos[1];
        return screenPos;
    }

    public void setZoom(double newZoom) {
        this.zoom = newZoom;
    }

    public void multZoom(double multiplier) {
        this.zoom *= multiplier;
    }

    public void move(int axis, double movementMultiplier) {
        double distance = movementMultiplier / this.zoom;
        this.position.set(axis, this.position.get(axis) + distance);
    }
}