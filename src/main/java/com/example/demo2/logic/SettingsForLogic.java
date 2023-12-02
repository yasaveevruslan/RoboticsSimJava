package com.example.demo2.logic;
import java.util.ArrayList;
import java.util.List;

public class SettingsForLogic
{
    private  String [][] warehouse;
    private  String [][] orderboard;

    private String[] palace = {"First", "Second", "Third", "Fourth"};

    public SettingsForLogic(String[][] warehouse, String[][] orderboard)
    {
        this.warehouse = warehouse;
        this.orderboard = orderboard;

    }

    public String getCubeForWarehouse(String color)
    {
        String coordinate = "none";
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if(warehouse[i][j].equals(color))
                {
                    coordinate = "Warehouse" + (j + 1) + " " + (i + 1);
                    warehouse[i][j] = "none";
                    break;
                }
            }
        }
        return coordinate;
    }

    public ArrayList<String> getCommands()
    {
        ArrayList<String> commands = new ArrayList<>();

        int positionGreenDEL = 0, positionRedDEL = 0;
        List<String> cubeR = new ArrayList<>();
        List<String> cubeG = new ArrayList<>();

        for (int i = 0; i < orderboard.length; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                if(!orderboard[i][j].equals("none"))
                {
                    if(orderboard[i][j].equals("yellow"))
                    {
                        positionRedDEL+=1;
                        commands.add("DEL " + palace[i] + " " +orderboard[i][j] + " " + positionRedDEL);
                    }else
                    {
                        positionGreenDEL+=1;
                        commands.add("DEL " + palace[i] + " " +orderboard[i][j] + " " + positionGreenDEL);
                    }
                }
            }
            for (int j = 4; j < 7; j++)
            {
                if(!orderboard[i][j].equals("none")){
                        if (orderboard[i][j].equals("yellow"))
                        {
                            cubeR.add("yellow");
                        }else
                        {
                            cubeG.add(orderboard[i][j]);
                        }
                    }
                }
                for (String cube : cubeR)
                {
                    commands.add("RET " + palace[i] + " " +cube + " " + 1);
                }
                for (int c = cubeG.size(); c > 0; c--)
                {
                    commands.add("RET " + palace[i] + " " +cubeG.get(c-1) + " "+ c);
                }
                cubeR.clear();
                cubeG.clear();

                positionGreenDEL = 0; positionRedDEL = 0;
        }

        return  commands;
    }
}