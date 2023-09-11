package com.example.demo2.training;

import com.example.demo2.cases.*;

public class States {

    public static final String ABSOLUTE_ODOMETRY = "absolute";

    public static final String USE_FRONT_SHARP = "front";
    public static final String USE_BACK_SHARP = "back";
    public static final String USE_RIGHT_SONIC = "right";
    public static final String DEFAULT_SENSOR = "default";

    public static IState[][] states = new IState[][]{
            {
                    new Start(),

                    new Sensors(15, 15, States.USE_FRONT_SHARP, 0, SmoothEnum.OFF),
                    new Sensors(0, 15, States.USE_RIGHT_SONIC, 0, SmoothEnum.OFF),
                    new Sensors(15, 15, States.USE_RIGHT_SONIC, 0, SmoothEnum.OFF),

//                    new Odometry(States.ABSOLUTE_ODOMETRY, 4000, 0, 45, SmoothEnum.OFF),

//                    new Odometry(States.ABSOLUTE_ODOMETRY, 4000, 0, 90, SmoothEnum.OFF),
//                    new Odometry(States.ABSOLUTE_ODOMETRY, 3500, 1000, 180, SmoothEnum.OFF),
//                    new Odometry(States.ABSOLUTE_ODOMETRY, 0, 1000, -90, SmoothEnum.OFF),
//                    new Odometry(States.ABSOLUTE_ODOMETRY, 0, -1000, 0, SmoothEnum.OFF),
//                    new Odometry(States.ABSOLUTE_ODOMETRY, 5000, -1000, 0, SmoothEnum.OFF),

                    new End(),
            }
        };
}
