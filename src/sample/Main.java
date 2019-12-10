package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.opencv.core.Core;

public class Main extends Application {
    public static double x, y;
    public static Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        System.out.println(System.getProperty("java.library.path"));
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Image Processing");
        Scene scene=new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.DECORATED);

//        primaryStage.getIcons().add(new Image("EndpointGenerator/icon.png"));
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);
            stage=primaryStage;
        });
        primaryStage.show();
        stage=primaryStage;
    }



    public static void main(String[] args) {
        launch(args);
    }
}
