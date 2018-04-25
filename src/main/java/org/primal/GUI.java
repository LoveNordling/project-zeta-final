package org.primal;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

import org.primal.entity.LivingEntity;
import org.primal.map.*;
import org.primal.tile.Tile;

class Surface extends JPanel {
    private Map map;
    public Surface(){
        super();
        this.map = map;
    }
    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        this.map = new Map(1, g2d);


        Simulation simulation = new Simulation(this.map);
        simulation.start();

        g2d.drawString("Java 2D", 50, 50);


        for(int i = 0; i < 100; i++) {
            super.paintComponent(g);
            repaint();

            for (Chunk chunk : map.getChunks()) {
                for (int x = 0; x < chunk.getSize(); x++) {
                    for (int y = 0; y < chunk.getSize(); y++) {
                        Tile tile = chunk.getTile(x, y);

                        for (LivingEntity entity : tile.getLivingEntities()) {

                            g2d.setPaint(entity.getColor());
                            g2d.fill(entity.getShape());
                        }
                    }
                }
            }


        }


    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }
}

public class GUI extends JFrame {

    public GUI() {

        initUI();
    }

    private void initUI() {


        add(new Surface());

        setTitle("Simple Java 2D example");
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}