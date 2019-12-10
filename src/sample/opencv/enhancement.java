package sample.opencv;

import sample.Utils.openCvUtils;
import sample.Utils.popubUtils;
import javafx.scene.image.Image;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;

public class enhancement {


    public Mat zoomIn(Mat img, double sliderValue) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat destination = new Mat();
        try {
            int zoomingFactor = (int) sliderValue;
            destination = new Mat(img.rows() * zoomingFactor, img.cols() * zoomingFactor, img.type());

            Imgproc.resize(img, destination, destination.size(), zoomingFactor, zoomingFactor, Imgproc.INTER_AREA);
            Image hist = new histogram().showHistogram(destination);
            new popubUtils().imagePopupWindowShow(openCvUtils.mat2Image(destination), hist);


        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return destination;
    }

    public Mat zoomOut(Mat img, double sliderValue) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat destination = new Mat();
        try {
            String slider = 0 + "." + (int) sliderValue;
            float zoomingFactor = Float.parseFloat(slider);
            int rows = (int) (img.rows() * zoomingFactor);
            int cols = (int) (img.cols() * zoomingFactor);
            destination = new Mat(rows, cols, img.type());

            Imgproc.resize(img, destination, destination.size(), zoomingFactor, zoomingFactor, Imgproc.INTER_AREA);
            Image hist = new histogram().showHistogram(destination);

            new popubUtils().imagePopupWindowShow(openCvUtils.mat2Image(destination), hist);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return destination;
    }

    public Mat bright(Mat input, double sliderValue) {
        int value = (int) (sliderValue * 10);
        Mat output = new Mat(input.rows(),input.cols(),input.type());
        input.convertTo(output, -1, 1, value); //increase the brightness by slider value
        Image hist = new histogram().showHistogram(output);
        new popubUtils().imagePopupWindowShow(openCvUtils.mat2Image(input), hist);
        return input;
    }

    public Mat dark(Mat input, double sliderValue) {
        int value = (int) (sliderValue * -10);
        Mat output = new Mat(input.rows(),input.cols(),input.type());
        input.convertTo(output, -1, 1, value); //decrease the brightness by slider value
        Image hist = new histogram().showHistogram(output);
        new popubUtils().imagePopupWindowShow(openCvUtils.mat2Image(output), hist);
        return output;
    }


    public Mat negative(Mat img) {
        Mat invertedMat = new Mat(img.rows(), img.cols(), img.type());
        Mat invertColorMat = new Mat(img.rows(), img.cols(), img.type(), new Scalar(255, 255, 255));
        Core.subtract(invertColorMat, img, invertedMat);
        Image hist = new histogram().showHistogram(invertedMat);
        new popubUtils().imagePopupWindowShow(openCvUtils.mat2Image(invertedMat), hist);
        return invertedMat;
    }

    public Mat threshold(Mat img, double sliderValue) {
        int value = (int) sliderValue * 10;
        Mat thresh = new Mat();
//        Imgproc.threshold(img, thresh, value, 255, Imgproc.THRESH_BINARY_INV);
        // Creating an empty matrix to store the result

        Imgproc.adaptiveThreshold(img, thresh, value, Imgproc.ADAPTIVE_THRESH_MEAN_C,
                Imgproc.THRESH_BINARY, 11, 12);
        Image hist = new histogram().showHistogram(thresh);
        new popubUtils().imagePopupWindowShow(openCvUtils.mat2Image(thresh), hist);
        return thresh;
    }

    public Mat rotate(Mat source, Float angleVal) {
        Mat rotMat;
        Mat destination = new Mat(source.rows(), source.cols(), source.type());
        Point center = new Point(destination.cols() / 2, destination.rows() / 2);
        rotMat = Imgproc.getRotationMatrix2D(center, angleVal, 1);
        Imgproc.warpAffine(source, destination, rotMat, destination.size());
        Imgcodecs.imwrite("E://out//lena-rotate.jpg", destination);
        Image hist = new histogram().showHistogram(destination);
        new popubUtils().imagePopupWindowShow(openCvUtils.mat2Image(destination), hist);
        return destination;

    }

    public Mat flip(Mat img, Float angleVal) {
        Mat output = new Mat();
        if (angleVal > 1) angleVal = 1f;
        else if (angleVal < -1) angleVal = -1f;
        else angleVal = 1f;
        Core.flip(img, output, angleVal.intValue());
        Image hist = new histogram().showHistogram(output);
        new popubUtils().imagePopupWindowShow(openCvUtils.mat2Image(output), hist);
        return output;
    }

