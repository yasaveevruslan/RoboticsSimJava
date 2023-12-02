package com.example.demo2.training;

import com.example.demo2.logic.InitLogic;

public class RobotContainer {

    public static Elements el;
    public static InitLogic init;

    public RobotContainer(){
        el = new Elements();
        init = new InitLogic(StateMachine.commandLogic);
    }
}
