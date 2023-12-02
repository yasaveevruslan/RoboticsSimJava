package com.example.demo2.logic;
public class DriveElements {

    private String action;
    private int positionLift;

    public DriveElements(String actionIn, int positionLiftIn){
        this.action = actionIn;
        this.positionLift = positionLiftIn;
    }

    public String getAction() {
        return action;
    }

    public int getPositionLift() {
        return positionLift;
    }
}

