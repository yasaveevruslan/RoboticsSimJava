package com.example.demo2.cases;

import com.example.demo2.training.MainController;
import com.example.demo2.training.RobotContainer;

public class Start implements IState
{

    @Override
    public boolean execute()
    {
        RobotContainer.el.resetCoordinates(0, 0, 0);
        RobotContainer.el.setAxisSpeed(0, 0, 0);
        return MainController.startClicked;
    }
}
