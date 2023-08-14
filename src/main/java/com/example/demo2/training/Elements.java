package com.example.demo2.training;

public class Elements
{

    private static float speedX, speedY, speedZ = 0;

    public static float coordinatesX, coordinatesY, coordinatesZ = 0;

    public static float positionRobotX = 290, positionRobotY = 290, positionRobotZ = 0;
    public static float lastCoordinatesX, lastCoordinatesY, lastCoordinatesZ = 0;

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

    public void CalculateCoordinates()
    {
        coordinatesX += speedX * 4;
        coordinatesY += speedY * 4;
        coordinatesZ += speedZ;

//        System.out.println("coordinatesX: " + coordinatesX + " coordinatesY: " + coordinatesY + " coordinatesZ: " + coordinatesZ);
    }

    public void resetCoordinates(float x, float y, float z)
    {
        coordinatesX = x;
        coordinatesY = y;
        coordinatesZ = z;
    }

    public void changePosition()
    {
//        positionRobotX += coordinatesX / 4 - lastCoordinatesX;
//        lastCoordinatesX = coordinatesX / 4;
//
//        positionRobotY += coordinatesY / 4 - lastCoordinatesY;
//        lastCoordinatesY = coordinatesY / 4;

        positionRobotX += speedX;

        positionRobotY += speedY;

        positionRobotZ += speedZ;

//        System.out.println("positionRobotX: " + positionRobotX + " positionRobotY: " + positionRobotY + " positionRobotZ: " + positionRobotZ);
    }
}
