package viewer;

import java.awt.*;

import javax.swing.JPanel;

public class DrawingCanvas extends JPanel {
    private int xsize = 512;
    private int ysize = 512;

    private int numberOfShapes = 0;
    private Shape[] shapes = new Shape[10];

    public DrawingCanvas(int xsize, int ysize) {
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

    public void addShape(Shape shape) {
        if (this.numberOfShapes == this.shapes.length) {
            Shape[] temp = new Shape[shapes.length * 2];
            for (int i = 0; i < this.shapes.length; i++) {
                temp[i] = this.shapes[i];
            }
            this.shapes = temp;
        }
        
        this.shapes[this.numberOfShapes] = shape;
        this.numberOfShapes += 1;
    }

    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.GREEN);
        super.paintComponent(g);
        
        for (int i = 0; i < this.numberOfShapes; i++){
            g2.draw(this.shapes[i]);
        }
    }
}