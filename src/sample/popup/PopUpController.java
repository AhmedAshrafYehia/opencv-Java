package sample.popup;

import sample.Utils.openCvUtils;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class PopUpController implements Initializable {
    private static Double x, y;
    private static Double Cx, Cy;
//    private static Stage stage;

    @FXML
    public Button closeButton;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public Pane pane;
    private static int lastLineY = -10;
    private static Mat image;

    public void showPopUp(Mat image) {

        PopUpController.image = image;
        lastLineY = -10;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("popup.fxml"));
        try {
            Parent layout = loader.load();
//            layout.getStylesheets().add("C:\\Users\\abdo\\Desktop\\image\\src\\sample.popup\\PopUpStyle.css");


            Scene scene = new Scene(layout);
            Stage popupStage = new Stage();
//            stage=popupStage;
            popupStage.initOwner(Main.stage);

            layout.setOnMousePressed(event -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });
            layout.setOnMouseDragged(event -> {
                popupStage.setX(event.getScreenX() - x);
                popupStage.setY(event.getScreenY() - y);
                Cx = popupStage.getX();
                Cy = popupStage.getY();
            });

            if (x != null && y != null) {
                popupStage.setX(Cx);
                popupStage.setY(Cy);
            } else {
                popupStage.setX(Main.stage.getX() + 70);
                popupStage.setY(Main.stage.getY() + 50);
            }

            scene.setFill(Color.TRANSPARENT);
            popupStage.initStyle(StageStyle.TRANSPARENT);
            popupStage.setTitle("Image Processing");
            popupStage.setScene(scene);
            popupStage.initModality(Modality.APPLICATION_MODAL);

            popupStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showLog(image);
    }


    @FXML
    protected void handleCancelButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        ;
        stage.close();
        image = new Mat();
    }


    public void showLog(Mat image) {
        lastLineY = lastLineY + 20;
        Label l = new Label("rows: " + image.rows());
        l.setLayoutX(10);
        l.setLayoutY(lastLineY);
        l.setTextFill(Color.YELLOW);
        l.setFont(new Font("Arial", 24));
        pane.getChildren().add(l);
        lastLineY += 50;
        Label label = new Label("cols: " + image.cols());
        label.setLayoutX(10);
        label.setLayoutY(lastLineY);
        label.setTextFill(Color.YELLOW);
        label.setFont(new Font("Arial", 24));
        pane.getChildren().add(label);
        lastLineY += 50;
        Map <String, Integer> values = calcImgValues(image);
        values.keySet().forEach(value ->{
            Label min = new Label(value+": " + values.get(value));
            min.setLayoutX(10);
            min.setLayoutY(lastLineY);
            min.setTextFill(Color.YELLOW);
            min.setFont(new Font("Arial", 24));
            pane.getChildren().add(min);
            lastLineY += 50;
        });


        slowScrollToBottom(scrollPane);
        showHistogram();
    }

    @FXML
    private ImageView histogram;
    @FXML
    private ImageView imgToShow;

    private void showHistogram() {
        int hist_w = 150;
        int hist_h = 150;
        int bin_w = (int) Math.round(hist_w / image.get(0, 0)[0]);
        Mat histImage = new Mat(hist_h, hist_w, CvType.CV_8UC3, new Scalar(0, 0, 0));
        Core.normalize(image, image, 0, histImage.rows(), Core.NORM_MINMAX, -1, new Mat());
        for (int i = 1; i < image.get(0, 0)[0]; i++) {
            Imgproc.line(histImage, new Point(bin_w * (i - 1), hist_h - Math.round(image.get(i - 1, 0)[0])), new Point(bin_w * (i), hist_h - Math.round(image.get(i, 0)[0])), new Scalar(255, 0, 0), 1, 4, 0);
        }
        Image hist = openCvUtils.mat2Image(histImage);
        histogram.setImage(hist);
        imgToShow.setImage(openCvUtils.mat2Image(image));
    }

    @FXML
    protected void handleOnKeyPressed(KeyEvent event) {
        KeyCode key = event.getCode();
        if (key.equals(KeyCode.ENTER) || key.equals(KeyCode.ESCAPE)) {
            handleCancelButtonAction(null);
        }
    }

    private static void slowScrollToBottom(ScrollPane scrollPane) {
        Animation animation = new Timeline(
                new KeyFrame(Duration.millis(1100),
                        new KeyValue(scrollPane.vvalueProperty(), 1)));
        animation.play();
    }

    private Map<String, Integer> calcImgValues(Mat img) {
        int min = 0;
        int max = 0;
        int total = 0;
        Map<String,Integer> map = new HashMap <>();
        map.put("min", (int) img.get(0,0)[0]);
        map.put("max", (int) img.get(0,0)[0]);
        map.put("total",0);
        for (int i = 0; i < img.rows(); i++) {
            for (int j = 0; j < img.cols(); j++) {
                if (img.get(i, j)[0] < min) {
                    map.put("min", (int) img.get(i, j)[0]);
                }
                if (img.get(i, j)[0] > max) {
                    map.put("max", (int) img.get(i, j)[0]);
                }
                map.put("total",total);
                total+=img.get(i, j)[0];
            }
        }
        int average = (int) (total / img.total());
        map.put("average",average);

        return map;
    }
}
