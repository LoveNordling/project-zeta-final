package org.primal;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import org.primal.entity.LivingEntity;
import org.primal.map.Chunk;
import org.primal.map.Map;

import org.primal.GUI;

import java.awt.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;


public class Main extends Application {
    public int windowWidth = 600;
    public int windowHeight = 600;
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

        launch(args);
    }



    @Override
    public void start(Stage stage) throws Exception {

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






        //stage.show();
    }
}
