package com.example.demo2.cases;

import com.example.demo2.logic.InitLogic;
import com.example.demo2.training.RobotContainer;
import com.example.demo2.training.StateMachine;

public class Transition implements IState{

    private InitLogic log = RobotContainer.init;

    @Override
    public boolean execute() {
        StateMachine.currentArray = log.indexMas.get(StateMachine.indexElementLogic)[0]+1;
//        StateMachineOMS.autonomousPositionLift = log.indexMas.get(StateMachine.indexElementLogic)[1];
        StateMachine.currentIndex=-1;
        // StateMachine.CurrentArray++;
        StateMachine.indexElementLogic++;
        return true;
    }
}
