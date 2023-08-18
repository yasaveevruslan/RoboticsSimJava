package com.example.demo2.training;

import com.example.demo2.StartApplication;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.demo2.StartApplication.lastContours;
import static com.example.demo2.StartApplication.sourceImage;

public class LogicBorders {

    //Берёт значения сверху
    public void findNearestUpContours(int posX, int posY) {
        double distanceUp = 0.0;
        try {

                List<MatOfPoint> contours = findNearestContours();
                //Условия для фильтрации контуров, находящихся выше картинки робота
                int yThreshold = 36;
                List<MatOfPoint> nearestContours = new ArrayList<>();


                Point robotPosition = new Point(posX, posY);

                for (MatOfPoint contour : contours) {
                    Moments moments = Imgproc.moments(contour);
                    double centerX = moments.m10 / moments.m00;
                    double centerY = moments.m01 /   moments.m00;

                    double distanceX = Math.abs(centerX - robotPosition.x);

                    boolean isUp = distanceX <= yThreshold && centerY < posY;

                    if (isUp) {
                        double distance = Math.sqrt(Math.pow(centerX - robotPosition.x, 2) + Math.pow(centerY - robotPosition.y, 2));
                        nearestContours.add(contour);
                        distanceUp = Math.min(distanceUp, distance);
                    }
                }




        } catch (Exception e) {
            e.printStackTrace();
        }

//        Elements.disUp = distance;
    }

