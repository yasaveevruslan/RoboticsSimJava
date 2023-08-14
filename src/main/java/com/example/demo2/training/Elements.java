package com.example.demo2.training;

public class Elements
{

    private static float speedX, speedY, speedZ = 0;

    public static float coordinatesX, coordinatesY, coordinatesZ = 0;

    public static float positionRobotX = 290, positionRobotY = 290, positionRobotZ = 0;

    public static int fronteirLeft = 50, fronteirRight = 1043, fronteirUp = 70, fronteirDown = 570;

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

    }

    public void resetCoordinates(float x, float y, float z)
    {
        coordinatesX = x;
        coordinatesY = y;
        coordinatesZ = z;
    }

    public void changePosition() {

        positionRobotZ += speedZ;

        int frontierX = 0;
        int frontierY = 0;

        if (Function.InRangeBool(Math.abs(positionRobotZ), 0, 20) || Function.InRangeBool(Math.abs(positionRobotZ), 160, 200))
        {
            frontierX = 105;
            frontierY = 100;
        }
        else if (Function.InRangeBool(Math.abs(positionRobotZ), 70, 110) || Function.InRangeBool(Math.abs(positionRobotZ), 250, 290))
        {
            frontierX = 100;
            frontierY = 105;
        }
        else
        {
            frontierX = 110;
            frontierY = 110;
        }

        if ((positionRobotX <= fronteirLeft && speedX < 0))
        {
            positionRobotX = fronteirLeft;
        }
        else if ((positionRobotX >= fronteirRight - frontierX && speedX > 0))
        {
            positionRobotX = fronteirRight - frontierX;
        }
        else
        {
            positionRobotX += speedX;
        }

        if ((positionRobotY <= fronteirUp && speedY < 0))
        {
            positionRobotY = fronteirUp;
        }
        else if (positionRobotY >= fronteirDown - frontierY && speedY > 0)
        {
            positionRobotY = fronteirDown - frontierY;
        }
        else
        {
            positionRobotY += speedY;
        }
    }
}
