package com.example.demo2.training;

public class Function
{

    public static float InRange(float in, float min, float max)
    {
        return in < min ? min : in > max ? max : in;
    }

    public static boolean InRangeBool(float in, float min, float max)
    {
        return in >= min && in <= max;
    }

    private static float[] speeds = new float[2];

    public static boolean lastElement = true;

    public static float TransF(float[][] array, float number)
    {
        float output = 0;
        float max_o;
        float max_i;
        float min_o;
        float min_i;

        for (int i = 0; i < array[0].length; i ++)
        {
            if (i < 1)
            {
                if (Math.abs(number) < array[0][i])
                {
                    if (number >= 0)
                    {
                        output = array[1][i];
                    }
                    else
                    {
                        output = -array[1][i];
                    }
                    break;
                }
            }
            else
            {
                if (Math.abs(number) >= array[0][i-1] && Math.abs(number) <= array[0][i])
                {
                    min_i = array[0][i - 1];
                    max_i =  array[0][i];
                    min_o = array[1][i - 1];
                    max_o =  array[1][i];

                    float v = min_o + (((max_o - min_o) * ((Math.abs(number) - min_i) * 100 / (max_i - min_i))) / 100);
                    if (number > 0)
                    {
                        output = v;
                        break;
                    }
                    else
                    {
                        output = -v;
                        break;
                    }
                }
                else
                {
                    if (number >=0)
                    {
                        output = array[1][i];
                    }
                    else
                    {
                        output = - array[1][i];

                    }
                }
            }
        }
        return output;
    }

    public static float[] ReImToPolar(float x, float y)
    {
        float[] arr = new float[2];
        arr[0] = (float)Math.sqrt((x * x + y * y));  //
        arr[1] = (float)Math.atan2(y, x);  // theta
        return arr;
    }

    public static float[] PolarToReIm(float r, float theta)
    {
        float[] arr = new float[2];
        arr[0] = r * (float)Math.cos(theta);  // x
        arr[1] = r * (float)Math.sin(theta);  // y
        return arr;
    }

    public static float[] smoothDrive(float x, float y, float cof)
    {

        speeds[0] += InRange(x - speeds[0], -cof, cof);

        speeds[1] += InRange(y - speeds[1], -cof, cof);

        return speeds;
    }

    public static int axis(float x, float y, float curX, float curY)
    {
        float xDist = 0;
        if (Math.abs(x - curX) != 0)
        {
            xDist = Math.abs(x - curX);
        }
        float yDist = Math.abs(y - curY);

        if (Function.InRangeBool(yDist / xDist, 0.75f, 1.38f) || (xDist < 150 && yDist < 150))
        {
            return 0;
        }
        else
        {
            if (yDist / xDist > 1)
            {
                return 1;
            }
            else
            {
                return 2;
            }

        }
    }

}
