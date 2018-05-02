package org.primal;

import org.primal.entity.LivingEntity;
import org.primal.map.Chunk;
import org.primal.map.Map;
import org.primal.tile.Tile;

import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D.Float;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

class Surface extends JPanel implements MouseListener {
    private Map map;
    private int mapWidth = 960;
    private float convertionRate;
    public Surface(Map map) {
        super();

        convertionRate = ((float)map.getSize())/((float)mapWidth);
        this.addMouseListener(this);
        this.map = map;
    }

    private void doDrawing(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (Chunk[] chunks : map.getChunks()) {
            for (Chunk chunk : chunks) {
                for (int x = 0; x < chunk.getSize(); x++) {
                    for (int y = 0; y < chunk.getSize(); y++) {
                        Tile tile = chunk.getTile(x, y);
                        g2d.setPaint(new Color(181, 202, 51));
                        g2d.fill(tile.getShape());
                        g2d.setPaint(new Color(0, 0, 0));
                        g2d.draw(tile.getShape());
                        for (LivingEntity entity : tile.getLivingEntities()) {
                            g2d.setPaint(entity.getColor());
                            g2d.fill(entity.getShape());
                        }
                    }
                }
            }
        }
        g2d.drawString("Java 2D", 50, 50);
        repaint();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
    private Float translate(int x, int y){
        float fX = x*convertionRate;
        float fY = y*convertionRate;
        return new Float(fX, fY);
    }
    public void mouseClicked(MouseEvent click) {
        int x = click.getX();
        int y = click.getY();
        Float coords = translate(x, y);
        
        Tile t = map.getTile(((float) coords.getX()), ((float) coords.getY()));
        System.out.println(t);
    }

    // Useless methods (needed to be implemented)
    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

}

public class GUI extends JFrame {

    public GUI(Map map) {
        initUI(map);
    }

    private void initUI(Map map) {
        add(new Surface(map));

        setTitle("Simple Java 2D example");
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
