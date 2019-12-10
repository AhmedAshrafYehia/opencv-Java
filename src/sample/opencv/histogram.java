package sample.opencv;

import sample.Utils.openCvUtils;
import javafx.scene.image.Image;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class histogram {
    private void calcHist(Mat src) {
        //! [Separate the image in 3 places ( B, G and R )]
        List <Mat> bgrPlanes = new ArrayList <>();
        Core.split(src, bgrPlanes);
        //! [Separate the image in 3 places ( B, G and R )]

        //! [Establish the number of bins]
        int histSize = 256;
        //! [Establish the number of bins]

        //! [Set the ranges ( for B,G,R) )]
        float[] range = {0, 256}; //the upper boundary is exclusive
        MatOfFloat histRange = new MatOfFloat(range);
        //! [Set the ranges ( for B,G,R) )]

        //! [Set histogram param]
        boolean accumulate = false;
        //! [Set histogram param]

        //! [Compute the histograms]
        Mat bHist = new Mat(), gHist = new Mat(), rHist = new Mat();
        Imgproc.calcHist(bgrPlanes, new MatOfInt(0), new Mat(), bHist, new MatOfInt(histSize), histRange, accumulate);
        //! [Compute the histograms]

        //! [Draw the histograms for B, G and R]
        int histW = 512, histH = 400;
        int binW = (int) Math.round((double) histW / histSize);

        Mat histImage = new Mat(histH, histW, CvType.CV_8UC3, new Scalar(0, 0, 0));
        //! [Draw the histograms for B, G and R]

        //! [Normalize the result to ( 0, histImage.rows )]
        Core.normalize(bHist, bHist, 0, histImage.rows(), Core.NORM_MINMAX);

        //! [Normalize the result to ( 0, histImage.rows )]

        //! [Draw for each channel]
        float[] bHistData = new float[(int) (bHist.total() * bHist.channels())];
        bHist.get(0, 0, bHistData);
        float[] gHistData = new float[(int) (gHist.total() * gHist.channels())];
        gHist.get(0, 0, gHistData);
        float[] rHistData = new float[(int) (rHist.total() * rHist.channels())];
        rHist.get(0, 0, rHistData);

        for (int i = 1; i < histSize; i++) {
            Imgproc.line(histImage, new Point(binW * (i - 1), histH - Math.round(bHistData[i - 1])),
                    new Point(binW * (i), histH - Math.round(bHistData[i])), new Scalar(255, 0, 0), 2);
        }
        //! [Draw for each channel]

        //! [Display]
        HighGui.imshow("Source image", src);
        HighGui.imshow("calcHist Demo", histImage);
        HighGui.waitKey(0);
        //! [Display]

        System.exit(0);
    }


    public Image showHistogram(Mat image) {
        int hist_w = 150;
        int hist_h = 150;
        int bin_w = (int) Math.round(hist_w / image.get(0, 0)[0]);
        Mat histImage = new Mat(hist_h, hist_w, CvType.CV_8UC3, new Scalar(0, 0, 0));
        Core.normalize(image, image, 0, histImage.rows(), Core.NORM_MINMAX, -1, new Mat());
        for (int i = 1; i < image.get(0, 0)[0]; i++) {
            Imgproc.line(histImage, new Point(bin_w * (i - 1), hist_h - Math.round(image.get(i - 1, 0)[0])), new Point(bin_w * (i), hist_h - Math.round(image.get(i, 0)[0])), new Scalar(255, 0, 0), 1, 4, 0);
        }
       Image hist = openCvUtils.mat2Image(histImage);
        return hist;
    }


}

