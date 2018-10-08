package viewer;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JFrame;

import simulation.*;

public class Window extends JFrame implements MouseListener {
    private ArrayList<Viewer> viewers;
    private ArrayList<World> worlds;
    private int currentWorld = -1;

    public static void main(String[] args) {
        Window window = new Window();
    }

    public Window() {
        this.viewers = new ArrayList<Viewer>();
        this.worlds = new ArrayList<World>();

        World world = new World();
        world.setupBasic();
        world.step(2);
        world.debugOutput(1);
        this.worlds.add(world);
        this.currentWorld = 0;

        Viewer viewer = new Viewer(512, 512);
        this.viewers.add(viewer);
        add(viewer);
        pack();
        setVisible(true);



        addMouseListener(this);
    }

    public void updateViewers() {
        this.worlds.get(this.currentWorld).step(0.5);
        Entity[] entities = this.worlds.get(this.currentWorld).getState();
        for (Viewer viewer : this.viewers){
            for (Entity entity : entities) {
                viewer.addEntity(entity);
            }
        }
    }


    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) { repaint(); }
    public void mouseExited(MouseEvent e) { updateViewers(); }
    public void mouseClicked(MouseEvent e) { repaint(); }
    
}