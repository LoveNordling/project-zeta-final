package graphics;

import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;

public class GUI extends Application {

    public void start(Stage stage) throws InterruptedException {
        Rectangle rect = new Rectangle(200, 180, 30, 30);
        rect.setFill(Color.BLUE);

        Group root = new Group();
        Scene scene = new Scene(root, 600, 600);

        ObservableList list = root.getChildren();

        list.add(rect);
        stage.setScene(scene);
        scene.setFill(Color.rgb(255,255,102));

        Text versionText = new Text();
        versionText.setText("Primal: prototype build v. 1.0");
        versionText.setX(150);
        versionText.setY(150);

        list.add(versionText);



        Circle circle = new Circle(100, 470, 5, Color.GREEN);
        Circle circle2 = new Circle(275, 300, 5, Color.GREEN);
        Circle circle3 = new Circle(50, 173, 5, Color.GREEN);
        Circle circle4 = new Circle(300, 350, 5, Color.GREEN);
        Circle circle5 = new Circle(500, 40, 5, Color.GREEN);

        list.add(circle);
        list.add(circle2);
        list.add(circle3);
        list.add(circle4);
        list.add(circle5);

        Rectangle rect2 = new Rectangle(300, 290, 10, 10);
        rect2.setFill(Color.DARKRED);

        Rectangle rect3 = new Rectangle(400, 170, 10, 10);
        rect3.setFill(Color.PURPLE);


        list.add(rect2);
        list.add(rect3);

        Random randomMove = new Random();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                rect2.setX(rect2.getX() + randomMove.nextInt(10 + 1 + 10) - 10);
                rect2.setY(rect2.getY() + randomMove.nextInt(10 + 1 + 10) - 10);

                rect3.setX(rect3.getX() + randomMove.nextInt(10 + 1 + 10) - 10);
                rect3.setY(rect3.getY() + randomMove.nextInt(10 + 1 + 10) - 10);
            }
        }, 0, 500);

        stage.show();
    }

    public static void main(String args[]) {
       launch(args);
    }
}