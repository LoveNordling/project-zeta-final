package org.primal;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

import org.primal.entity.LivingEntity;
import org.primal.map.*;
import org.primal.tile.Tile;

class Surface extends JPanel {
    private Map map;
    public Surface(Map map){
        super();

        this.map = map;
    }
    private void doDrawing(Graphics g) {
        super.paintComponent(g);
        System.out.println("Doing a doDrawing");
        Graphics2D g2d = (Graphics2D) g;

        for (Chunk[] chunks : map.getChunks()) {
            for(Chunk chunk : chunks){
                for (int x = 0; x < chunk.getSize(); x++) {
                    for (int y = 0; y < chunk.getSize(); y++) {
                        Tile tile = chunk.getTile(x, y);
                        g2d.setPaint(new Color(0, 100, 50));
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