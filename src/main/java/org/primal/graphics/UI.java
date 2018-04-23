package org.primal.graphics;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.primal.entity.LivingEntity;
import org.primal.map.Chunk;
import org.primal.map.Map;

public class UI extends Application {
    private Map map;

    public static void main(String[] args) {
        Map map = new Map(1);
        new UI(map, args);
        launch(args);

        for (Chunk c : map.getChunks()) {
            Thread t = new Thread(c, "thread " + c.getId());
            t.start();
        }
    }

    public UI(Map map, String[] args) {
        this.map = map;
    }

    public void setMap(Map map) {
        // TODO: only update map parts that are changed.
        this.map = map;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 600, 600);
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