    //Берёт значения снизу
    public void findNearestDownContours(int posX, int posY) {
        double distanceDown = 0.0;

        try {
                List<MatOfPoint> contours = findNearestContours();

                //Условия для фильтрации контуров, находящихся ниже картинки робота
                int yThreshold = 36;
                List<MatOfPoint> nearestContours = new ArrayList<>();

                Point robotPosition = new Point(posX, posY);

                for (MatOfPoint contour : contours) {
                    Moments moments = Imgproc.moments(contour);
                    double centerX = moments.m10 / moments.m00;
                    double centerY = moments.m01 / moments.m00;

                    double distanceX = Math.abs(centerX - robotPosition.x);

                    boolean isDown = distanceX <= yThreshold && centerY > posY;

                    if (isDown) {
                        double distance = Math.sqrt(Math.pow(centerX - robotPosition.x, 2) + Math.pow(centerY - robotPosition.y, 2));
                        nearestContours.add(contour);
                        distanceDown = Math.min(distanceDown, distance);
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
        double distanceRight = 0.0;

        try {
                List<MatOfPoint> contours = findNearestContours();
                //Условия для фильтрации контуров, находящихся ниже картинки робота
                List<MatOfPoint> nearestContours = new ArrayList<>();
                Point robotPosition = new Point(posX, posY);

                for (MatOfPoint contour : contours) {
                    Moments moments = Imgproc.moments(contour);
                    double centerX = moments.m10 / moments.m00;
                    double centerY = moments.m01 / moments.m00;

                    double distanceX = centerX - robotPosition.x;
                    double distanceY = Math.abs(centerY - robotPosition.y);

                    boolean isRight = distanceX > 0 && distanceY <= 100 && distanceY >= -100;

                    if (isRight) {
                        double distance = Math.sqrt(Math.pow(centerX - robotPosition.x, 2) + Math.pow(centerY - robotPosition.y, 2));
                        nearestContours.add(contour);
                        distanceRight = Math.min(distanceRight, distance);
                    }

                }

//                showFrameContours(nearestContours);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Elements.disRight = distance;
    }

    //слева
    public void findNearestLeftContours(int posX, int posY) {

        double distanceLeft = 0.0;
        try {
                List<MatOfPoint> contours = findNearestContours();

                //Условия для фильтрации контуров, находящихся левее картинки робота
                List<MatOfPoint> nearestContours = new ArrayList<>();
                Point robotPosition = new Point(posX, posY);

                for (MatOfPoint contour : contours) {
                    Moments moments = Imgproc.moments(contour);
                    double centerX = moments.m10 / moments.m00;
                    double centerY = moments.m01 / moments.m00;

                    double distanceX = centerX - robotPosition.x;
                    double distanceY = Math.abs(centerY - robotPosition.y);

                    boolean isLeft = distanceX < 0 && distanceY <= 36;

                    if (isLeft) {
                        double distance = Math.sqrt(Math.pow(centerX - robotPosition.x, 2) + Math.pow(centerY - robotPosition.y, 2));
                        nearestContours.add(contour);
                        distanceLeft = Math.min(distanceLeft, distance);
                    }
                }
//                showFrameContours(nearestContours);

        } catch (Exception e) {
            e.printStackTrace();
        }
//        Elements.disLeft = distance;
    }

    public List<MatOfPoint> calculateBorders(int posX, int posY) {

        double distanceUp = 0.0;
        double distanceDown = 0.0;
        double distanceRight = 0.0;
        double distanceLeft = 0.0;

        List<MatOfPoint> nearestContours = new ArrayList<>();

        try {
            List<MatOfPoint> contours = findNearestContours();
            Point robotPosition = new Point(posX, posY);
            int yThreshold = 36;

            for (MatOfPoint contour : contours) {
                Moments moments = Imgproc.moments(contour);
                double centerX = moments.m10 / moments.m00;
                double centerY = moments.m01 / moments.m00;

                double distanceX = centerX - robotPosition.x;
                double distanceY = Math.abs(centerY - robotPosition.y);
                double distanceXDownUp = Math.abs(centerX - robotPosition.x);

//                System.out.println("DistX"+ distanceX);
//                System.out.println("DistY"+ distanceY);
                boolean isLeft = distanceX < 0 && Math.abs(distanceY) <= 43 || (centerY < posY);
                boolean isRight = distanceX > 0 && distanceY <= 100 && distanceY >= -100;
                boolean isDown = distanceXDownUp <= yThreshold && centerY > posY;
                boolean isUp = distanceXDownUp <= yThreshold && centerY < posY;

                if (isLeft) {
                    double distance = Math.sqrt(Math.pow(centerX - robotPosition.x, 2) + Math.pow(centerY - robotPosition.y, 2));
                    nearestContours.add(contour);

                    if (isLeft) {
                        distanceLeft = Math.min(distanceLeft, distance);
                    } else if (isRight) {
                        distanceRight = Math.min(distanceRight, distance);
                    } else if (isDown) {
                        distanceDown = Math.min(distanceDown, distance);
                    } else if (isUp) {
                        distanceUp = Math.min(distanceUp, distance);
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
                Mat newImg = new Mat();
                //Обработка самого изображения
                Imgproc.cvtColor(sourceImage, newImg, Imgproc.COLOR_BGR2GRAY);
                Mat edges = new Mat();
                Imgproc.Canny(newImg, edges, 100, 200, 5);

                List<MatOfPoint> contours = new ArrayList<>();
                Mat hierarchy = new Mat();
                Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

                return contours;
            }
        }catch (Exception e){
            return Collections.emptyList();
        }
    }

    public Mat showFrameContours(List<MatOfPoint> nearestContours, boolean draw){
        Mat wind;

        //TODO: исправление логики, которая ниже закоменченна
//        if(draw){
//            wind = sourceImage.clone();
//            // Отрисовка контуров подходящих по условию
//            Scalar contourColor = new Scalar(255, 0, 255);
//            Imgproc.drawContours(wind, nearestContours, -1, contourColor, 4);
//        }else{
//            wind = Imgcodecs.imread(StartApplication.compMatImage()).clone();
//        }

        wind = sourceImage.clone();

        Scalar contourColor = new Scalar(255, 0, 255);
        Imgproc.drawContours(wind, nearestContours, -1, contourColor, 4);
        drawFigureRobot(wind, nearestContours);

        return wind;
    }

    private void drawFigureRobot(Mat wind, List<MatOfPoint> contours){


        for (MatOfPoint contour : contours) {

            boolean bool = isAnyPointInsideHexagon(wind,contour);
           if(bool){
               System.out.println(true);
           }
        }
    }


    public boolean isAnyPointInsideHexagon(Mat wind, MatOfPoint contour) {
        double hexagonRadius = 55;

        int robotCenterX = (int) Elements.positionRobotX;
        int robotCenterY = (int) Elements.positionRobotY-26;
        Point hexagonCenter = new Point(robotCenterX, robotCenterY);

        // Определение координат вершин шестиугольника
        Point[] hexagonPoints = new Point[6];
        for (int i = 0; i < 6; i++) {
            double angle = 2 * Math.PI / 6 * i;
            double x = hexagonCenter.x + hexagonRadius * Math.cos(angle);
            double y = hexagonCenter.y + hexagonRadius * Math.sin(angle);
            hexagonPoints[i] = new Point(x, y);
        }
        MatOfPoint2f hexagon2f = new MatOfPoint2f(hexagonPoints);
        MatOfPoint hexagon = new MatOfPoint(hexagonPoints);

        List<MatOfPoint> hexagonFill = new ArrayList<>();
        hexagonFill.add(hexagon);

        // Цвет(красный)
        Scalar fillColor = new Scalar(0, 0, 255);

        // Закрашиваем шестиугольник
        Imgproc.fillPoly(wind, hexagonFill, fillColor);

        for (Point wallPoint : contour.toArray()) {
            double distance = Imgproc.pointPolygonTest(hexagon2f, new Point(wallPoint.x, wallPoint.y), true);
            if (distance >= -17) {
                return true; // Если хотя бы одна точка контура внутри шестиугольника
            }
        }
        return  false;
    }
    public boolean compareContour(List<MatOfPoint> nearestContours){
        if(lastContours.isEmpty()){
            lastContours = nearestContours;
            return false;
        }
        if(nearestContours.size() != StartApplication.lastContours.size()){
            lastContours = nearestContours;
            return false;
        }else{
            boolean areAreasEqual = true;
            int minSize = Math.min(nearestContours.size(), StartApplication.lastContours.size());

            for (int i = 0; i < minSize; i++) {
                MatOfPoint contour1 = nearestContours.get(i);
                MatOfPoint contour2 = StartApplication.lastContours.get(i);

                double area1 = Imgproc.contourArea(contour1);
                double area2 = Imgproc.contourArea(contour2);

                if (Math.abs(area1 - area2) > 0.1) { // You can adjust the threshold as needed
                    areAreasEqual = false;
                    break;
                }
            }
            if (areAreasEqual) {
                return true;
            } else {
                lastContours = nearestContours;
                return false;
            }
        }
    }


}
