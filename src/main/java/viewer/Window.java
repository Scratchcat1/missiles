package viewer;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JFrame;

import simulation.*;

public class Window extends JFrame implements MouseListener, KeyListener{
    private ArrayList<Viewer> viewers;
    private ArrayList<World> worlds;
    private int currentViewer = 0;
    private int currentWorld = 0;

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
        addKeyListener(this);
    }

    public void updateViewers() {
        long startTime = System.nanoTime();
        this.worlds.get(this.currentWorld).step(0.5);
        this.worlds.get(this.currentWorld).debugOutput(0);
        Entity[] entities = this.worlds.get(this.currentWorld).getState();
        for (Viewer viewer : this.viewers) {
            viewer.clearEntities();
            for (Entity entity : entities) {
                viewer.addEntity(entity);
            }
        }
        long endTime = System.nanoTime();
        System.out.println("Step " + String.format("%5d", -1) + " took " + (endTime - startTime) + " nanoseconds");
    }


    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) { repaint(); }
    public void mouseExited(MouseEvent e) { updateViewers(); }
    public void mouseClicked(MouseEvent e) { repaint(); }
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                this.viewers.get(this.currentViewer).getCamera().move(0, 10);       
                break;
            case KeyEvent.VK_RIGHT:
                this.viewers.get(this.currentViewer).getCamera().move(0, -10);       
                break;
            case KeyEvent.VK_UP:
                this.viewers.get(this.currentViewer).getCamera().move(2, 10);       
                break;
            case KeyEvent.VK_DOWN:
                this.viewers.get(this.currentViewer).getCamera().move(2, -10);       
                break;

            case KeyEvent.VK_1:
                this.viewers.get(this.currentViewer).getCamera().multZoom(0.5);
                break;
            case KeyEvent.VK_2:
                this.viewers.get(this.currentViewer).getCamera().multZoom(1.5);;       
                break;
            default:
                break;
        }
        
        updateViewers();
        repaint();
    }
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    
}