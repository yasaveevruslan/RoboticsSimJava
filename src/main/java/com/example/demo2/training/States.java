package com.example.demo2.training;

import com.example.demo2.cases.*;

public class States {

    public static final String ABSOLUTE_ODOMETRY = "absolute";

    public static IState[][] states = new IState[][]{
            {
                    new Start(),
                    new Odometry(States.ABSOLUTE_ODOMETRY, 1000, 0, 90, SmoothEnum.OFF),
                    new Odometry(States.ABSOLUTE_ODOMETRY, 1000, 1000, 180, SmoothEnum.OFF),
                    new Odometry(States.ABSOLUTE_ODOMETRY, 0, 1000, -90, SmoothEnum.OFF),
                    new Odometry(States.ABSOLUTE_ODOMETRY, 0, 0, 0, SmoothEnum.OFF),
                    new End(),
            }
    };
}
