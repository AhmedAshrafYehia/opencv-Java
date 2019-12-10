package sample.Controllers;

import sample.Utils.FilePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.opencv.enhancement;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import sample.popup.PopUpController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    public Button closeButton;
    @FXML
    public Button reloadButton;
    @FXML
    public Button imgDetailBtn;
    @FXML
    public Button processBtn;
    @FXML
    public TextField path;

    private static FileChooser fileChooser = new FileChooser();
    @FXML
    public Slider mySlider;
    @FXML
    public TextField angleTextField;
    @FXML
    public CheckBox copulativeBox;
    @FXML
    public Button histogramBtn;
    @FXML
    public Button ZoomInbtn;
    @FXML
    public Button Zoomoutbtn;
    @FXML
    public Button Brightnessbtn;
    @FXML
    public Button Darknessbnt;
    @FXML
    public Button Contrastbtn;
    @FXML
    public Button Negativebtn;
    @FXML
    public Button Thresholdbtn;
    @FXML
    public Button Rotationbtn;
    @FXML
    public Button Flipbtn;
    @FXML
    public Button LogTransformationbtn;
    @FXML
    public Button PowerTransformationbtn;
    @FXML
    public Button Blurbtn;
    @FXML
    public Button GaussianBlurbtn;
    @FXML
    public Button MedianBlurbtn;
    @FXML
    public Button LowPassFilterbtn;
    @FXML
    public Button TextDetectionbtn;
    @FXML
    public Button FaceDetectionbtn;

    private Float angleVal;
    private Mat img = new Mat();
    private Mat distImage = new Mat();

    @FXML
    protected void handleCancelButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void handleReloadButtonAction(ActionEvent event) {
        path.setText("");
        img = new Mat();
        reloadButton.setDisable(true);
        imgDetailBtn.setDisable(true);
        processBtn.setDisable(true);
        mySlider.setDisable(true);
        angleTextField.setDisable(true);
        copulativeBox.setDisable(true);
    }


    @FXML
    public void handleBrowseButtonAction(ActionEvent actionEvent) throws IOException {
        File file = new FilePicker(path.getText()).showOpenDialog();
        if (file != null) {
            path.setText(file.getPath());
            fileChooser.setInitialDirectory(file.getParentFile());
            Mat read = Imgcodecs.imread(path.getText());
            // Converting the image to gray scale and
            // saving it in the dst matrix
                Imgproc.cvtColor(read, img, Imgproc.COLOR_RGB2GRAY);
            reloadButton.setDisable(false);
            imgDetailBtn.setDisable(false);
            processBtn.setDisable(false);
            mySlider.setDisable(false);
            angleTextField.setDisable(false);
            copulativeBox.setDisable(false);
            histogramBtn.setDisable(false);

        }
    }

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

    }


    public void showImageDetailsButtonAction(ActionEvent actionEvent) {
        new PopUpController().showPopUp(img);
    }

    public void ProcessAction(ActionEvent actionEvent) {
        try {
            // convert angle to integer
            convertToInteger();
            // bind the selected fruit label to the selected fruit in the combo box.
            String value="a";
            switch (value) {
                case "Zoom In": {
                    if (copulativeBox.isSelected())
                        img = new enhancement().zoomIn(img, mySlider.getValue());
                    else
                        distImage = new enhancement().zoomIn(img, mySlider.getValue());
                    break;
                }
                case "Zoom Out": {
                    if (copulativeBox.isSelected())
                        img = new enhancement().zoomOut(img, mySlider.getValue());
                    else
                        distImage = new enhancement().zoomOut(img, mySlider.getValue());
                    break;
                }
                case "Brightness": {
                    if (copulativeBox.isSelected())
                        img = new enhancement().bright(img, mySlider.getValue());
                    else
                        distImage = new enhancement().bright(img, mySlider.getValue());

                    break;
                }
                case "Darkness": {
                    if (copulativeBox.isSelected())
                        img = new enhancement().dark(img, mySlider.getValue());
                    else
                        distImage = new enhancement().dark(img, mySlider.getValue());
                    break;
                }
                case "Negative": {
                    if (copulativeBox.isSelected())
                        img = new enhancement().negative(img);
                    else
                        distImage = new enhancement().negative(img);
                    break;
                }
                case "Threshold": {
                    if (copulativeBox.isSelected())
                        img = new enhancement().threshold(img, mySlider.getValue());
                    else
                        distImage = new enhancement().threshold(img, mySlider.getValue());
                    break;
                }
                case "Rotation": {
                    if (copulativeBox.isSelected())
                        img = new enhancement().rotate(img, angleVal);
                    else
                        distImage = new enhancement().rotate(img, angleVal);
                    break;
                }
                case "Flip": {
                    if (copulativeBox.isSelected())
                        img = new enhancement().flip(img, angleVal);
                    else
                        distImage = new enhancement().flip(img, angleVal);
                    break;
                }
                case "Histogram Equalization": {
                    if (copulativeBox.isSelected())
                        img = new enhancement().histEqualize(img);
                    else
                        distImage = new enhancement().histEqualize(img);
                    break;
                }
                case "Log Transformation": {
                    if (copulativeBox.isSelected())
                        img = new enhancement().log(img);
                    else
                        distImage = new enhancement().log(img);
                    break;
                }
                case "Power Transformation": {
                    if (copulativeBox.isSelected())
                        img = new enhancement().power(img, angleVal);
                    else
                        distImage = new enhancement().power(img, angleVal);
                    break;
                }
                case "Blur": {
                    if (copulativeBox.isSelected())
                        img = new enhancement().blur(img, mySlider.getValue());
                    else
                        distImage = new enhancement().blur(img, mySlider.getValue());
                    break;
                }
                case "Gaussian Blur": {
                    if (copulativeBox.isSelected())
                        img = new enhancement().gBlur(img, mySlider.getValue());
                    else
                        distImage = new enhancement().gBlur(img, mySlider.getValue());
                    break;
                }
                case "Median Blur": {
                    if (copulativeBox.isSelected())
                        img = new enhancement().mBlur(img, mySlider.getValue());
                    else
                        distImage = new enhancement().mBlur(img, mySlider.getValue());
                    break;
                }
                case "Contrast": {
                    if (copulativeBox.isSelected())
                        img = new enhancement().contrast(img, mySlider.getValue(), angleVal);
                    else
                        distImage = new enhancement().contrast(img, mySlider.getValue(), angleVal);
                    break;
                }
                case "Low Pass Filter": {
                    if (copulativeBox.isSelected())
                        img = new enhancement().lpf(img, mySlider.getValue());
                    else
                        distImage = new enhancement().lpf(img, mySlider.getValue());
                    break;
                }
                case "Text Detection": {
                    new enhancement().textDetect(path.getText(),angleVal);
                    break;
                }        case "Face Detection": {
                    if (copulativeBox.isSelected())
                        img = new enhancement().faceDetect(img);
                    else
                        distImage = new enhancement().faceDetect(img);
                    break;
                }
                default:{
                    System.out.println("Default");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            img = new Mat();
            distImage = new Mat();
        }

    }

    public void convertToInteger() {
        try {
            angleVal = Float.parseFloat(angleTextField.getText());
            if (angleVal > 360)
                angleVal = 360f;
            else if (angleVal < -360)
                angleVal = -360f;
        } catch (Exception e) {
            angleVal = 1f;
        }
    }

    @FXML
    public void HistogramBtnAction(ActionEvent actionEvent) {
        convertToInteger();

        if (copulativeBox.isSelected())
            img = new enhancement().histEqualize(img);
        else
            distImage = new enhancement().histEqualize(img);
    }



    @FXML
    public void GaussianBlurButtonAction(ActionEvent actionEvent) {

        convertToInteger();

        if (copulativeBox.isSelected())
            img = new enhancement().gBlur(img, mySlider.getValue());
        else
            distImage = new enhancement().gBlur(img, mySlider.getValue());


    }

    @FXML
    public void ZoomInButtonAction(ActionEvent actionEvent) {
        convertToInteger();


        if (copulativeBox.isSelected())
            img = new enhancement().zoomIn(img, mySlider.getValue());
        else
            distImage = new enhancement().zoomIn(img, mySlider.getValue());


    }

    @FXML
    public void ZoomoutButtonAction(ActionEvent actionEvent) {
        convertToInteger();


        if (copulativeBox.isSelected())
            img = new enhancement().zoomOut(img, mySlider.getValue());
        else
            distImage = new enhancement().zoomOut(img, mySlider.getValue());

    }

    @FXML
    public void BrightnessButtonAction(ActionEvent actionEvent) {
        convertToInteger();

        if (copulativeBox.isSelected())
            img = new enhancement().bright(img, mySlider.getValue());
        else
            distImage = new enhancement().bright(img, mySlider.getValue());


    }

    @FXML
    public void DarknessButtonAction(ActionEvent actionEvent) {
        convertToInteger();

        if (copulativeBox.isSelected())
            img = new enhancement().bright(img, mySlider.getValue());
        else
            distImage = new enhancement().bright(img, mySlider.getValue());

    }

    @FXML
    public void ContrastButtonAction(ActionEvent actionEvent) {
        convertToInteger();

        if (copulativeBox.isSelected())
            img = new enhancement().contrast(img, mySlider.getValue(), angleVal);
        else
            distImage = new enhancement().contrast(img, mySlider.getValue(), angleVal);


    }

    @FXML
    public void NegativeButtonAction(ActionEvent actionEvent) {
        convertToInteger();

        if (copulativeBox.isSelected())
            img = new enhancement().negative(img);
        else
            distImage = new enhancement().negative(img);
    }

    @FXML
    public void ThresholdButtonAction(ActionEvent actionEvent) {
        convertToInteger();

        if (copulativeBox.isSelected())
            img = new enhancement().threshold(img, mySlider.getValue());
        else
            distImage = new enhancement().threshold(img, mySlider.getValue());
    }

    @FXML
    public void RotationButtonAction(ActionEvent actionEvent) {

        convertToInteger();

        if (copulativeBox.isSelected())
            img = new enhancement().rotate(img, angleVal);
        else
            distImage = new enhancement().rotate(img, angleVal);
    }

    @FXML
    public void FlipButtonAction(ActionEvent actionEvent) {
        convertToInteger();

        if (copulativeBox.isSelected())
            img = new enhancement().flip(img, angleVal);
        else
            distImage = new enhancement().flip(img, angleVal);
    }

    @FXML
    public void LogTransformationButtonAction(ActionEvent actionEvent) {
        convertToInteger();

        if (copulativeBox.isSelected())
            img = new enhancement().log(img);
        else
            distImage = new enhancement().log(img);
    }

    @FXML
    public void PowerTransformationButtonAction(ActionEvent actionEvent) {
        convertToInteger();

        if (copulativeBox.isSelected())
            img = new enhancement().power(img, angleVal);
        else
            distImage = new enhancement().power(img, angleVal);
    }

    @FXML
    public void BlurButtonAction(ActionEvent actionEvent) {
        convertToInteger();

        if (copulativeBox.isSelected())
            img = new enhancement().blur(img, mySlider.getValue());
        else
            distImage = new enhancement().blur(img, mySlider.getValue());
    }

    @FXML
    public void MedianBlurButtonAction(ActionEvent actionEvent) {
        convertToInteger();

        if (copulativeBox.isSelected())
            img = new enhancement().gBlur(img, mySlider.getValue());
        else
            distImage = new enhancement().gBlur(img, mySlider.getValue());
    }

    @FXML
    public void LowPassFilterButtonAction(ActionEvent actionEvent) {
        convertToInteger();

        if (copulativeBox.isSelected())
            img = new enhancement().lpf(img, mySlider.getValue());
        else
            distImage = new enhancement().lpf(img, mySlider.getValue());
    }

    @FXML
    public void TextDetectionButtonAction(ActionEvent actionEvent) {
        convertToInteger();

        new enhancement().textDetect(path.getText(),angleVal);
    }

    @FXML
    public void FaceDetectionButtonAction(ActionEvent actionEvent) {
        convertToInteger();

        if (copulativeBox.isSelected())
            img = new enhancement().faceDetect(img);
        else
            distImage = new enhancement().faceDetect(img);
    }
}
