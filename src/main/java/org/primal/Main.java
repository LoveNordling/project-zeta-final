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

public class Main extends Application {
    public int windowWidth = 600;
    public int windowHeight = 600;
    private Map map;
    private Simulation simulation;

    public static void main(String[] args) {
        launch(args);
    }



    @Override
    public void start(Stage stage) throws Exception {




        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI gui = new GUI();
                gui.setVisible(true);
            }
        });






        //stage.show();
    }
}
