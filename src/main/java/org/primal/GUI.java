package org.primal;

import org.primal.entity.LivingEntity;
import org.primal.map.Chunk;
import org.primal.map.Map;
import org.primal.tile.Pixel;
import org.primal.tile.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D.Float;

class Surface extends JPanel implements MouseListener, KeyListener {

    private Map map;
    private int mapWidth;
    private float convertionRate;
    private boolean commandSent = false;
    private boolean inputMode = false;
    public enum Commands{ NOTHING, SPAWNLIONS, PRINTALL, HEJ, KILL, KILLALL, KILLSOME, HEAL, RESPAWN, MASSHEAL, INPUT, LISTCOMMANDS, FREEZECHUNK, SPAWNANIMAL, SPAWNGIRAFFE, SPAWNZEBRA, SPAWNHYENA, SPAWNTREE, SPAWNENVIRONMENT, UNFREEZECHUNK }
    private Commands command;

    /**
     * Surface initiates the surface of the graphics
     *
     * @param map the map which will be drawn
     */
    public Surface(Map map) {
        super();

        mapWidth = map.width * 480;
        convertionRate = ((float) map.getSize()) / ((float) mapWidth);
        this.addKeyListener(this);
        this.addMouseListener(this);
        System.out.println(this.isFocusable());
        this.map = map;
    }

