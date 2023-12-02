package com.example.demo2.training;

public class StateMachine
{

    public static int currentArray = 0, currentIndex = 0;
    public static float time = 0, startTime = 0;

    public static boolean isFirst = false;

    public static String commandLogic = "E";
    public static int indexElementLogic = 0;

    public void Update()
    {
        if (States.main[currentArray][currentIndex].execute())
        {
            startTime = time;
            isFirst = true;
            currentIndex ++;
        }
    }

    public void resetStateMachine()
    {
        currentArray = 0;
        currentIndex = 0;
    }
}
