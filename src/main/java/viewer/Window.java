package viewer;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import simulation.*;

public class Window extends JFrame implements MouseListener, KeyListener{
    private ArrayList<Viewer> viewers;
    private ArrayList<World> worlds;
    private int currentViewer = 0;
    private int currentWorld = -1;

    public static void main(String[] args) {
        Window window = new Window();
        window.run();
    }

    public Window() {
        this.viewers = new ArrayList<Viewer>();
        this.worlds = Armory.createBasicWorlds(1);

        Viewer viewer = new Viewer(512, 512);
        this.viewers.add(viewer);
        this.changeWorld(0);

        add(viewer);
        pack();
        setVisible(true);

        addMouseListener(this);
        addKeyListener(this);
    }

    /** run the simulation continously */
    public void run() {
        while (true) {
            this.stepWorld();
            repaint();
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (Exception e) {}
        }
    }

    public void changeWorld(int worldPosition) {
        this.currentWorld = worldPosition;
        for (Viewer viewer : this.viewers) {
            viewer.setWorld(this.worlds.get(this.currentWorld));
        }
    }

    public void stepWorld(){
        long startTime = System.nanoTime();
        this.worlds.get(currentWorld).step(0.5);
        long endTime = System.nanoTime();
        System.out.println("Step " + String.format("%5d", -1) + " took " + (endTime - startTime) + " nanoseconds");
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_D:
                this.viewers.get(this.currentViewer).getCamera().move(0, 10);
                break;
            case KeyEvent.VK_A:
                this.viewers.get(this.currentViewer).getCamera().move(0, -10);
                break;
            case KeyEvent.VK_W:
                this.viewers.get(this.currentViewer).getCamera().move(1, 10);       
                break;
            case KeyEvent.VK_S:
                this.viewers.get(this.currentViewer).getCamera().move(1, -10);       
                break;
            case KeyEvent.VK_E:
                this.viewers.get(this.currentViewer).getCamera().move(2, 10);       
                break;
            case KeyEvent.VK_Q:
                this.viewers.get(this.currentViewer).getCamera().move(2, -10);       
                break;

            case KeyEvent.VK_1:
                this.viewers.get(this.currentViewer).getCamera().multZoom(0.7);
                break;
            case KeyEvent.VK_2:
                this.viewers.get(this.currentViewer).getCamera().multZoom(1.3);;       
                break;
            default:
                break;
        }
    }
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    
}