    public Mat histEqualize(Mat img) {
        Mat output = new Mat();
        Imgproc.equalizeHist(img, output);
        Image hist = new histogram().showHistogram(output);
        new popubUtils().imagePopupWindowShow(openCvUtils.mat2Image(output), hist);
        return output;
    }


    public Mat log(Mat img) {
        Mat output = new Mat(img.rows(), img.cols(), img.type());
        for (int i = 0; i < img.rows(); i++) {
            for (int j = 0; j < img.cols(); j++) {
                double log = Math.log(img.get(i, j)[0]);
                output.put(i, j, log);
            }
        }
        Image hist = new histogram().showHistogram(output);
        new popubUtils().imagePopupWindowShow(openCvUtils.mat2Image(output), hist);
        return output;
    }

    public Mat power(Mat img, Float gama) {
        if (gama > 1.5) gama = 1.5f;
        else if (gama < 0.5) gama = 0.5f;
        else gama = 1.5f;
        int cons = 1;
        Mat output = new Mat(img.rows(), img.cols(), img.type());
        for (int i = 0; i < img.rows(); i++) {
            for (int j = 0; j < img.cols(); j++) {
                double data = cons * Math.pow(img.get(i, j)[0], gama);
                output.put(i, j, data);
            }
        }
        new popubUtils().imagePopupWindowShow(openCvUtils.mat2Image(output),null);
        return output;
    }


    public Mat blur(Mat src, double value) {
        if (value == 0) value = 1;
        // Creating an empty matrix to store the result
        Mat dst = new Mat();

        // Creating the Size and Point objects
        Size size = new Size(value*5, value*5);
        Point point = new Point(value, value);

        // Applying Blur effect on the Image
        Imgproc.blur(src, dst, size, point, Core.BORDER_DEFAULT);

        Image hist = new histogram().showHistogram(dst);
        new popubUtils().imagePopupWindowShow(openCvUtils.mat2Image(dst), hist);
        return dst;
    }

    public Mat gBlur(Mat src, double value) {
        if (value == 0) value = 1;

        // Creating an empty matrix to store the result
        Mat dst = new Mat();

        // Applying GaussianBlur on the Image
        Imgproc.GaussianBlur(src, dst, new Size(value*5, value*5), 0);

        Image hist = new histogram().showHistogram(dst);
        new popubUtils().imagePopupWindowShow(openCvUtils.mat2Image(dst), hist);
        // Writing the image
        return dst;
    }

    public Mat mBlur(Mat src, double value) {
        Mat dst = new Mat();
        try {
            if (value == 0) value = 1;

            // Creating an empty matrix to store the result

            // Applying MedianBlur on the Image
            Imgproc.medianBlur(src, dst, (int) value);

            Image hist = new histogram().showHistogram(dst);
            new popubUtils().imagePopupWindowShow(openCvUtils.mat2Image(dst), hist);
        } catch (Exception e){
            return src;
        }
        return  dst;

    }

    public Mat contrast(Mat source, double beta, Float alpha) {
        Mat destination = new Mat(source.rows(), source.cols(), source.type());
        // applying brightness enhancement
        source.convertTo(destination, -1, alpha, beta);

        new popubUtils().imagePopupWindowShow(openCvUtils.mat2Image(destination), null);
        return destination;
    }


    public Mat lpf(Mat img, double value) {
        return null;
    }


    public String textDetect(String path, Float angleVal) {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("resources/tessdata");
        if (angleVal == 1f)
            tesseract.setLanguage("eng");
        else
            tesseract.setLanguage("ara");



        String result = "NOTHING";

        try {
            result = tesseract.doOCR(new File(path));
        } catch (TesseractException ex) {
            ex.printStackTrace();
        }
        System.out.println(result);

        new popubUtils().imagePopupWindowShow(result);
        return result;
    }


    public Mat faceDetect(Mat src) {

        // Instantiating the CascadeClassifier
        String xmlFile = "resources/lbpcascade_frontalface.xml";

        CascadeClassifier classifier = new CascadeClassifier(xmlFile);

        // Detecting the face in the snap
        MatOfRect faceDetections = new MatOfRect();
        classifier.detectMultiScale(src, faceDetections);
        System.out.println(String.format("Detected %s faces",
                faceDetections.toArray().length));

        // Drawing boxes
        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(
                    src,                                               // where to draw the box
                    new Point(rect.x, rect.y),                            // bottom left
                    new Point(rect.x + rect.width, rect.y + rect.height), // top right
                    new Scalar(0, 0, 255),
                    3                                                     // RGB colour
            );
        }

        new popubUtils().imagePopupWindowShow(openCvUtils.mat2Image(src), null);
        return src;
    }
}
