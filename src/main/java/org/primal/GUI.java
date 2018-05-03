package org.primal;

import org.primal.entity.LivingEntity;
import org.primal.map.Chunk;
import org.primal.map.Map;
import org.primal.tile.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D.Float;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class Surface extends JPanel implements MouseListener, KeyListener {
    private Map map;
    private int mapWidth;
    private float convertionRate;
    private boolean commandSent = false;
    public enum Commands{ NOTHING, SPAWNLIONS }
    private Commands command;
    public Surface(Map map) {
        super();

        mapWidth = map.width*360;
        convertionRate = ((float)map.getSize())/((float)mapWidth);
        this.addKeyListener(this);
        this.addMouseListener(this);
        System.out.println(this.isFocusable());
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
                        g2d.setPaint(new Color(0, 100, 50));
                        g2d.fill(tile.getShape());
                        g2d.setPaint(new Color(0, 0, 0));
                        g2d.draw(tile.getShape());
                        for (LivingEntity entity : tile.getLivingEntities().values()) {
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
    private void execCommands(){
        switch(command){
            case SPAWNLIONS:
                System.out.println("lions");
                break;
        }
    }
    public void mouseClicked(MouseEvent click) {
        this.requestFocusInWindow();
        int x = click.getX();
        int y = click.getY();
        Float coords = translate(x, y);
        if(commandSent == true){
            execCommands();
            commandSent = false;
        }
        else{
            Tile t = map.getTile(((float) coords.getX()), ((float) coords.getY()));
            System.out.println(t);
        }
    }
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        System.out.println("hej");
        if(key == KeyEvent.VK_A){
            commandSent = true;
            command = Commands.SPAWNLIONS;
            System.out.println("aaa");
        }
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
    public void keyReleased(KeyEvent e){
    }
    public void keyTyped(KeyEvent e){
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
