package org.primal;

import org.primal.map.Map;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Main {

    public int windowWidth = 480 * 2;
    public int windowHeight = 480 * 2;
    private Map map;
    private Simulation simulation;

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

        Map map = new Map(2);
        Simulation simulation = new Simulation(map);
        simulation.start();

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                GUI gui = new GUI(map);
                gui.setVisible(true);
            }
        });
    }
}
