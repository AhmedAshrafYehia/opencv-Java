package sample.Utils;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class popubUtils {

    public void imagePopupWindowShow(Image image, Image hist) {
        ImageView imageView;
        ImageView histView;
        BorderPane pane;
        Scene scene;
        Stage stage;

        imageView = new ImageView(image);
        histView = new ImageView(hist);

        // Our image will sit in the middle of our sample.popup.
        pane = new BorderPane();
        pane.setLeft(imageView);
        pane.setRight(histView);
        scene = new Scene(pane);

        // Create the actual window and display it.
        stage = new Stage();
        stage.setScene(scene);

        stage.showAndWait();

    }

    public void imagePopupWindowShow(String text ) {
        BorderPane pane;
        Scene scene;
        Stage stage;
        Label l = new Label(text);
        l.setLayoutX(10);
        l.setLayoutY(10);
        l.setTextFill(Color.BLACK);
        l.setFont(new Font("Arial", 24));

        pane = new BorderPane();
        pane.setRight(l);
        scene = new Scene(pane);

        // Create the actual window and display it.
        stage = new Stage();
        stage.setScene(scene);

        stage.showAndWait();

    }
}
