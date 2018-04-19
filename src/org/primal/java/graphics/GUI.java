package graphics;

import java.lang.reflect.Array;
import java.util.ArrayList;
import org.primal.java.entity.Animal;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;

public class GUI extends Application {

    public void start(Stage stage) throws InterruptedException {
        Group root = new Group();
        Scene scene = new Scene(root, 600, 600);

        Text versionText = new Text();
        versionText.setText("Primal: prototype build");
        versionText.setX(150);
        versionText.setY(150);

        ObservableList list = root.getChildren();
        list.add(versionText);

        Rectangle rect = new Rectangle(200, 180, 10, 10);
        rect.setFill(Color.BLUE);
        list.add(rect);

        stage.setTitle("Primal");
        stage.setScene(scene);

        int x = 10;
        int y = 10;

        for (int i = 0; i < 10; i++) {
  
            Rectangle rectangle = new Rectangle(200 + x, 180 + y, 10, 10);
            rectangle.setFill(Color.BLUE);
            list.add(rectangle);
            x += 10;
            y += 10;
            stage.show();
        }



    }

    public static void main(String args[]) {
       launch(args);
    }
}