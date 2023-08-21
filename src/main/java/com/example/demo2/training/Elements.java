package com.example.demo2.training;

public class Elements
{

    private static float speedX, speedY, speedZ = 0;

    public static float coordinatesX, coordinatesY, coordinatesZ = 0;

    public static float positionRobotX = 290, positionRobotY = 290, positionRobotZ = 0;

    public static int fronteirLeft = 50, fronteirRight = 1043, fronteirUp = 70, fronteirDown = 570;

    public static int frontierX = 0, frontierY = 0;

    public static float disUp, disDown, disRight, disLeft = 0;

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

    public void changePosition()
    {

        positionRobotZ += speedZ;

        boolean zero = Function.InRangeBool(Math.abs(positionRobotZ), 0, 20) || Function.InRangeBool(Math.abs(positionRobotZ), 160, 200);
        boolean one = Function.InRangeBool(Math.abs(positionRobotZ), 70, 110) || Function.InRangeBool(Math.abs(positionRobotZ), 250, 290);

        frontierX = zero ? 105 : one ? 100 : 110;
        frontierY = zero ? 100 : one ? 105 : 110;

        positionRobotX = setPosition(positionRobotX, fronteirLeft, fronteirRight - frontierX, speedX);
        positionRobotY = setPosition(positionRobotY, fronteirUp, fronteirDown - frontierY, speedY);


    }

    public static float setPosition(float out, float min, float max, float speed)
    {
        boolean left = out <= min && speed < 0;
        boolean right = out >= max && speed > 0;

        out = left ? min : right ? max : out + speed;
        return out;
    }
}
