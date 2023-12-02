package com.example.demo2.cases;

import com.example.demo2.logic.InitLogic;
import com.example.demo2.training.RobotContainer;

public class InitializeLogic implements IState{

    public static InitLogic init = RobotContainer.init;
    @Override
    public boolean execute() {

        init.initLogic();

        RobotContainer.el.setAxisSpeed(0, 0, 0);

        return true;
    }
}
