package com.example.demo2.training;

public class StateMachine
{

    public static int currentArray = 0, currentindex = 0;
    public static float time = 0, startTime = 0;

    public static boolean isFirst = false;

    public void Update()
    {
        if (States.states[currentArray][currentindex].execute())
        {
            startTime = time;
            isFirst = true;
            currentindex ++;
        }
    }

    public void resetStateMachine()
    {
        currentArray = 0;
        currentindex = 0;
    }
}
