package com.example.demo2.training;

import org.opencv.core.Mat;

public class Elements
{

    public static float speedX, speedY, speedZ = 0;

    public static float coordinatesX, coordinatesY, coordinatesZ = 0;

    public static float positionRobotX = 290, positionRobotY = 290, positionRobotZ = 0;

    public static int frontierLeft = 45, frontierRight = 1043, frontierUp = 70, frontierDown = 570;

    public static int limitLeft = 45, limitRight = 1043, limitUp = 70, limitDown = 570;
    public static float distanceLeft, distanceRight, distanceUp, distanceDown;

    public static float lastPositionX = 0, lastPositionY = 0;



    public static int frontierX = 0, frontierY = 0;

//    public static float disUp, disDown, disRight, disLeft = 0;


    /*
    подача скорости на перемещения робота
    */
    public void setAxisSpeed(float x, float y, float z)
    {
        float theta = (float)(Math.atan2(y, x) + Math.toRadians(positionRobotZ));
        float r = (float)(Math.sqrt(x * x + y * y));

        float newSpeedX = (float)(r * Math.cos(theta));
        float newSpeedY = (float)(r * Math.sin(theta));

        if (MainController.stopClicked || MainController.resetClicked)
        {
            speedX = 0;
            speedY = 0;
            speedZ = 0;
        }
        else
        {
            speedX = newSpeedX / 16;
            speedY = newSpeedY / 16;
            speedZ = z / 16;
        }

    }

    /*
    подсчет координат
    */
    public void CalculateCoordinates()
    {

        coordinatesX += speedX * 4;
        coordinatesY += speedY * 4;
        coordinatesZ += speedZ;

    }

    /*
    сброс координат
    */
    public void resetCoordinates(float x, float y, float z)
    {
        coordinatesX = x;
        coordinatesY = y;
        coordinatesZ = z;

    }

    public void resetGyro(float z)
    {
        coordinatesZ = z;
    }

    /*
    выделение контуров
    создание барьера поля
    */
    public void changePosition()
    {

        positionRobotZ += speedZ;

        frontierX = 105;
        frontierY = 105;

        positionRobotX = setPosition(positionRobotX, frontierLeft, frontierRight - frontierX, speedX);
        positionRobotY = setPosition(positionRobotY, frontierUp, frontierDown - frontierY, speedY);

//        positionRobotX = setPosition(positionRobotX, limitLeft, limitRight - frontierX, speedX);
//        positionRobotY = setPosition(positionRobotY, limitUp, limitDown - frontierY, speedY);

//        System.out.println(frontierLeft + " " + frontierRight + " " + frontierUp + " " + frontierDown);

    }

    /*
    проверка на вмещение робота в контуры (рамки поля)
    */
    public static float setPosition(float out, float min, float max, float speed)
    {
        boolean left = out <= min && speed < 0;
        boolean right = out >= max && speed > 0;

        out = left ? min : right ? max : out + speed;
        return out;
    }
}
