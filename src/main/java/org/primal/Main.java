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

public class Main extends Application {
    private Map map;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.map = new Map(1);

        for (Chunk c : map.getChunks()) {
            Thread t = new Thread(c, "thread " + c.getId());
            t.start();
        }

        Group root = new Group();
        Scene scene = new Scene(root, 600, 600);
        scene.setFill(Color.rgb(188, 166, 49));
        stage.setScene(scene);
        ObservableList list = root.getChildren();

        for (Chunk chunk : map.getChunks()) {
            for (int x = 0; x < chunk.getSize(); x++) {
                for (int y = 0; y < chunk.getSize(); y++) {
                    for (LivingEntity entity : chunk.getTile(x, y).getLivingEntities()) {
                        list.add(entity.getShape());
                    }
                }
            }
        }

        stage.show();
    }
}
