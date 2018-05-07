package org.primal;

import org.primal.entity.LivingEntity;
import org.primal.map.Chunk;
import org.primal.map.Map;
import org.primal.tile.Pixel;
import org.primal.tile.Tile;
import org.primal.tile.WaterTile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D.Float;

class Surface extends JPanel implements MouseListener {

    private Map map;
    private int mapWidth = 960;
    private float convertionRate;

    public Surface(Map map) {
        super();

        convertionRate = ((float) map.getSize()) / ((float) mapWidth);
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
                        for (Pixel pixel : tile.getPixels()) {
                            g2d.setPaint(pixel.getColor());
                            g2d.fill(pixel.getRectangle());
                            g2d.draw(pixel.getRectangle());
                        }
                        for (LivingEntity entity : tile.getLivingEntities()) {
                            g2d.setPaint(entity.getColor());
                            g2d.fill(entity.getShape());
                        }
                    }
                }
            }
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private Float translate(int x, int y) {
        float fX = x * convertionRate;
        float fY = y * convertionRate;
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

        setTitle("Primal");
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
