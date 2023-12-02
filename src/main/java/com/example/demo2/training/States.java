package com.example.demo2.training;

import com.example.demo2.cases.*;

public class States {

    public static final String ABSOLUTE_ODOMETRY = "absolute";
    public static final String RELATIVE_ODOMETRY = "relative";

    public static final String USE_FRONT_SHARP = "front";
    public static final String USE_BACK_SHARP = "back";
    public static final String USE_RIGHT_SONIC = "right";
    public static final String DEFAULT_SENSOR = "default";


    public static final String CAMERA_YELLOW = "Yellow";
    public static final String CAMERA_BLUE = "Blue";
    public static final String CAMERA_WHITE = "White";
    public static final String CAMERA_QR = "QR";
    public static final String CAMERA_QRC = "QRC";

    public static final String CAMERA_GREEN_CUBE = "GREEN";
    public static final String CAMERA_YELLOW_CUBE = "YELLOW";
    public static final String CAMERA_OBJECTS = "objects";
    public static final String CAMERA_STANDS = "stands";

    public static final String CAMERA_TOTAL_COLORS_F = "totalColorF";


    public static final String CAMERA_COLOR_STANDS_F = "colorStandsF";
    public static final String CAMERA_COLOR_STANDS = "colorStands";

    public static final String ALL_RESET = "all";
    public static final String GYRO_RESET = "gyro";

    public static final String C_SONIC = "sonic";
    public static final String C_SHARP = "sharp";

    public static final String SEARCH_BLACK_LINE = "search";
    public static final String POSITION_BLACK_LINE = "position";
    public static final String RESET_BLACK_LINE = "reset";
    public static final String DEFAULT_BLACK_LINE = "default";


    public static final int INIT_OMS = 0;
    public static final int TAKE_CUBE_FROM_STAND = 1;
    public static final int PUT_CUBE_FULL = 2;
    public static final int WAREHOUSE_OMS = 3;
    public static final int MIDDLE_CLOSE_GRAB = 4;
    public static final int CLOSE_FULL_GRAB = 5;
    public static final int DOWNLOAD_IN_ROBOT = 6;
    public static final int CLEAN_CUBE = 7;
    public static final int TAKE_CUBE_IN_ROBOT = 8;
    public static final int PUT_CUBE_IN_STAND = 9;
    public static final int OMS_CAMERA_DOWN = 10;
    public static final int OMS_CAMERA_QR = 11;
    public static final int END_SCAN = 12;
    public static final int CLEAN_CUBE_FROM_ROBOT = 13;
    public static final int CUBE_OUT_FROM_SCAN = 14;
    public static final int START_CUBE_OUT_FROM_SCAN = 15;
    public static final int INIT_LIFT_AFTER_CAMERA = 16;

    public static IState[][] main = new IState[][]
            {
                    {
                            new Start(),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 1000, 0, 90, SmoothEnum.OFF),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 1000, 1000, 180, SmoothEnum.OFF),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 0, 1000, -90, SmoothEnum.OFF),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 0, 0, 0, SmoothEnum.OFF),
                            new End(),
