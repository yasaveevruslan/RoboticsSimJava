package com.example.demo2.training;

public class Elements
{

    private static float speedX, speedY, speedZ = 0;

    public static float coordinatesX, coordinatesY, coordinatesZ = 0;

    public static float positionRobotX = 290, positionRobotY = 290, positionRobotZ = 0;

    public static int fronteirLeft = 45, fronteirRight = 1043, fronteirUp = 70, fronteirDown = 570;

    public static int limitLeft = 45, limitRight = 1043, limitUp = 70, limitDown = 570;
    public static float distanceLeft, distanceRight, distanceUp, distanceDown;

    public static float irFront, irBack, usFront, usRight;

    public static int frontierX = 0, frontierY = 0;

//    public static float disUp, disDown, disRight, disLeft = 0;


    /*
    подача скорости на перемещения робота
    */
    public void setAxisSpeed(float x, float y, float z)
    {
        if (MainController.stopClicked || MainController.resetClicked)
        {
            speedX = 0;
            speedY = 0;
            speedZ = 0;
        }
        else
        {
            speedX = x / 16;
            speedY = y / 16;
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

        System.out.println(limitUp + " " + limitDown + " " + limitLeft + " " + limitRight);
    }

    /*
    подсчет значений с датчиков
    */
    public void CalculateSensors()
    {
        irFront = distanceUp * 4;
        irBack = distanceUp * 4;
        usFront = distanceRight * 4;
        usRight = distanceDown * 4;
    }

    public static float calculatePositionSensors()
    {
       return 0;
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

    /*
    выделение контуров
    создание барьера поля
    */
    public void changePosition()
    {

        positionRobotZ += speedZ;

        frontierX = 105;
        frontierY = 105;

//        positionRobotX = setPosition(positionRobotX, fronteirLeft, fronteirRight - frontierX, speedX);
//        positionRobotY = setPosition(positionRobotY, fronteirUp, fronteirDown - frontierY, speedY);

        positionRobotX = setPosition(positionRobotX, limitLeft, limitRight - frontierX, speedX);
        positionRobotY = setPosition(positionRobotY, limitUp, limitDown - frontierY, speedY);
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
