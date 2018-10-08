package viewer;

import java.awt.*;

import javax.swing.JPanel;

import simulation.Entity;

public class Viewer extends JPanel {
    private int xsize = 512;
    private int ysize = 512;

    private int numberOfEntities = 0;
    private Entity[] entities = new Entity[10];

    private Camera camera = new Camera();

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

    public void clearEntities() {
        this.entities = new Entity[10];
    }

    public void addEntity(Entity entity) {
        if (this.numberOfEntities == this.entities.length) {
            Entity[] temp = new Entity[entities.length * 2];
            for (int i = 0; i < this.entities.length; i++) {
                temp[i] = this.entities[i];
            }
            this.entities = temp;
        }
        
        this.entities[this.numberOfEntities] = entity;
        this.numberOfEntities += 1;
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        
        for (int i = 0; i < this.numberOfEntities; i++) {
            Entity entity = this.entities[i];

            g.setColor(Color.GREEN);
            int[] screenPos = this.camera.convertCoordinates(entity.getPosition(), this.ysize, "y");
            g.drawRect(screenPos[0], screenPos[1], 1, 1);
        }
    }
}