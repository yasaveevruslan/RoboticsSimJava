package com.example.demo2.cases;

import com.example.demo2.training.RobotContainer;

public class End implements IState
{

    @Override
    public boolean execute()
    {
        RobotContainer.el.setAxisSpeed(0, 0, 0);
        return false;
    }
}
