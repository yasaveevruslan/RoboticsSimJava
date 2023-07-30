package com.example.demo2.training;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogicBorders {
    String PATH_AREA = "C:\\Users\\Monbe\\IdeaProjects\\Sim\\RoboticsSimJava\\src\\main\\resources\\com\\example\\demo2\\paint.png";
    Mat sourceImage = Imgcodecs.imread(PATH_AREA);

    static{
        System.load("C:\\Users\\Monbe\\IdeaProjects\\Sim\\RoboticsSimJava\\src\\main\\java\\com\\example\\demo2\\opencv_java440.dll");
    }


    //Берёт значения сверху
    public void findNearestUpContours(int posX, int posY) {

        double distance = 0.0;

        try {

                List<MatOfPoint> contours = findNearestContours();
                //Условия для фильтрации контуров, находящихся выше картинки робота
                int yThreshold = 36;
                List<MatOfPoint> nearestContours = new ArrayList<>();
                double minDistance = Double.MAX_VALUE;

                Point robotPosition = new Point(posX, posY);

                for (MatOfPoint contour : contours) {
                    Moments moments = Imgproc.moments(contour);
                    double centerX = moments.m10 / moments.m00;
                    double centerY = moments.m01 /   moments.m00;

                    double distanceX = Math.abs(centerX - robotPosition.x);

                    if (distanceX <= yThreshold && centerY < posY) {
                        distance = Math.sqrt(Math.pow(centerX - robotPosition.x, 2) + Math.pow(centerY - robotPosition.y, 2));
                        if (distance < minDistance) {
                            minDistance = distance;
                            nearestContours.clear();
                            nearestContours.add(contour);
                        }
                    }
                }


                showFrameContours(nearestContours);

        } catch (Exception e) {
            e.printStackTrace();
        }

//        Elements.disUp = distance;
    }

    //Берёт значения снизу
    public void findNearestDownContours(int posX, int posY) {

        double distance = 0.0;

        try {
                List<MatOfPoint> contours = findNearestContours();

                //Условия для фильтрации контуров, находящихся ниже картинки робота
                int yThreshold = 36;
                List<MatOfPoint> nearestContours = new ArrayList<>();
                double minDistance = Double.MAX_VALUE;

                Point robotPosition = new Point(posX, posY);

                for (MatOfPoint contour : contours) {
                    Moments moments = Imgproc.moments(contour);
                    double centerX = moments.m10 / moments.m00;
                    double centerY = moments.m01 / moments.m00;

                    double distanceX = Math.abs(centerX - robotPosition.x);

                    if (distanceX <= yThreshold && centerY > posY) {
                        distance = Math.sqrt(Math.pow(centerX - robotPosition.x, 2) + Math.pow(centerY - robotPosition.y, 2));
                        if (distance < minDistance) {
                            minDistance = distance;
                            nearestContours.clear();
                            nearestContours.add(contour);
                        }
                    }
                }

//                showFrameContours(nearestContours);


        } catch (Exception e) {
            e.printStackTrace();
        }

//        Elements.disDown = distance;

    }
    //справа
    public void findNearestRightContours(int posX, int posY) {

        double distance = 0.0;

        try {
                List<MatOfPoint> contours = findNearestContours();
                //Условия для фильтрации контуров, находящихся ниже картинки робота
                List<MatOfPoint> nearestContours = new ArrayList<>();
                double minDistance = Double.MAX_VALUE;

                Point robotPosition = new Point(posX, posY);

                for (MatOfPoint contour : contours) {
                    Moments moments = Imgproc.moments(contour);
                    double centerX = moments.m10 / moments.m00;
                    double centerY = moments.m01 / moments.m00;

                    double distanceX = centerX - robotPosition.x;
                    double distanceY = Math.abs(centerY - robotPosition.y);

                    if (distanceX > 0 && distanceY <= 36) { // Проверка только справа и по вертикали
                        distance = Math.sqrt(Math.pow(centerX - robotPosition.x, 2) + Math.pow(centerY - robotPosition.y, 2));
                        if (distance < minDistance) {
                            minDistance = distance;
                            nearestContours.add(contour);
                        }
                    }
                }

                showFrameContours(nearestContours);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Elements.disRight = distance;
    }

    //слева
    public void findNearestLeftContours(int posX, int posY) {

        double distance = 0.0;

        try {
                List<MatOfPoint> contours = findNearestContours();

                //Условия для фильтрации контуров, находящихся левее картинки робота
                List<MatOfPoint> nearestContours = new ArrayList<>();
                double minDistance = Double.MAX_VALUE;

                Point robotPosition = new Point(posX, posY);



                for (MatOfPoint contour : contours) {
                    Moments moments = Imgproc.moments(contour);
                    double centerX = moments.m10 / moments.m00;
                    double centerY = moments.m01 / moments.m00;

                    double distanceX = centerX - robotPosition.x;
                    double distanceY = Math.abs(centerY - robotPosition.y);

                    if (distanceX < 0 && distanceY <= 36) { // Проверка только слева и по вертикали
                        distance = Math.sqrt(Math.pow(centerX - robotPosition.x, 2) + Math.pow(centerY - robotPosition.y, 2));
                        if (distance < minDistance) {
                            minDistance = distance;
                            nearestContours.add(contour);
                        }
                    }
                }

                showFrameContours(nearestContours);

        } catch (Exception e) {
            e.printStackTrace();
        }

//        Elements.disLeft = distance;

    }

    public List<MatOfPoint> calculateBorders(int posX, int posY){

        double distanceUp = 0.0;
        double distanceDown = 0.0;
        double distanceRight = 0.0;
        double distanceLeft = 0.0;

        try {
            List<MatOfPoint> contours = findNearestContours();

            //Условия для фильтрации контуров, находящихся левее картинки робота
            List<MatOfPoint> nearestContours = new ArrayList<>();

            int yThreshold = 36;

            double minDistanceUp = Double.MAX_VALUE;
            double minDistanceDown = Double.MAX_VALUE;
            double minDistanceRight = Double.MAX_VALUE;
            double minDistanceLeft = Double.MAX_VALUE;

            Point robotPosition = new Point(posX, posY);



            for (MatOfPoint contour : contours) {
                Moments moments = Imgproc.moments(contour);
                double centerX = moments.m10 / moments.m00;
                double centerY = moments.m01 / moments.m00;

                double distanceX = centerX - robotPosition.x;
                double distanceY = Math.abs(centerY - robotPosition.y);

                double distanceXDownUp = Math.abs(centerX - robotPosition.x);

                if (distanceX < 0 && distanceY <= 36) { // Проверка только слева и по вертикали
                    distanceLeft = Math.sqrt(Math.pow(centerX - robotPosition.x, 2) + Math.pow(centerY - robotPosition.y, 2));
                    if (distanceLeft < minDistanceLeft) {
                        minDistanceLeft = distanceLeft;
                        nearestContours.add(contour);
                    }
                }

                if (distanceX > 0 && distanceY <= 36) { // Проверка только справа и по вертикали
                    distanceRight = Math.sqrt(Math.pow(centerX - robotPosition.x, 2) + Math.pow(centerY - robotPosition.y, 2));
                    if (distanceRight < minDistanceRight) {
                        minDistanceRight = distanceRight;
                        nearestContours.add(contour);
                    }
                }

                if (distanceXDownUp <= yThreshold && centerY > posY) {
                    distanceDown = Math.sqrt(Math.pow(centerX - robotPosition.x, 2) + Math.pow(centerY - robotPosition.y, 2));
                    if (distanceDown < minDistanceDown) {
                        minDistanceDown = distanceDown;
                        nearestContours.clear();
                        nearestContours.add(contour);
                    }
                }

                if (distanceXDownUp <= yThreshold && centerY < posY) {
                    distanceUp = Math.sqrt(Math.pow(centerX - robotPosition.x, 2) + Math.pow(centerY - robotPosition.y, 2));
                    if (distanceUp < minDistanceUp) {
                        minDistanceUp = distanceUp;
                        nearestContours.clear();
                        nearestContours.add(contour);
                    }
                }
            }
//                    Elements.disUp = distanceUp;
//        Elements.disDown = distanceDown;
//        Elements.disRight = distanceRight;
//        Elements.disLeft = distanceLeft;
            return nearestContours;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }


    private List<MatOfPoint> findNearestContours(){

        try{
            if (sourceImage.empty()) {
                return Collections.emptyList();
            } else {
                //Обработка самого изображения
                Imgproc.cvtColor(sourceImage, sourceImage, Imgproc.COLOR_BGR2GRAY);
                Mat edges = new Mat();
                Imgproc.Canny(sourceImage, edges, 100, 200, 5);

                List<MatOfPoint> contours = new ArrayList<>();
                Mat hierarchy = new Mat();
                Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
                return contours;
            }
        }catch (Exception e){
            return Collections.emptyList();
        }
    }

    public Mat showFrameContours(List<MatOfPoint> nearestContours){
        Mat windowBoard = Imgcodecs.imread(PATH_AREA);

        // Отрисовка контуров подходящих по условию
        Scalar contourColor = new Scalar(255, 0, 255);
        Imgproc.drawContours(windowBoard, nearestContours, -1, contourColor, 4);

        return windowBoard;

    }



}
