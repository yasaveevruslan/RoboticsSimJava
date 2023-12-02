package com.example.demo2.training;

public class Elements
{

    public static float speedX, speedY, speedZ = 0;

    public static float coordinatesX, coordinatesY, coordinatesZ = 0;

    public static float positionRobotX = 680, positionRobotY = 100, positionRobotZ = 0;

    public static int fronteirLeft = 45, fronteirRight = 1043, fronteirUp = 70, fronteirDown = 570;

    public static int limitLeft = 45, limitRight = 1043, limitUp = 70, limitDown = 570;
    public static float distanceLeft, distanceRight, distanceUp, distanceDown;



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

    }

    /*
    подсчет значений с датчиков
    */
    public void CalculateSensors()
    {
        MainController.irFront = (calculatePositionSensors(0) * 4) / 10;
        MainController.irBack = (calculatePositionSensors(0) * 4) / 10;
        MainController.usFront = (calculatePositionSensors(1) * 4) / 10;
        MainController.usRight = (calculatePositionSensors(2) * 4) / 10;
    }

    public static float calculatePositionSensors(int element)
    {

        int position = 0;
        float distance = 0;

        if  (   Function.InRangeBool(positionRobotZ, -45, 45) ||
                Function.InRangeBool(positionRobotZ, 315, 405) ||
                Function.InRangeBool(positionRobotZ, -405, -315)
            )
        {
            position = 0;
        }
        else if (   Function.InRangeBool(positionRobotZ, 45, 135) ||
                    Function.InRangeBool(positionRobotZ, -315, -225)
                )
        {
            position = 1;
        }
        else if (   Function.InRangeBool(positionRobotZ, 135, 225) ||
                    Function.InRangeBool(positionRobotZ, -225, -135)
                )
        {
            position = 2;
        }
        else if (   Function.InRangeBool(positionRobotZ, 225, 315) ||
                    Function.InRangeBool(positionRobotZ, -135, -45)
                )
        {
            position = 3;
        }



        if ((position + element) == 0 || (position + element) == 4)
        {
            distance = distanceUp;
        }
        else if ((position + element) == 1 || (position + element) == 5)
        {
            distance = distanceRight;
        } else if ((position + element) == 2 || (position + element) == 6)
        {
            distance = distanceDown;
        }
        else if ((position + element) == 3)
        {
            distance = distanceLeft;
        }
        return distance;
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

        positionRobotX = setPosition(positionRobotX, fronteirLeft, fronteirRight - frontierX, speedX);
        positionRobotY = setPosition(positionRobotY, fronteirUp, fronteirDown - frontierY, speedY);

//        positionRobotX = setPosition(positionRobotX, limitLeft, limitRight - frontierX, speedX);
//        positionRobotY = setPosition(positionRobotY, limitUp, limitDown - frontierY, speedY);
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
