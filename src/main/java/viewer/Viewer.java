package viewer;

import java.awt.*;

import javax.swing.JPanel;

import simulation.*;

public class Viewer extends JPanel {
    private int xsize = 512;
    private int ysize = 512;

    private World world;

    private Camera camera = new Camera();
    private String cameraMode = "y";

    public Viewer(int xsize, int ysize) {
        this.xsize = xsize;
        this.ysize = ysize;
        
        setBackground(Color.BLACK);
        setOpaque(true);
    }

    public Dimension getMinimumSize() {
        return new Dimension(xsize,ysize);
    }
    public Dimension getPreferredSize() {
        return new Dimension(xsize,ysize);
    }

    public Camera getCamera() {
        return this.camera;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void setMode(String mode) {
        this.cameraMode = mode;
    }

    /** Draws all entities for the world the viewer currently is set to. */
    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        
        g.setColor(Color.WHITE);
        for (City city : this.world.getCities()) {
            int[] screenPos = this.camera.convertCoordinates(city.getPosition(), this.ysize, this.cameraMode);
            g.drawRect(screenPos[0], screenPos[1], 1, 1);
        }
        
        g.setColor(Color.GREEN);
        for (LaunchPlatform launchPlatform : this.world.getLaunchPlatforms()) {
            int[] screenPos = this.camera.convertCoordinates(launchPlatform.getPosition(), this.ysize, this.cameraMode);
            g.drawRect(screenPos[0], screenPos[1], 1, 1);
        }

        g.setColor(Color.BLUE);
        for (Missile missile : this.world.getMissiles()) {
            int[] screenPos = this.camera.convertCoordinates(missile.getPosition(), this.ysize, this.cameraMode);
            g.drawRect(screenPos[0], screenPos[1], 1, 1);
        }

        g.setColor(Color.ORANGE);
        for (Warhead warhead : this.world.getWarheads()) {
            int[] screenPos = this.camera.convertCoordinates(warhead.getPosition(), this.ysize, this.cameraMode);
            g.drawRect(screenPos[0], screenPos[1], 1, 1);
        }

        g.setColor(Color.RED);
        for (Explosion explosion : this.world.getExplosions()) {
            int[] screenPos = this.camera.convertCoordinates(explosion.getPosition(), this.ysize, this.cameraMode);
            g.drawRect(screenPos[0], screenPos[1], 1, 1);
        }
    }
}