    /**
     * doDrawing generates the graphical representation of the simulation
     *
     * @param g the graphics being drawn upon
     */
    private void doDrawing(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (Chunk[] chunks : map.getChunks()) {
            for (Chunk chunk : chunks) {
                for (int x = 0; x < chunk.getSize(); x++) {
                    for (int y = 0; y < chunk.getSize(); y++) {
                        Tile tile = chunk.getTile(x, y);
                        tile.update();
                        for (Pixel pixel : tile.getPixels()) {
                            g2d.setPaint(pixel.getColor());
                            g2d.fill(pixel.getRectangle());
                            g2d.draw(pixel.getRectangle());
                        }
                    }
                }
            }
        }
        for (Chunk[] chunks : map.getChunks()) {
            for (Chunk chunk : chunks) {
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
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    /**
     * translate translates the x and y coords of the graphical component to it's backend representation
     *
     * @param x the x coordinate generated from the map
     * @param y the y coordinate generated from the map
     * @return reterns the coordinate for the backend position choosen on the frontend graphics
     */
    private Float translate(int x, int y) {
        float fX = x * convertionRate;
        float fY = y * convertionRate;
        return new Float(fX, fY);
    }

    /** freeze freeze the tile at the position pos
     *
     * @param pos the backend position that is somewhere within the tile we want to freeze
     */
    private void freeze(Float pos){
        int xInt = (int) pos.getX();
        int yInt = (int) pos.getY();
        int cSize = map.getChunkSize();


        Chunk c = map.getChunk(xInt/cSize, yInt/cSize);
        c.freeze();
        
    }
    
    /** unfreeze unfreeze the tile at the position pos
     *
     * @param pos the backend position that is somewhere within the tile we want to unfreeze
     */
    private void unfreeze(Float pos){
        int xInt = (int) pos.getX();
        int yInt = (int) pos.getY();
        int cSize = map.getChunkSize();


        Chunk c = map.getChunk(xInt/cSize, yInt/cSize);
        c.unfreeze();
    }
    /** spawnEnvironment generates plants and trees
     * useful incase of unexpected nuke
     */
    private void spawnEnvironment() {
        map.addPlants();
    }


    /**
     * listCommands is a function used to send out the availible commands to the terminal
     */
    private void listCommands() {

        //TODO add alot of print statements
        //TODO if possible add some kind of command listening in window
        System.out.println("The first of many print statements");
    }

    /**
     * printAll prints out inforamtion about all the living entites
     */
    private void printAll() {
        map.printAll();
    }

    /**
     * healAnimals heals the animals in the the tile with the position pos
     *
     * @param pos is the position of the tile to have it's inhabitats healed
     */
    private void healAnimals(Float pos) {
        System.out.println("Thoughts and prayers sent!");
        Tile t = map.getTile(((float) pos.getX()), ((float) pos.getY()));
        t.antiSlaughter();
    }

    /**
     * massHeal a function for healing all the animals on the map
     */
    private void massHeal() {
        System.out.println("Useless func");
        map.antiNuke();
    }

    /**
     * killSomeAnimals a function for killing a random amount of animals on the tile with position pos, the random amount is between 0 and the amount of animals existing on the tile
     */
    private void killSomeAnimals(Float pos) {
        System.out.println("killSomeAnimals");
    }

    /**
     * killAnimals kills all the animals on the tile with the position pos
     */
    private void killAnimals(Float pos) {
        Tile t = map.getTile(((float) pos.getX()), ((float) pos.getY()));
        t.slaughter();
    }

    /**
     * NUKEDECIMATESLAUGHTER removes all existing beings on the map from existance
     */
    private void NUKEDECIMATESLAUGHTER() {
        System.out.println("Freedom sent");
        map.nuke();
    }

    /**
     * spawn respawns a new set of beings, genarly used in conjunction with the inevitble destruction of all things living
     */
    private void spawn() {
        map.addAnimals();
        System.out.println("Due to reasons this function is not implemented");
    }

    private void spawnLions(Float pos) {
        System.out.println("Lions has arrived");
        Tile t = map.getTile(((float) pos.getX()), ((float) pos.getY()));
        map.spawnLion(t);
    }

    private void spawnGiraffe(Float pos) {
        System.out.println("Giraffe has arrived");
        Tile t = map.getTile(((float) pos.getX()), ((float) pos.getY()));
        map.spawnGiraffe(t);
    }

    /**
     * input opens a window that that runs diffrent commands depending on input, type l/list commands/list to see all commands
     */
    private void input() {
        boolean exit = false;

        inputMode = true;
        while (!exit) {
            String input = JOptionPane.showInputDialog(this, "Enter Command", "Input Box", JOptionPane.PLAIN_MESSAGE);
            System.out.println(input);

            if (input.equals("q") || input.equals("quit") || input.equals("exit")) {
                exit = true;
                command = Commands.NOTHING;
            } else if (input.equals("n") || input.equals("nuke") || input.equals("kill all")) {
                command = Commands.KILLALL;
            } else if (input.equals("zebra") || input.equals("spawn zebra")) {
                command = Commands.SPAWNZEBRA;
                commandSent = true;
                return;

            }

            else if(input.equals("freeze") || input.equals("freeze chunk")){
                command = Commands.FREEZECHUNK;
                commandSent = true;
                return;
            }

            
            else if(input.equals("unfreeze") || input.equals("unfreeze chunk")){
                command = Commands.UNFREEZECHUNK;
                commandSent = true;
                return;
            }
            
            else if(input.equals("giraffe") || input.equals("spawn giraffe")){
                command = Commands.SPAWNGIRAFFE;
                commandSent = true;
                return;
            } else if (input.equals("hyena") || input.equals("spawn hyena")) {
                command = Commands.SPAWNHYENA;
                commandSent = true;
                return;
            } else if (input.equals("lion") || input.equals("spawn lion")) {
                command = Commands.SPAWNLIONS;
                commandSent = true;
                return;
            } else if (input.equals("tree") || input.equals("spawn tree")) {
                command = Commands.SPAWNTREE;
                commandSent = true;
                return;
            } else if (input.equals("k") || input.equals("kill")) {
                commandSent = true;
                command = Commands.KILL;
                return;
            } else if (input.equals("spawn environment") || input.equals("e")) {
                command = Commands.SPAWNENVIRONMENT;
            } else if (input.equals("spawn animals") || input.equals("animals")) {
                command = Commands.RESPAWN;
            } else if (input.equals("p") || input.equals("print") || input.equals("print all")) {
                command = Commands.PRINTALL;
            } else if (input.equals("freeze") || input.equals("freeze chunk")) {
                command = Commands.FREEZECHUNK;
            } else if (input.equals("l") || input.equals("list") || input.equals("list all") || input.equals("help")) {
                command = Commands.LISTCOMMANDS;
            } else {
                System.out.println("invalid command");
                command = Commands.NOTHING;
            }
            execCommands();

        }

        inputMode = false;
    }

    /**
     * execCommands runs the currently active command if it is a zero arguments command
     */
    private void execCommands() {
        switch (command) {
            case INPUT:
                input();
                break;
            case SPAWNENVIRONMENT:
                spawnEnvironment();
                break;
            case LISTCOMMANDS:
                listCommands();
                break;
            case PRINTALL:
                printAll();
                break;
            case HEJ:
                System.out.println("Hej pa dig");
                break;
            case KILLALL:
                NUKEDECIMATESLAUGHTER();
                break;
            case RESPAWN:
                spawn();
                break;
            case MASSHEAL:
                massHeal();
                break;
        }
    }

    /**
     * execCommands runs the currently active command if it is a 1 argument command
     *
     * @param pos the argument to be sent onward to the function coresponding to the command
     */
    private void execCommands(Float pos) {
        switch (command) {
            case SPAWNLIONS:
                spawnLions(pos);
                break;
            case SPAWNGIRAFFE:
                spawnGiraffe(pos);
                break;
            case FREEZECHUNK:
                freeze(pos);
                break;
            case UNFREEZECHUNK:
                unfreeze(pos);
                break;
            case KILL:
                killAnimals(pos);
                break;
            case KILLSOME:
                killSomeAnimals(pos);
                break;
            case HEAL:
                healAnimals(pos);
                break;

        }
    }

    /**
     * mouseClicked is a function that is run when a click on the screen is received
     * this function will either print out information about the living entities on the clicked tile or perform an action dependant on the command on it
     *
     * @param click information generated from the mouse click
     */
    public void mouseClicked(MouseEvent click) {
        this.requestFocusInWindow();
        int x = click.getX();
        int y = click.getY();
        Float coords = translate(x, y);

        if (commandSent) {
            System.out.println("hej");
            execCommands(coords);
            commandSent = false;
        } else {
            Tile t = map.getTile(((float) coords.getX()), ((float) coords.getY()));
            System.out.println(t);
        }

        if (inputMode) {
            input();
        }
    }

    /**
     * keyPressed sets the active command depending on which key is pressed, if the corresponding command is a zero argument command the active command is executed
     * to see list of commands type click l or use the command box
     * the key used to open the command box is 'i'
     *
     * @param e inforamtion about the key pressed
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A) {
            command = Commands.SPAWNLIONS;
            commandSent = true;
        } else if (key == KeyEvent.VK_P) {
            command = Commands.PRINTALL;
        } else if (key == KeyEvent.VK_E) {
            command = Commands.SPAWNENVIRONMENT;
        } else if (key == KeyEvent.VK_H) {
            command = Commands.HEJ;
        } else if (key == KeyEvent.VK_N) {
            command = Commands.KILLALL;
        } else if (key == KeyEvent.VK_L) {
            command = Commands.LISTCOMMANDS;
        } else if (key == KeyEvent.VK_I) {
            command = Commands.INPUT;
        } else if (key == KeyEvent.VK_R) {
            command = Commands.RESPAWN;
        } else if (key == KeyEvent.VK_M) {
            command = Commands.MASSHEAL;
        } else if (key == KeyEvent.VK_S) {
            commandSent = true;
            command = Commands.KILLSOME;
        } else if (key == KeyEvent.VK_K) {
            commandSent = true;
            command = Commands.KILL;
        } else if (key == KeyEvent.VK_D) {
            commandSent = true;
            command = Commands.HEAL;
        } else {
            System.out.println("Invalid command");
            command = Commands.NOTHING;
        }
        execCommands();
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

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

}

public class GUI extends JFrame {

    public GUI(Map map) {
        initUI(map);
    }

    /**
     * initUI initiates the graphical interface
     *
     * @param map the map to be drawn/graphically shown
     */
    private void initUI(Map map) {
        add(new Surface(map));
        setTitle("Primal");
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
