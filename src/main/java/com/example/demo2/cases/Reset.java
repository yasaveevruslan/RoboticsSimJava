package com.example.demo2.cases;

import com.example.demo2.training.Elements;
import com.example.demo2.training.RobotContainer;
import com.example.demo2.training.States;

public class Reset  implements IState{

    private String name;
    private float x, y, z;

    private Elements el = RobotContainer.el;
    @Override
    public boolean execute() {
        if (name.equals(States.ALL_RESET))
        {
            el.resetCoordinates(this.x, this.y, this.z);
        }
        else
        {
            el.resetGyro(this.z);
        }
        return true;
    }

    public Reset(String name, int x, int y, int z)
    {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