//                            new InitializeLogic(),
//                            new Transition(),

                    },

                    // {
                    //     new Start(),
                    //     // new Sensors(13f, 15f, States.USE_FRONT_SHARP, 1, SmoothEnum.OFF),
                    //     // new Reset(States.ALL_RESET, 0, 0, 0),
                    //     // new Odometry(States.ABSOLUTE_ODOMETRY, 0, 560, 0, SmoothEnum.ACCELERATION),
                    //     // new Odometry(States.ABSOLUTE_ODOMETRY, 690, 560, -90, SmoothEnum.FAST),
                    //     // new Odometry(States.ABSOLUTE_ODOMETRY, 690, 0, -90, SmoothEnum.FAST),
                    //     // new Sensors(22f, 0f, States.DEFAULT_SENSOR, 0, SmoothEnum.FAST),
                    //     // new Sensors(27.7f, 20f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),

                    //     // new Odometry(States.RELATIVE_ODOMETRY, 0, 94, 0, SmoothEnum.OFF),
                    //     // new OMS(States.OMS_CAMERA_DOWN),
                    //     // new Camera(States.CAMERA_GREEN_CUBE),
                    //     // new OMS(States.OMS_CAMERA_QR),
                    //     // new Odometry(States.RELATIVE_ODOMETRY, 26, 0, 0, SmoothEnum.OFF),
                    //     // new Camera(States.CAMERA_QR),
                    //     // new Odometry(States.RELATIVE_ODOMETRY, -30, 0, 0, SmoothEnum.OFF),
                    //     // new OMS(States.OMS_CAMERA_DOWN),
                    //     // new Sensors(27.7f, 20f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),
                    //     // new Camera(States.CAMERA_YELLOW_CUBE),

                    //     // new ResetLogic(),
                    //     // new InitializeLogic(),

                    //     new OMS(States.OMS_CAMERA_DOWN),
                    //     new Camera(States.CAMERA_TOTAL_COLORS_F),
                    //     new OMS(States.OMS_CAMERA_QR),
                    //     new Camera(States.CAMERA_QR),

                    //     new Transition(),
                    // },

                    { //0
                            new Sensors(15f, 15f, States.USE_FRONT_SHARP, 1, SmoothEnum.OFF),
                            new Reset(States.ALL_RESET, 0, 0, 0),
                            new Odometry(States.ABSOLUTE_ODOMETRY, -900, 0, -90, SmoothEnum.ACCELERATION),
                            new Odometry(States.ABSOLUTE_ODOMETRY, -900, 1200, -180, SmoothEnum.FAST),
                            new Sensors(0f, 15f, States.USE_FRONT_SHARP, 1, SmoothEnum.FAST),
                            new Sensors(28.5f, 10f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),
                            new Sensors(0, 20, States.USE_RIGHT_SONIC, 0, SmoothEnum.DECELERATION),
                            new Odometry(States.RELATIVE_ODOMETRY, 0, 0, 90, SmoothEnum.OFF),
                            new Sensors(15, 15, States.USE_FRONT_SHARP, 1, SmoothEnum.OFF),
                            new End(),
//                            new Odometry(States.ABSOLUTE_ODOMETRY, 0, 560, 0, SmoothEnum.ACCELERATION),
//                            new Odometry(States.ABSOLUTE_ODOMETRY, 690, 560, -90, SmoothEnum.FAST),
//                            new Odometry(States.ABSOLUTE_ODOMETRY, 690, 0, -90, SmoothEnum.FAST),
//                            new Sensors(22f, 0f, States.DEFAULT_SENSOR, 0, SmoothEnum.FAST),
//                            new Sensors(20.6f, 20f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),

                            new Transition(),
                    },

                    { //1
                            new BlackLineSensor(States.RESET_BLACK_LINE, 0, 0),
                            new Odometry(States.ABSOLUTE_ODOMETRY, -19, -676, 270, SmoothEnum.OFF),
                            new Sensors(17f, 0, States.DEFAULT_SENSOR, 1, SmoothEnum.OFF),
                            new BlackLineSensor(States.POSITION_BLACK_LINE, 0, 2),
                            new Odometry(States.RELATIVE_ODOMETRY, -20, 0, 0, SmoothEnum.OFF),

                            new Transition(),
                    },
                    { //2
                            new BlackLineSensor(States.RESET_BLACK_LINE, 0, 0),
                            new Odometry(States.ABSOLUTE_ODOMETRY, -19, -676, 270, SmoothEnum.OFF),
                            new Sensors(17f, 0, States.DEFAULT_SENSOR, 1, SmoothEnum.OFF),
                            new BlackLineSensor(States.POSITION_BLACK_LINE, 0, 1),
                            new Odometry(States.RELATIVE_ODOMETRY, -20, 0, 0, SmoothEnum.OFF),

                            new Transition(),
                    },

                    { //3
                            new Sensors(20.6f, 16F, States.USE_FRONT_SHARP, 1, SmoothEnum.OFF),
                            new Reset(States.ALL_RESET, -19, -676, 270),

                            new Transition(),
                    },
                    { //4
                            new Reset(States.GYRO_RESET, 0, 0, -90),
                            new Sensors(21f, 18, States.USE_FRONT_SHARP, 1, SmoothEnum.OFF),
                            new Sensors(20.6f, 20, States.USE_RIGHT_SONIC, 0, SmoothEnum.DECELERATION),

                            new Transition(),
                    },
                    { //5

                            new Odometry(States.ABSOLUTE_ODOMETRY, 700, -659, 90, SmoothEnum.ACCELERATION),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 700, 0, 90, SmoothEnum.ACCELERATION),
                            new Sensors(15f, 0f, States.DEFAULT_SENSOR, 0, SmoothEnum.FAST),

                            new Sensors(17f, 30, States.USE_FRONT_SHARP, 1, SmoothEnum.FAST),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 2350, -500, 270, SmoothEnum.FAST),
                            new Sensors(18, 0, States.DEFAULT_SENSOR, 0, SmoothEnum.FAST),
                            new Sensors(18f, 20, States.USE_BACK_SHARP, 1, SmoothEnum.DECELERATION),


                            new Transition(),
                    },
                    { //6
                            new Odometry(States.ABSOLUTE_ODOMETRY, 700, -659, 90, SmoothEnum.ACCELERATION),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 700, 0, 90, SmoothEnum.ACCELERATION),
                            new Sensors(15f, 0f, States.DEFAULT_SENSOR, 0, SmoothEnum.FAST),

                            new Sensors(17f, 30, States.USE_FRONT_SHARP, 1, SmoothEnum.FAST),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 2350, 130, 180, SmoothEnum.DECELERATION),
                            new Sensors(18, 0, States.DEFAULT_SENSOR, 0, SmoothEnum.ACCELERATION),
                            new Sensors(20, 20, States.USE_BACK_SHARP, 1, SmoothEnum.DECELERATION),

                            new Transition(),

                    },
                    { //7

                            new Odometry(States.ABSOLUTE_ODOMETRY, 730, -659, 180, SmoothEnum.ACCELERATION),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 730, 0, 180, SmoothEnum.FAST),
                            new Sensors(0f, 15f, States.USE_FRONT_SHARP, 1, SmoothEnum.FAST),
                            new Sensors(30f, 15f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),
                            new Transition(),
                    },
                    {//8

                            new Odometry(States.ABSOLUTE_ODOMETRY, 730, -659, 180, SmoothEnum.ACCELERATION),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 730, 0, 180, SmoothEnum.FAST),
                            new Sensors(0f, 15f, States.USE_FRONT_SHARP, 1, SmoothEnum.FAST),
                            new Sensors(28.5f, 10f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),

                            new Transition(),
                    },
                    { //9
                            new Odometry(States.ABSOLUTE_ODOMETRY, 730, -659, 180, SmoothEnum.ACCELERATION),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 730, 0, 180, SmoothEnum.FAST),
                            new Sensors(30.5f, 10f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),
                            new Odometry(States.RELATIVE_ODOMETRY, -725, 0, 0, SmoothEnum.OFF),
                            new Sensors(0, 15f, States.USE_RIGHT_SONIC, 0, SmoothEnum.DECELERATION),

                            new End(),

                    },
                    { //10

                            new Sensors(13f, 15f, States.USE_FRONT_SHARP, 1, SmoothEnum.OFF),
                            new Reset(States.ALL_RESET, 0, 0, 0),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 0, 560, 0, SmoothEnum.ACCELERATION),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 690, 560, 0, SmoothEnum.FAST),
                            new Sensors(0f, 16f, States.USE_FRONT_SHARP, 0, SmoothEnum.FAST),
                            new Sensors(20.6f, 20f, States.USE_FRONT_SHARP, 1, SmoothEnum.FAST),
                            new Sensors(18, 20f, States.USE_FRONT_SHARP, 1, SmoothEnum.OFF),
                            new Odometry(States.RELATIVE_ODOMETRY, 0, 50, -90, SmoothEnum.OFF),

                            new Transition(),

                    },
                    { //11
                            new BlackLineSensor(States.RESET_BLACK_LINE, 0, 0),

                            new Odometry(States.ABSOLUTE_ODOMETRY, 1684, -598, -90, SmoothEnum.OFF),
                            new Odometry(States.RELATIVE_ODOMETRY, 0, -40, 0, SmoothEnum.OFF),

                            new Sensors(16.8f, 0, States.DEFAULT_SENSOR, 1, SmoothEnum.OFF),
                            // new Sensors(0, 23.9f, States.USE_RIGHT_SONIC, 0, SmoothEnum.OFF),
                            new BlackLineSensor(States.POSITION_BLACK_LINE, 0, 2),
                            new Odometry(States.RELATIVE_ODOMETRY, -24, 0, 0, SmoothEnum.OFF),

                            new Transition(),
                    },
                    { //12
                            new BlackLineSensor(States.RESET_BLACK_LINE, 0, 0),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 1684, -598, -90, SmoothEnum.OFF),
                            new Odometry(States.RELATIVE_ODOMETRY, 0, -40, 0, SmoothEnum.OFF),

                            new Sensors(17f, 0, States.DEFAULT_SENSOR, 1, SmoothEnum.OFF),
                            new BlackLineSensor(States.POSITION_BLACK_LINE, 0, 1),
                            new Odometry(States.RELATIVE_ODOMETRY, -24, 0, 0, SmoothEnum.OFF),

                            new Transition(),
                    },
                    { //13
                            new Sensors(20.3f, 36, States.USE_RIGHT_SONIC, 0, SmoothEnum.OFF),
                            new Reset(States.ALL_RESET, 1684, -598, -90),

                            new Transition(),
                    },
                    { //14
                            new Sensors(20.6f, 20f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),
                            new Transition(),

                    },

                    { //15
                            new Odometry(States.ABSOLUTE_ODOMETRY, 820, -659, 90, SmoothEnum.ACCELERATION),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 780, 0, 90, SmoothEnum.ACCELERATION),
                            new Sensors(15f, 0f, States.DEFAULT_SENSOR, 0, SmoothEnum.FAST),

                            new Sensors(17f, 30, States.USE_FRONT_SHARP, 1, SmoothEnum.FAST),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 2440, -500, 270, SmoothEnum.FAST),
                            new Sensors(25, 0, States.DEFAULT_SENSOR, 0, SmoothEnum.DECELERATION),
                            new Sensors(20, 20, States.USE_BACK_SHARP, 1, SmoothEnum.DECELERATION),

                            new Transition(),
                    },
                    { //16
                            new Odometry(States.ABSOLUTE_ODOMETRY, 780, -659, 90, SmoothEnum.ACCELERATION),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 780, 0, 90, SmoothEnum.ACCELERATION),
                            new Sensors(15f, 0f, States.DEFAULT_SENSOR, 0, SmoothEnum.FAST),

                            new Sensors(17f, 30, States.USE_FRONT_SHARP, 1, SmoothEnum.FAST),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 2350, 130, 180, SmoothEnum.DECELERATION),
                            new Sensors(18, 0, States.DEFAULT_SENSOR, 0, SmoothEnum.ACCELERATION),
                            new Sensors(20, 20, States.USE_BACK_SHARP, 1, SmoothEnum.DECELERATION),

                            new Transition(),
                    },
                    { //17
                            new Odometry(States.ABSOLUTE_ODOMETRY, 770, -700, -90, SmoothEnum.ACCELERATION),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 730, 0, -180, SmoothEnum.FAST),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 730, 400, -180, SmoothEnum.FAST),

                            new Sensors(0f, 15f, States.USE_FRONT_SHARP, 1, SmoothEnum.FAST),
                            new Sensors(30f, 15f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),

                            new Transition(),
                    },
                    { //18
                            new Odometry(States.ABSOLUTE_ODOMETRY, 770, -700, -90, SmoothEnum.ACCELERATION),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 730, 0, -180, SmoothEnum.FAST),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 730, 400, -180, SmoothEnum.FAST),

                            new Sensors(0f, 15f, States.USE_FRONT_SHARP, 1, SmoothEnum.FAST),
                            new Sensors(28.5f, 10.5f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),

                            new Transition(),
                    },
                    { //19
                            new Odometry(States.ABSOLUTE_ODOMETRY, 770, -700, -90, SmoothEnum.ACCELERATION),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 730, 0, -180, SmoothEnum.FAST),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 730, 400, -180, SmoothEnum.FAST),

                            new Sensors(30.5f, 10f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),
                            new Odometry(States.RELATIVE_ODOMETRY, -725, 0, 0, SmoothEnum.OFF),
                            new Sensors(0, 15f, States.USE_RIGHT_SONIC, 0, SmoothEnum.DECELERATION),

                            new End(),

                    },
                    { //20
                            new Sensors(13f, 15f, States.USE_FRONT_SHARP, 1, SmoothEnum.OFF),
                            new Reset(States.ALL_RESET, 0, 0, 0),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 0, 560, 90, SmoothEnum.ACCELERATION),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 500, 560, 90, SmoothEnum.FAST),

                            new Sensors(17f, 30, States.USE_FRONT_SHARP, 1, SmoothEnum.FAST),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 2350, -500, 270, SmoothEnum.FAST),
                            new Sensors(15f, 0, States.DEFAULT_SENSOR, 0, SmoothEnum.FAST),
                            new Sensors(18f, 20, States.USE_BACK_SHARP, 1, SmoothEnum.DECELERATION),


                            new Transition(),
                    },

                    { //21
                            new BlackLineSensor(States.RESET_BLACK_LINE, 0, 0),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 2375, -670, -90, SmoothEnum.OFF),
                            new Odometry(States.RELATIVE_ODOMETRY, 0, -40, 0, SmoothEnum.OFF),

                            new Sensors(17f, 0, States.DEFAULT_SENSOR, 1, SmoothEnum.OFF),

                            // new Sensors(19.4f, 0, States.DEFAULT_SENSOR, 1, SmoothEnum.OFF),
                            // new Odometry(States.RELATIVE_ODOMETRY, 0, 130, 0, SmoothEnum.OFF),
                            new BlackLineSensor(States.POSITION_BLACK_LINE, 0, 2),
                            new Odometry(States.RELATIVE_ODOMETRY, -24, 0, 0, SmoothEnum.OFF),

                            new Transition(),
                    },
                    { //22

                            new BlackLineSensor(States.RESET_BLACK_LINE, 0, 0),

                            new Odometry(States.ABSOLUTE_ODOMETRY, 2375, -670, -90, SmoothEnum.OFF),
                            new Odometry(States.RELATIVE_ODOMETRY, 0, -40, 0, SmoothEnum.OFF),
                            new Sensors(17f, 0, States.DEFAULT_SENSOR, 1, SmoothEnum.OFF),


                            // new Sensors(19.4f, 0, States.DEFAULT_SENSOR, 1, SmoothEnum.OFF),
                            new BlackLineSensor(States.POSITION_BLACK_LINE, 0, 1),
                            new Odometry(States.RELATIVE_ODOMETRY, -24, 0, 0, SmoothEnum.OFF),
                            new Transition(),

                    },
                    { //23
                            new Sensors(20f, 18, States.USE_FRONT_SHARP, 1, SmoothEnum.OFF),
                            new Reset(States.ALL_RESET, 2375, -670, -90),
                            new Transition(),
                    },
                    { //24
                            new Sensors(20, 30f, States.USE_RIGHT_SONIC, 0, SmoothEnum.ACCELERATION),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 2375, 300, -180, SmoothEnum.FAST),

                            new Sensors(0, 15f, States.USE_FRONT_SHARP, 1, SmoothEnum.FAST),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 720, 620, -90, SmoothEnum.FAST),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 700, 0, -90, SmoothEnum.FAST),
                            new Sensors(22f, 0f, States.DEFAULT_SENSOR, 0, SmoothEnum.FAST),
                            new Sensors(20.6f, 20f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),


                            new Transition(),

                    },
                    { //25
                            new Sensors(20, 30f, States.USE_RIGHT_SONIC, 0, SmoothEnum.ACCELERATION),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 2375, 300, -180, SmoothEnum.FAST),

                            new Sensors(0, 17f, States.USE_FRONT_SHARP, 1, SmoothEnum.FAST),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 720, 620, -90, SmoothEnum.FAST),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 700, 0, -90, SmoothEnum.FAST),
                            new Sensors(22f, 0f, States.DEFAULT_SENSOR, 0, SmoothEnum.FAST),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 750, -740, 0, SmoothEnum.DECELERATION),
                            new Sensors(20.6f, 20f, States.USE_FRONT_SHARP, 0, SmoothEnum.DECELERATION),
                            new Sensors(18, 20f, States.USE_FRONT_SHARP, 1, SmoothEnum.OFF),
                            new Odometry(States.RELATIVE_ODOMETRY, 0, 50, -90, SmoothEnum.OFF),

                            new Transition(),


                    },
                    { //26

                            new Odometry(States.ABSOLUTE_ODOMETRY, 2480, -70, -180, SmoothEnum.DECELERATION),
                            new Sensors(18, 0, States.DEFAULT_SENSOR, 0, SmoothEnum.ACCELERATION),
                            new Sensors(20, 20, States.USE_BACK_SHARP, 1, SmoothEnum.DECELERATION),

                            new Transition(),
                    },
                    { //27
                            new Sensors(20, 30f, States.USE_RIGHT_SONIC, 0, SmoothEnum.ACCELERATION),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 2375, 300, -180, SmoothEnum.FAST),

                            new Sensors(0, 15f, States.USE_FRONT_SHARP, 1, SmoothEnum.FAST),

                            new Sensors(30f, 15f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),
                            new Transition(),
                    },
                    { //28
                            new Sensors(20, 30f, States.USE_RIGHT_SONIC, 0, SmoothEnum.ACCELERATION),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 2375, 300, -180, SmoothEnum.FAST),

                            new Sensors(0, 15f, States.USE_FRONT_SHARP, 1, SmoothEnum.FAST),
                            new Sensors(28.5f, 10.5f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),

                            new Transition(),
                    },
                    { //29
                            new Sensors(20, 30f, States.USE_RIGHT_SONIC, 0, SmoothEnum.ACCELERATION),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 2375, 300, -180, SmoothEnum.FAST),

                            new Sensors(0, 15f, States.USE_FRONT_SHARP, 1, SmoothEnum.FAST),
                            new Sensors(30.5f, 10f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),
                            new Odometry(States.RELATIVE_ODOMETRY, -725, 0, 0, SmoothEnum.OFF),
                            new Sensors(0, 15f, States.USE_RIGHT_SONIC, 0, SmoothEnum.DECELERATION),

                            new End(),


                    },
                    { //30
                            new Sensors(13f, 15f, States.USE_FRONT_SHARP, 1, SmoothEnum.OFF),
                            new Reset(States.ALL_RESET, 0, 0, 0),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 0, 560, 90, SmoothEnum.ACCELERATION),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 500, 560, 90, SmoothEnum.FAST),

                            new Sensors(17f, 30, States.USE_FRONT_SHARP, 1, SmoothEnum.FAST),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 2350, 130, 180, SmoothEnum.DECELERATION),
                            new Sensors(18, 0, States.DEFAULT_SENSOR, 0, SmoothEnum.ACCELERATION),
                            new Sensors(20, 20, States.USE_BACK_SHARP, 1, SmoothEnum.DECELERATION),

                            new Transition(),

                    },
                    { //31
                            new BlackLineSensor(States.RESET_BLACK_LINE, 0, 0),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 1565, 98, 180, SmoothEnum.OFF),
                            new Odometry(States.RELATIVE_ODOMETRY, 0, -40, 0, SmoothEnum.OFF),

                            new Sensors(17f, 0, States.DEFAULT_SENSOR, 1, SmoothEnum.OFF),
                            new BlackLineSensor(States.POSITION_BLACK_LINE, 0, 1),
                            new Odometry(States.RELATIVE_ODOMETRY, -24, 0, 0, SmoothEnum.OFF),


                            new Transition(),
                    },
                    { //32
                            new BlackLineSensor(States.RESET_BLACK_LINE, 0, 0),

                            new Odometry(States.ABSOLUTE_ODOMETRY, 1565, 98, 180, SmoothEnum.OFF),
                            new Odometry(States.RELATIVE_ODOMETRY, 0, -40, 0, SmoothEnum.OFF),

                            new Sensors(17f, 0, States.DEFAULT_SENSOR, 1, SmoothEnum.OFF),
                            // new Sensors(0, 23.9f, States.USE_RIGHT_SONIC, 0, SmoothEnum.OFF),
                            new BlackLineSensor(States.POSITION_BLACK_LINE, 0, 2),
                            new Odometry(States.RELATIVE_ODOMETRY, -24, 0, 0, SmoothEnum.OFF),



                            // new Sensors(0, 21f, States.USE_RIGHT_SONIC, 1, SmoothEnum.DECELERATION),
                            new Transition(),
                    },
                    { //33
                            new Sensors(18.6f, 16, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),
                            new Reset(States.ALL_RESET, 1565, 98, 180),
                            new Transition(),
                    },
                    { //34
                            new Odometry(States.ABSOLUTE_ODOMETRY, 2400, 70, 180, SmoothEnum.DECELERATION),
                            new Sensors(0, 16, States.USE_FRONT_SHARP, 1, SmoothEnum.FAST),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 720, 750, 270, SmoothEnum.FAST),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 700, 0, 270, SmoothEnum.FAST),
                            new Sensors(22f, 0f, States.DEFAULT_SENSOR, 0, SmoothEnum.FAST),
                            new Sensors(20.6f, 20f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),

                            new Transition(),

                    },
                    { //35
                            new Odometry(States.ABSOLUTE_ODOMETRY, 2400, 70, 180, SmoothEnum.DECELERATION),
                            new Sensors(0, 16, States.USE_FRONT_SHARP, 1, SmoothEnum.FAST),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 720, 750, 270, SmoothEnum.FAST),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 700, 0, 270, SmoothEnum.FAST),
                            new Sensors(22f, 0f, States.DEFAULT_SENSOR, 0, SmoothEnum.FAST),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 750, -740, 360, SmoothEnum.DECELERATION),
                            new Sensors(20.6f, 20f, States.USE_FRONT_SHARP, 1, SmoothEnum.FAST),
                            new Sensors(18, 20f, States.USE_FRONT_SHARP, 1, SmoothEnum.OFF),
                            new Odometry(States.RELATIVE_ODOMETRY, 0, 50, -90, SmoothEnum.OFF),

                            new Transition(),
                    },
                    { //36
                            new Odometry(States.ABSOLUTE_ODOMETRY, 2300, 70, 270, SmoothEnum.DECELERATION),

                            new Sensors(15f, 0, States.DEFAULT_SENSOR, 0, SmoothEnum.FAST),
                            new Sensors(18f, 20, States.USE_BACK_SHARP, 1, SmoothEnum.DECELERATION),

                            new Transition(),
                    },
                    { //37

                            new Odometry(States.ABSOLUTE_ODOMETRY, 2300, 70, 180, SmoothEnum.ACCELERATION),

                            new Sensors(0f, 15f, States.USE_FRONT_SHARP, 1, SmoothEnum.FAST),
                            new Sensors(30f, 15f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),
                            new Transition(),
                    },
                    { //38

                            new Odometry(States.ABSOLUTE_ODOMETRY, 2300, 70, 180, SmoothEnum.ACCELERATION),

                            new Sensors(0f, 15f, States.USE_FRONT_SHARP, 1, SmoothEnum.FAST),
                            new Sensors(28.5f, 10.5f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),
                            new Transition(),
                    },
                    { //39
                            new Odometry(States.ABSOLUTE_ODOMETRY, 2300, 70, 180, SmoothEnum.ACCELERATION),
                            new Sensors(30.5f, 10f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),
                            new Odometry(States.RELATIVE_ODOMETRY, -725, 0, 0, SmoothEnum.OFF),
                            new Sensors(0, 15f, States.USE_RIGHT_SONIC, 0, SmoothEnum.DECELERATION),
                            new End(),
                    },
                    { //40
                            new Sensors(30.5f, 8.5f, States.USE_FRONT_SHARP, 1, SmoothEnum.ACCELERATION),

                            new Transition(),
                    },
                    { //41
                            new Sensors(26f, 22f, States.USE_FRONT_SHARP, 1, SmoothEnum.ACCELERATION),
                            new Sensors(30.5f, 9f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),
                            new Reset(States.ALL_RESET, -750, 650, 180),

                            new Odometry(States.ABSOLUTE_ODOMETRY, 635, 560, 270, SmoothEnum.ACCELERATION),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 635, 0, 270, SmoothEnum.FAST),

                            new Sensors(22f, 0f, States.DEFAULT_SENSOR, 0, SmoothEnum.FAST),
                            new Sensors(20.6f, 20f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),

                            new Transition(),
                    },
                    { //42
                            new Sensors(26f, 22f, States.USE_FRONT_SHARP, 1, SmoothEnum.ACCELERATION),
                            new Sensors(30.5f, 9f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),
                            new Reset(States.ALL_RESET, -750, 650, 180),

                            new Odometry(States.ABSOLUTE_ODOMETRY, 645, 560, 0, SmoothEnum.ACCELERATION),
                            new Sensors(0f, 16f, States.USE_FRONT_SHARP, 0, SmoothEnum.FAST),
                            new Sensors(20.6f, 20f, States.USE_FRONT_SHARP, 1, SmoothEnum.FAST),
                            new Sensors(18, 20f, States.USE_FRONT_SHARP, 1, SmoothEnum.OFF),
                            new Odometry(States.RELATIVE_ODOMETRY, 0, 50, -90, SmoothEnum.OFF),


                            new Transition(),
                    },
                    { //43
                            new Sensors(26f, 22f, States.USE_FRONT_SHARP, 1, SmoothEnum.ACCELERATION),
                            new Sensors(30.5f, 9f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),

                            new Reset(States.ALL_RESET, -750, 650, 180),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 645, 560, 90, SmoothEnum.ACCELERATION),

                            new Sensors(17f, 30, States.USE_FRONT_SHARP, 1, SmoothEnum.FAST),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 2350, -500, 270, SmoothEnum.FAST),
                            new Sensors(15f, 0, States.DEFAULT_SENSOR, 0, SmoothEnum.FAST),
                            new Sensors(18f, 20, States.USE_BACK_SHARP, 1, SmoothEnum.DECELERATION),


                            new Transition(),
                    },
                    { //44
                            new Sensors(26f, 22f, States.USE_FRONT_SHARP, 1, SmoothEnum.ACCELERATION),
                            new Sensors(30.5f, 8.5f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),
                            new Reset(States.ALL_RESET, -750, 650, 180),

                            new Odometry(States.ABSOLUTE_ODOMETRY, 645, 560, 90, SmoothEnum.ACCELERATION),
                            new Sensors(17f, 30, States.USE_FRONT_SHARP, 1, SmoothEnum.FAST),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 2350, 0, 180, SmoothEnum.DECELERATION),
                            new Sensors(18, 0, States.DEFAULT_SENSOR, 0, SmoothEnum.ACCELERATION),
                            new Sensors(20, 20, States.USE_BACK_SHARP, 1, SmoothEnum.DECELERATION),

                            new Transition(),
                    },
                    { //45
                            new Sensors(25, 34f, States.USE_RIGHT_SONIC, 0, SmoothEnum.DECELERATION),
                            new Reset(States.ALL_RESET, 0, 0, 0),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 0, 0, 720, SmoothEnum.OFF),
                            new Reset(States.GYRO_RESET, 0, 0, 180),

                            new Transition(),

                    },
                    { //46

                            new Sensors(30.5f, 10f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),
                            new Odometry(States.RELATIVE_ODOMETRY, -725, 0, 0, SmoothEnum.DECELERATION),
                            new Sensors(0, 17f, States.USE_RIGHT_SONIC, 0, SmoothEnum.DECELERATION),
                            new End(),

                    },
                    { //47
                            new Sensors(13f, 15f, States.USE_FRONT_SHARP, 1, SmoothEnum.OFF),
                            new Reset(States.ALL_RESET, 0, 0, 0),

                            new Odometry(States.ABSOLUTE_ODOMETRY, 0, 560, 180, SmoothEnum.ACCELERATION),
                            new Sensors(28.5f, 9.5f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),

                            new Transition(),
                    },
                    { //48
                            new Sensors(30.5f, 9f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),
                            new Reset(States.ALL_RESET, -750, 650, 180),

                            new Odometry(States.ABSOLUTE_ODOMETRY, 635, 560, 270, SmoothEnum.ACCELERATION),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 635, 0, 270, SmoothEnum.FAST),

                            new Sensors(22f, 0f, States.DEFAULT_SENSOR, 0, SmoothEnum.FAST),
                            new Sensors(20.6f, 20f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),

                            new Transition(),
                    },
                    { //49
                            new Sensors(30.5f, 9f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),
                            new Reset(States.ALL_RESET, -750, 650, 180),

                            new Odometry(States.ABSOLUTE_ODOMETRY, 645, 560, 0, SmoothEnum.ACCELERATION),
                            new Sensors(0f, 16f, States.USE_FRONT_SHARP, 0, SmoothEnum.FAST),
                            new Sensors(20.6f, 20f, States.USE_FRONT_SHARP, 1, SmoothEnum.FAST),
                            new Sensors(17, 19, States.USE_FRONT_SHARP, 1, SmoothEnum.OFF),
                            new Odometry(States.RELATIVE_ODOMETRY, 0, 50, -90, SmoothEnum.OFF),

                            new Transition(),

                    },
                    { //50
                            new Sensors(29f, 9f, States.USE_FRONT_SHARP, 1, SmoothEnum.OFF),
                            new Reset(States.ALL_RESET, -750, 650, 180),

                            new Odometry(States.ABSOLUTE_ODOMETRY, 645, 560, 90, SmoothEnum.ACCELERATION),

                            new Sensors(17f, 30, States.USE_FRONT_SHARP, 1, SmoothEnum.FAST),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 2350, -500, 270, SmoothEnum.FAST),
                            new Sensors(22, 0, States.DEFAULT_SENSOR, 0, SmoothEnum.FAST),
                            new Sensors(20, 20, States.USE_BACK_SHARP, 1, SmoothEnum.DECELERATION),


                            new Transition(),
                    },
                    { //51
                            new Sensors(29f, 9f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),
                            new Reset(States.ALL_RESET, -750, 650, 180),

                            new Odometry(States.ABSOLUTE_ODOMETRY, 645, 560, 90, SmoothEnum.ACCELERATION),
                            new Sensors(17f, 30, States.USE_FRONT_SHARP, 1, SmoothEnum.FAST),
                            new Odometry(States.ABSOLUTE_ODOMETRY, 2350, -100, 180, SmoothEnum.DECELERATION),
                            new Sensors(18, 0, States.DEFAULT_SENSOR, 0, SmoothEnum.ACCELERATION),
                            new Sensors(20, 20, States.USE_BACK_SHARP, 1, SmoothEnum.DECELERATION),

                            new Transition(),
                    },
                    { //52
                            // new BlackLineSensor(States.RESET_BLACK_LINE, 0, 0),
                            // new Sensors(28.5f, 9f, States.USE_FRONT_SHARP, 1, SmoothEnum.OFF),

                            new OMS(States.WAREHOUSE_OMS),

                            new BlackLineSensor(States.POSITION_BLACK_LINE, 0, 0),

                            new Odometry(States.RELATIVE_ODOMETRY, 65, -2, 0, SmoothEnum.OFF),
                            new OMS(States.MIDDLE_CLOSE_GRAB),
                            new Odometry(States.RELATIVE_ODOMETRY, 33, 0, 0, SmoothEnum.OFF),
                            new OMS(States.CLOSE_FULL_GRAB),
                            new Odometry(States.RELATIVE_ODOMETRY, -200, 0, 0, SmoothEnum.OFF),
                            // new BlackLineSensor(States.RESET_BLACK_LINE, 0, 0),
                            new OMS(States.DOWNLOAD_IN_ROBOT),
                            new Sensors(0, 9f, States.USE_FRONT_SHARP, 1, SmoothEnum.OFF),
                            new Sensors(28.5f, 9f, States.USE_FRONT_SHARP, 1, SmoothEnum.OFF),
                            // new BlackLineSensor(States.RESET_BLACK_LINE, 0, 0),


                            new Transition(),
                    },
                    { //53
                            // new BlackLineSensor(States.RESET_BLACK_LINE, 0, 0),
                            // new Sensors(28.5f, 9f, States.USE_FRONT_SHARP, 1, SmoothEnum.OFF),
                            new OMS(States.WAREHOUSE_OMS),
                            new BlackLineSensor(States.POSITION_BLACK_LINE, 0, 0),

                            new Odometry(States.RELATIVE_ODOMETRY, 65, -2, 0, SmoothEnum.OFF),
                            new OMS(States.MIDDLE_CLOSE_GRAB),
                            new Odometry(States.RELATIVE_ODOMETRY, 33, 0, 0, SmoothEnum.OFF),
                            new OMS(States.CLOSE_FULL_GRAB),
                            new Odometry(States.RELATIVE_ODOMETRY, -200, 0, 0, SmoothEnum.OFF),
                            // new BlackLineSensor(States.RESET_BLACK_LINE, 0, 0),

                            new OMS(States.DOWNLOAD_IN_ROBOT),
                            new Odometry(States.RELATIVE_ODOMETRY, 110, -62, 0, SmoothEnum.OFF),
                            // new BlackLineSensor(States.RESET_BLACK_LINE, 0, 0),

                            new Transition(),
                    },
                    { //54
                            // new BlackLineSensor(States.RESET_BLACK_LINE, 0, 0),
                            // new Sensors(28.5f, 9f, States.USE_FRONT_SHARP, 1, SmoothEnum.OFF),
                            new OMS(States.WAREHOUSE_OMS),
                            new BlackLineSensor(States.POSITION_BLACK_LINE, 0, 0),

                            new Odometry(States.RELATIVE_ODOMETRY, 65, -7, 0, SmoothEnum.OFF),
                            new OMS(States.MIDDLE_CLOSE_GRAB),
                            new Odometry(States.RELATIVE_ODOMETRY, 33, 0, 0, SmoothEnum.OFF),
                            new OMS(States.CLOSE_FULL_GRAB),
                            new Odometry(States.RELATIVE_ODOMETRY, -200, 0, 0, SmoothEnum.OFF),
                            // new BlackLineSensor(States.RESET_BLACK_LINE, 0, 0),

                            new OMS(States.DOWNLOAD_IN_ROBOT),
                            new Odometry(States.RELATIVE_ODOMETRY, 110, -62, 0, SmoothEnum.OFF),

                            // new BlackLineSensor(States.RESET_BLACK_LINE, 0, 0),

                            new Transition(),
                    },
                    { //55
                            // new BlackLineSensor(States.RESET_BLACK_LINE, 0, 0),
                            // new Sensors(28.5f, 9f, States.USE_FRONT_SHARP, 1, SmoothEnum.OFF),
                            new OMS(States.WAREHOUSE_OMS),
                            new BlackLineSensor(States.POSITION_BLACK_LINE, 0, 0),

                            new Odometry(States.RELATIVE_ODOMETRY, 65, -6, 0, SmoothEnum.OFF),
                            new OMS(States.MIDDLE_CLOSE_GRAB),
                            new Odometry(States.RELATIVE_ODOMETRY, 38, 0, 0, SmoothEnum.OFF),
                            new OMS(States.CLOSE_FULL_GRAB),
                            new Odometry(States.RELATIVE_ODOMETRY, -200, 0, 0, SmoothEnum.OFF),
                            // new BlackLineSensor(States.RESET_BLACK_LINE, 0, 0),

                            new OMS(States.DOWNLOAD_IN_ROBOT),
                            new Odometry(States.RELATIVE_ODOMETRY, 110, -62, 0, SmoothEnum.OFF),

                            // new BlackLineSensor(States.RESET_BLACK_LINE, 0, 0),


                            new Transition(),
                    },
                    { //56
                            // new BlackLineSensor(States.RESET_BLACK_LINE, 0, 0),
                            // new Sensors(28.5f, 9f, States.USE_FRONT_SHARP, 1, SmoothEnum.OFF),
                            new OMS(States.WAREHOUSE_OMS),
                            new BlackLineSensor(States.POSITION_BLACK_LINE, 0, 0),

                            new Odometry(States.RELATIVE_ODOMETRY, 70, 0, 0, SmoothEnum.OFF),
                            new OMS(States.MIDDLE_CLOSE_GRAB),
                            new Odometry(States.RELATIVE_ODOMETRY, 50, 0, 0, SmoothEnum.OFF),
                            new OMS(States.CLOSE_FULL_GRAB),
                            new Odometry(States.RELATIVE_ODOMETRY, -200, 0, 0, SmoothEnum.OFF),
                            // new BlackLineSensor(States.RESET_BLACK_LINE, 0, 0),

                            new OMS(States.DOWNLOAD_IN_ROBOT),
                            new Odometry(States.RELATIVE_ODOMETRY, 110, -62, 0, SmoothEnum.OFF),

                            // new BlackLineSensor(States.RESET_BLACK_LINE, 0, 0),

                            new Transition(),
                    },
                    { //57
                            new OMS(States.TAKE_CUBE_FROM_STAND),
                            new Odometry(States.RELATIVE_ODOMETRY, -30, 0, 0, SmoothEnum.OFF),
                            new Odometry(States.RELATIVE_ODOMETRY, 26, 0, 0, SmoothEnum.OFF),
                            new OMS(States.MIDDLE_CLOSE_GRAB),
                            new Odometry(States.RELATIVE_ODOMETRY, 30, 0, 0, SmoothEnum.OFF),
                            new OMS(States.CLOSE_FULL_GRAB),
                            new Odometry(States.RELATIVE_ODOMETRY, -80, 0, 0, SmoothEnum.OFF),
                            new OMS(18),

                            new Transition(),
                    },
                    { //58
                            new Sensors(7, 30f, States.USE_RIGHT_SONIC, 0, SmoothEnum.FAST),
                            new OMS(States.CLEAN_CUBE),
                            new Transition(),
                    },
                    { //59
                            new OMS(States.PUT_CUBE_IN_STAND),
                            new Transition(),
                    },
                    { //60
                            new OMS(States.PUT_CUBE_FULL),
                            new Transition(),
                    },
                    { //61

                            new Sensors(25f, 36, States.USE_RIGHT_SONIC, 1, SmoothEnum.DECELERATION),

                            new Sensors(0, 23.9f, States.USE_RIGHT_SONIC, 0, SmoothEnum.DECELERATION),
                            new OMS(States.OMS_CAMERA_DOWN),
                            new Camera(States.CAMERA_GREEN_CUBE),
                            new OMS(States.OMS_CAMERA_QR),
                            new Odometry(States.RELATIVE_ODOMETRY, 24, 0, 0, SmoothEnum.OFF),
                            new Camera(States.CAMERA_QR),
                            new Odometry(States.RELATIVE_ODOMETRY, -30, 0, 0, SmoothEnum.OFF),
                            new OMS(States.OMS_CAMERA_DOWN),
                            new Sensors(26.3f, 36, States.USE_RIGHT_SONIC, 1, SmoothEnum.DECELERATION),
                            new Camera(States.CAMERA_YELLOW_CUBE),

                            new ResetLogic(),
                            new InitializeLogic(),
                            new Transition(),
                    },

                    { //62
                            new OMS(States.TAKE_CUBE_IN_ROBOT),
                            new Transition(),
                    },

                    { //63

                            new Sensors(26f, 20, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),

                            new Odometry(States.RELATIVE_ODOMETRY, 0, 130, 0, SmoothEnum.OFF),
                            new OMS(States.OMS_CAMERA_DOWN),
                            new Camera(States.CAMERA_GREEN_CUBE),
                            new OMS(States.OMS_CAMERA_QR),
                            new Odometry(States.RELATIVE_ODOMETRY, 24, 0, 0, SmoothEnum.OFF),
                            new Camera(States.CAMERA_QR),
                            new Odometry(States.RELATIVE_ODOMETRY, -30, 0, 0, SmoothEnum.OFF),
                            new OMS(States.OMS_CAMERA_DOWN),
                            new Sensors(26f, 18, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),
                            new Camera(States.CAMERA_YELLOW_CUBE),

                            new ResetLogic(),
                            new InitializeLogic(),
                            new Transition(),
                    },

                    { //64

                            new Sensors(28f, 20f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),

                            new Sensors(0, 20, States.USE_RIGHT_SONIC, 0, SmoothEnum.DECELERATION),
                            new OMS(States.OMS_CAMERA_DOWN),

                            new Camera(States.CAMERA_GREEN_CUBE),
                            new OMS(States.OMS_CAMERA_QR),
                            new Odometry(States.RELATIVE_ODOMETRY, 25, 0, 0, SmoothEnum.OFF),
                            new Camera(States.CAMERA_QR),

                            new Odometry(States.RELATIVE_ODOMETRY, -30, 0, 0, SmoothEnum.OFF),
                            new OMS(States.OMS_CAMERA_DOWN),
                            new Sensors(28, 18f, States.USE_FRONT_SHARP, 1, SmoothEnum.DECELERATION),

                            new Camera(States.CAMERA_YELLOW_CUBE),

                            new ResetLogic(),
                            new InitializeLogic(),
                            new Transition(),
                    },
                    { //65
                            new Sensors(7, 30f, States.USE_RIGHT_SONIC, 0, SmoothEnum.OFF),
                            new OMS(States.CLEAN_CUBE_FROM_ROBOT),
                            new Transition(),

                    },
                    { //66
                            new OMS(States.TAKE_CUBE_IN_ROBOT),
                            new Sensors(26, 30f, States.USE_RIGHT_SONIC, 0, SmoothEnum.ACCELERATION),
                            new Transition(),
                    },
                    { //67
                            new Sensors(26, 30f, States.USE_RIGHT_SONIC, 0, SmoothEnum.DECELERATION),
                            new Transition(),
                    },
                    { //68
                            new OMS(States.END_SCAN),
                            new Transition(),
                    },
                    { //69
                            new OMS(States.CUBE_OUT_FROM_SCAN),
                            new Transition(),
                    },
                    { // 70
                            new OMS(States.START_CUBE_OUT_FROM_SCAN),
                            new Transition(),
                    },
                    { //71
                            new End(),
                    },
                    {
                            new Sensors(28.5f, 9f, States.USE_FRONT_SHARP, 1, SmoothEnum.OFF),
                            new BlackLineSensor(States.RESET_BLACK_LINE, 0, 0),
                            new BlackLineSensor(States.RESET_BLACK_LINE, 0, 0),

                            new Transition(),

                    },
                    {
                            new Sensors(28.5f, 9f, States.USE_FRONT_SHARP, 1, SmoothEnum.OFF),
                            new BlackLineSensor(States.RESET_BLACK_LINE, 0, 0),
                            new BlackLineSensor(States.RESET_BLACK_LINE, 0, 0),

                            new Transition(),

                    }
            };
}