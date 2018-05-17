package org.primal;

import org.primal.map.Map;
import org.primal.PerformanceTester;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Main {

    public int windowWidth = 600;
    public int windowHeight = 600;
    private Map map;
    public static Simulation simulation;

    public static void main(String[] args) {
        File dir = new File("output/");
        dir.mkdir();

        // Set custom error log
        try {
            @SuppressWarnings("resource")
            PrintStream stream = new PrintStream("output/error.log");
            System.setErr(stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Map map = new Map(16);

        GUI gui = new GUI(map);

        Runnable action = () -> {
            gui.repaint();
        };
        simulation = new Simulation(Runtime.getRuntime().availableProcessors(), action);

        simulation.schedule(map.getChunksAsList());


        gui.setVisible(true);

        simulation.start();

        // PerformanceTester PFTester = new PerformanceTester();

        // PFTester.makeNewMap(16);

        // // PFTester.burnTestChunks();

    }
}
