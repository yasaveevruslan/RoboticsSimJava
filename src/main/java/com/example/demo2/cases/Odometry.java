package com.example.demo2.cases;

import com.example.demo2.training.Elements;
import com.example.demo2.training.Function;
import com.example.demo2.training.StateMachine;
import com.example.demo2.training.States;

public class Odometry implements IState{

    public Elements element = new Elements();

    private final String name;

    private float x, y, z;

    private SmoothEnum mode;

    private boolean relative;

    private float coordinateX, coordinateY, coordinateZ = 0;

    @Override
    public boolean execute() {

        return this.WhatIsElement(this.name, this.x, this.y, this.z, this.mode);
    }

    public Odometry(String name, float x, float y, float z, SmoothEnum mode)
    {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.mode = mode;
    }

    private final float[][] speedXandYfunc = {{0f, 5f, 15f, 40f, 70f, 150f, 170f, 220f}, {0f, 5f, 10f, 15f, 28f, 50f, 70f, 95}}; //ABS
    private final float[][] speedZfunc = { { 0f, 1.2f, 3f, 6f, 12f, 26, 32, 50 }, { 0f, 4f, 10f, 18, 24f, 32, 48, 70 } };//ABS

    private final float[][] speedXandYfuncRel = {{0f, 5f, 15f, 30f, 70f, 120f},{0, 3f, 5f, 10f, 18f, 45f}}; //REL
    private final float [][] speedZfuncRel = {{0, 1.2f, 3f, 6f, 12f, 26f},{0f, 4f, 10f, 18, 24f, 32}}; //REL

    private final float[][] timeSpeed = {{0f, 0.15f, 0.3f, 0.5f, 0.8f}, {0f, 0.3f, 0.5f, 0.7f, 1}}; // time

    private boolean WhatIsElement(String name, float x, float y, float z, SmoothEnum mode)
    {

        float gyro = (float) Elements.coordinatesZ;
        float[] polar = Function.ReImToPolar(x, y);
        float[] coordinates = Function.PolarToReIm(polar[0], (float) (polar[1] + Math.toRadians(gyro)));



        if (name.equals(States.ABSOLUTE_ODOMETRY))
        {
            coordinateX = x;
            coordinateY = y;
            coordinateZ = z;
            relative = false;
        }
        else
        {
            if (StateMachine.isFirst)
            {
                coordinateX = coordinates[0] + Elements.coordinatesX;
                coordinateY = coordinates[1] + Elements.coordinatesY;
                coordinateZ = z + gyro;

                relative = true;
            }
        }
        return Drive(coordinateX, coordinateY, coordinateZ, mode, relative);
    }



    private int position = 0;
    private float cofX = 5, cofZ = 0.5f;
    private boolean smooth = false;

    private static final float DECELEARTION_XY = 5, NOT_DECELERATION_XY = 220;
    private static final float DECELEARTION_Z = 1, NOT_DECELERATION_Z = 15;



    private boolean Drive(float x, float y, float z, SmoothEnum mode, boolean Relative)
    {

        float nowX = x - Elements.coordinatesX;
        float nowY = y - Elements.coordinatesY;
        float nowZ = z - (float) Elements.coordinatesZ;

        float acc = Function.TransF(timeSpeed, StateMachine.time - StateMachine.startTime);

        if (StateMachine.isFirst)
        {
            if (!Function.InRangeBool(nowZ, -30, 30))
            {
                position = Function.axis(x, y, Elements.coordinatesX, Elements.coordinatesY);
            }
            else
            {
                position = 0;
            }
            StateMachine.isFirst = false;
        }
        else if (Function.InRangeBool(nowZ, -10, 10))
        {
            position = 0;
        }

        if (mode == SmoothEnum.OFF)
        {
            cofX = Odometry.DECELEARTION_XY;
            cofZ = Odometry.DECELEARTION_Z;
            smooth = false;
        }
        else if (mode == SmoothEnum.ACCELERATION)
        {
            cofX = Odometry.NOT_DECELERATION_XY;
            cofZ = Odometry.NOT_DECELERATION_Z;
            smooth = true;
        }
        else if (mode == SmoothEnum.FAST)
        {
            cofX = Odometry.NOT_DECELERATION_XY;
            cofZ = Odometry.NOT_DECELERATION_Z;
            smooth = true;
        }
        else
        {
            cofX = Odometry.DECELEARTION_XY * 2.5f;
            cofZ = Odometry.DECELEARTION_Z * 2.5f;
            smooth = true;
        }



        boolean stopX = Function.InRangeBool(nowX, -cofX, cofX);
        boolean stopY = Function.InRangeBool(nowY, -cofX, cofX);
        boolean stopZ = Function.InRangeBool(nowZ, -cofZ, cofZ);

        if (position == 1)
        {
            nowX *= 5.2f;
        }
        else if(position == 2)
        {
            nowY *= 5.2f;
        }

        float r = Function.TransF(speedXandYfunc, (float) Math.sqrt(nowX * nowX + nowY * nowY));
        float theta = (float) (Math.atan2(nowY, nowX));

        float speedX = (float)(r * Math.cos(theta)) * acc;
        float speedY = (float)(r * Math.sin(theta)) * acc;
        float speedZ = Function.TransF(speedZfunc, nowZ) * acc;



        if (stopX && stopY && stopZ)
        {
            element.setAxisSpeed(0, 0, 0);
        }
        else
        {
            element.setAxisSpeed(speedX, speedY, speedZ);
        }

        return stopX && stopY && stopZ;
    }
}
