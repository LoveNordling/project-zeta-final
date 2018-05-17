package org.primal;

import org.primal.map.Map;
import org.primal.map.Chunk;
import org.primal.PerformanceTester;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import java.util.ArrayList;

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

        ArrayList<Chunk> chunks = new ArrayList<Chunk>(map.getChunksAsList());

        for (Chunk chunk : chunks) {
            Runnable renderChunk = () -> {chunk.renderImage();};
            simulation.submit(renderChunk);
        }

        simulation.schedule(chunks);


        gui.setVisible(true);

        simulation.start();

        // PerformanceTester PFTester = new PerformanceTester();

        // PFTester.makeNewMap(16);

        // // PFTester.burnTestChunks();

    }
}
