package com.example.demo2.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class InitLogic
{
    public static ArrayList<DriveElements> driveElements = new ArrayList<>();
    public ArrayList<int[]> indexMas = new ArrayList<>();

    public String[][] orders = new String[][]{
        // {"yellow", "none", "none", "none", "none", "none", "none"},
        // {"blue", "none", "none", "none", "none", "none", "none"},
        // {"yellow", "blue", "none", "none", "none", "none", "none"},
        // {"white", "none", "none", "none", "none", "none", "none"}

        // {"none", "none", "none", "none", "none", "none", "none"},
        // {"blue", "none", "none", "none", "none", "none", "none"},
        // {"yellow", "none", "none", "none", "none", "none", "none"},
        // {"white", "yellow", "blue", "none", "none", "none", "none"}


// `        {"white", "blue", "none", "none", "yellow", "none", "none"},
//         {"white", "white", "none", "none", "none", "none", "none"},
//         {"blue", "blue", "none", "none", "yellow", "none", "none"},
//         {"yellow", "blue", "white", "none", "none", "none", "none"}`

//         {"blue", "white", "none", "none", "none", "none", "none"},
//         {"blue", "white", "none", "none", "none", "none", "none"},
//         {"blue", "white", "none", "none", "none", "none", "none"},
//         {"blue", "white", "none", "none", "none", "none", "none"}

        //  {"blue", "yellow", "none", "none", "none", "white", "none"},
        //  {"blue", "yellow", "none", "none", "none", "white", "none"},
        //  {"blue", "yellow", "none", "none", "none", "white", "none"},
        //  {"blue", "yellow", "none", "none", "none", "white", "none"}

        {"none", "none", "none", "none", "none", "blue", "none"},
        {"none", "none", "none", "none", "none", "none", "none"},
        {"none", "none", "none", "none", "none", "none", "none"},
        {"none", "none", "none", "none", "none", "none", "none"}

    };

    public String[][] warehouse = new String[][]{
            { "yellow", "yellow", "yellow", "yellow", "yellow" },
            { "blue", "blue", "blue", "blue", "blue" },
            { "white", "white", "white", "white", "white" }
    };
    private final String[] palace = {"First", "Second", "Third", "Fourth"};
    public static String[][] stands = new String[][] { 
            { "green", "red" },
            { "green", "red" },
            { "green", "red" },
            { "red", "green" } };


    private String commandPath;
    private String lastPalateMethod = "None";

    public static String colorFirst = "none";
    public static String colorSecond = "none";
    public static String colorThird = "none";
    public static String colorStand = "none";
    public static String qrCode = "none";

    private int valueRetGreen = 0;

    public InitLogic(String command)
    {
        this.commandPath = command;
    }

    public void initCmodule()
    {
        SettingsForLogic set = new SettingsForLogic(warehouse,orders);
        ArrayList<String> arr = set.getCommands();

        if(commandPath.equals("warehouse"))
        {
            setWarehousePath(arr);
        }else if (commandPath.equals("ret")){
            ArrayList<String> retR = new ArrayList<>();
            ArrayList<String> retG = new ArrayList<>();
            for (String command: arr)
            {
                if(command.split(" ")[0].equals("RET"))
                {
                    if(command.split(" ")[2].equals("yellow"))
                    {
                        retR.add(command);
                    }
                    else
                    {
                        retG.add(command);
                        valueRetGreen+=1;
                    }
                }
            }
            Collections.reverse(retR);

            setRetPathGreenC(retG);
            setRetPathRedC(retR);
        }else if(commandPath.equals("delivery")){
            ArrayList<String> delR = new ArrayList<>();
            ArrayList<String> delG = new ArrayList<>();

            for (String command: arr)
            {
                if(command.split(" ")[0].equals("DEL"))
                {
                    if(command.split(" ")[2].equals("yellow"))
                    {
                        delR.add(command);
                    }
                    else
                    {
                        delG.add(command);
                    }
                }
            }

            setDeliveryPathGreenC(delG);
            setDeliveryPathRedC(delR);
        }
        driveElements.add(new DriveElements("END", 0));

        for (DriveElements element : driveElements)
        {
            System.out.println(element.getAction() + " " + element.getPositionLift());
        }

        generationLogic();

        for(int[] index: indexMas){
            System.out.println(Arrays.toString(index));

        }
    }


    public void initLogic()
    {
        SettingsForLogic set = new SettingsForLogic(warehouse,orders);

        ArrayList<String> arr = set.getCommands();
        ArrayList<String> delR = new ArrayList<>();
        ArrayList<String> retR = new ArrayList<>();
        ArrayList<String> delG = new ArrayList<>();
        ArrayList<String> retG = new ArrayList<>();
        for (String command: arr)
        {
            if(command.split(" ")[0].equals("RET"))
            {
                if(command.split(" ")[2].equals("yellow"))
                {
                    retR.add(command);
                }
                else
                {
                    retG.add(command);
                    valueRetGreen+=1;
                }
            }
            else
            {
                if(command.split(" ")[2].equals("yellow"))
                {
                    delR.add(command);
                }
                else
                {
                    delG.add(command);
                }
            }

        }
        Collections.reverse(retR);

        setRetPathGreen(retG, commandPath);
        setDeliveryPathGreen(delG, commandPath);
        setRetPathReds(retR);
        setDeliveryPathRed(delR);



        for (DriveElements element : driveElements)
        {
            System.out.println(element.getAction() + " " + element.getPositionLift());
        }

        generationLogic();

        for(int[] index: indexMas){
            System.out.println(Arrays.toString(index));

        }

    }

    public void initLogic(int numberPalace)
    {
        SettingsForLogic set = new SettingsForLogic(warehouse,orders);

        if(commandPath.equals("F")) {
            boolean firstPalate = numberPalace == 0|| numberPalace == 1 || numberPalace == 2;

            if(firstPalate){
                ArrayList<String> arr = set.getCommands();
                ArrayList<String> retG = new ArrayList<>();

                for (String command : arr) {

                    if (command.split(" ")[0].equals("RET")) {
                        if (!command.split(" ")[2].equals("yellow")) {
                            retG.add(command);
                            valueRetGreen+=1;
                        }
                    }
                }

                setRetPathGreenF(retG, numberPalace);
            }else{

                ArrayList<String> arr = set.getCommands();
                ArrayList<String> delR = new ArrayList<>();
                ArrayList<String> retR = new ArrayList<>();
                ArrayList<String> delG = new ArrayList<>();
                ArrayList<String> retG = new ArrayList<>();

                for (String command : arr) {
                    if (command.split(" ")[0].equals("RET")) {
                        if (command.split(" ")[2].equals("yellow")) {
                            retR.add(command);
                        } else {
                            retG.add(command);
                            valueRetGreen+=1;
                        }
                    } else {
                        if (command.split(" ")[2].equals("yellow")) {
                            delR.add(command);
                        } else {
                            delG.add(command);
                        }
                    }

                }
                Collections.reverse(retR);

                setRetPathGreen(retG, commandPath);
                setDeliveryPathGreen(delG, commandPath);
                setRetPathRed(retR);
                setDeliveryPathRed(delR);

            }


            for (DriveElements element : driveElements) {
                System.out.println(element.getAction() + " " + element.getPositionLift());
            }

            generationLogic();

            for (int[] d : indexMas) {
                System.out.println(Arrays.toString(d));
            }


        }

    }

    private void setRetPathGreenC(ArrayList<String> ret)
    {
        if(ret.size() != 0){
            for (int i = 0; i < ret.size(); i++)
            {
                String command = ret.get(i);
                int indexPalate = Arrays.asList(palace).indexOf(command.split(" ")[1]);
                if (driveElements.size()==0)
                {
                    driveElements.add(new DriveElements("FromStartTo"+command.split(" ")[1],0));
                    driveElements.add(new DriveElements("EndPalate"+command.split(" ")[1],0));
                    driveElements.add(new DriveElements(stands[indexPalate][0]+"Palate"+command.split(" ")[1],0));
                }

                driveElements.add(new DriveElements("CubeOut",Integer.parseInt(command.split(" ")[3])));
                if(command.equals(ret.get(ret.size()-1)))
                {
                    driveElements.add(new DriveElements("From"+command.split(" ")[1]+"ToClean",0));
                    driveElements.add(new DriveElements("ToCleanLift", 0));
                    driveElements.add(new DriveElements("CubeCleanInRobot", 0));
                    for (int j = 1; j < valueRetGreen; j++)
                    {
                        driveElements.add(new DriveElements("CubeClean",0));
                    }

                }
            }
        }
    }

    private void setRetPathRedC(ArrayList<String> ret)
    {
        for (String command : ret) {
            int indexPalate = Arrays.asList(palace).indexOf(command.split(" ")[1]);
            if (driveElements.size() == 0) {
                driveElements.add(new DriveElements("FromStartTo" + command.split(" ")[1], 0));

                driveElements.add(new DriveElements("EndPalate" + command.split(" ")[1], 0));
                driveElements.add(new DriveElements(stands[indexPalate][1] + "Palate" + command.split(" ")[1], 0));
            }

            driveElements.add(new DriveElements("CubeOut", Integer.parseInt(command.split(" ")[3])));
            driveElements.add(new DriveElements("From" + command.split(" ")[1] + "ToClean", 0));
            driveElements.add(new DriveElements("ToCleanLift", 0));
            driveElements.add(new DriveElements("CubeCleanInRobot", 0));
            driveElements.add(new DriveElements("Clean", 0));
        }
    }


    private void setDeliveryPathRedC(ArrayList<String> del)
    {
        SettingsForLogic set = new SettingsForLogic(warehouse,orders);

        for (String command : del) {
            int indexPalate = Arrays.asList(palace).indexOf(command.split(" ")[1]);

            if (driveElements.size() == 0) {
                driveElements.add(new DriveElements("FromStartToWarehouse", 0));
            }

            String[] coordinate = set.getCubeForWarehouse(command.split(" ")[2]).split(" ");
            driveElements.add(new DriveElements(coordinate[0], Integer.parseInt(coordinate[1])));

            driveElements.add(new DriveElements("FromWarehouseTo" + command.split(" ")[1], 0));
            driveElements.add(new DriveElements("CubeTakeInRobot", 0));
            driveElements.add(new DriveElements("EndPalate" + command.split(" ")[1], 0));
            driveElements.add(new DriveElements(stands[indexPalate][1] + "Palate" + command.split(" ")[1], 0));
            driveElements.add(new DriveElements("CubePut", Integer.parseInt(command.split(" ")[3])));
        }

    }

    private void setDeliveryPathGreenC(ArrayList<String> del)
    {
        SettingsForLogic set = new SettingsForLogic(warehouse,orders);

        for (String command : del) {
            int indexPalate = Arrays.asList(palace).indexOf(command.split(" ")[1]);
            if (driveElements.size() == 0) {
                driveElements.add(new DriveElements("FromStartToWarehouse", 0));
            }

            String[] coordinate = set.getCubeForWarehouse(command.split(" ")[2]).split(" ");
            driveElements.add(new DriveElements(coordinate[0], Integer.parseInt(coordinate[1])));

            driveElements.add(new DriveElements("FromWarehouseTo" + command.split(" ")[1], 0));
            driveElements.add(new DriveElements("CubeTakeInRobot", 0));
            driveElements.add(new DriveElements("EndPalate" + command.split(" ")[1], 0));
            driveElements.add(new DriveElements(stands[indexPalate][0] + "Palate" + command.split(" ")[1], 0));
            driveElements.add(new DriveElements("CubePut", Integer.parseInt(command.split(" ")[3])));
        }
    }

    private void setWarehousePath(ArrayList<String> path){
        SettingsForLogic set = new SettingsForLogic(warehouse,orders);

        String[] coordinate = set.getCubeForWarehouse(path.get(path.size() - 1).split(" ")[2]).split(" ");
        driveElements.add(new DriveElements(coordinate[0], Integer.parseInt(coordinate[1])));
    }

    private void setRetPathGreenF(ArrayList<String> ret, int numPalate)
    {

            for (int i = 0; i < ret.size(); i++)
            {
                String command = ret.get(i);
                int indexPalate = Arrays.asList(palace).indexOf(command.split(" ")[1]);

                driveElements.add(new DriveElements("StartCubeOutScan",0));

                if(i ==0)
                {
                    driveElements.add(new DriveElements(stands[indexPalate][0]+"Palate"+command.split(" ")[1],0));
                }

                driveElements.add(new DriveElements("CubeOutScan",Integer.parseInt(command.split(" ")[3])));

            }
        String nextPalate = palace[numPalate+1];
        driveElements.add(new DriveElements("EndScan", 0));
        driveElements.add(new DriveElements("From"+palace[numPalate]+"To"+nextPalate, 0));
        driveElements.add(new DriveElements("EndPalate"+nextPalate,0));
        driveElements.add(new DriveElements("Scan"+nextPalate, 0));

    }


    private void setDeliveryPathRed(ArrayList<String> del)
    {
        SettingsForLogic set = new SettingsForLogic(warehouse,orders);

        String lastCommand = " ";



        String lastPalate = lastPalateMethod;

        for (int i = 0; i < del.size(); i++)
        {
            if(driveElements.size()!=0)
            {
                lastCommand = driveElements.get(driveElements.size()-1).getAction().split(" ")[0];
            }
            String command = del.get(i);
            int indexPalate = Arrays.asList(palace).indexOf(command.split(" ")[1]);

            if (driveElements.size() == 0)
            {
                driveElements.add(new DriveElements("FromStartToWarehouse", 0));
            }
            else if (lastCommand.equals("CubeIn") || lastCommand.equals("CubePut"))
            {
                driveElements.add(new DriveElements("From"+lastPalate+"ToWarehouse", 0));

            }
            else if(!lastCommand.equals("CleanGoWarehouse"))
            {
                driveElements.add(new DriveElements("CleanGoWarehouse", 0));
            }

            String[] coordinate =   set.getCubeForWarehouse(command.split(" ")[2]).split(" ");
            driveElements.add(new DriveElements(coordinate[0], Integer.parseInt(coordinate[1])));

            driveElements.add(new DriveElements("FromWarehouseTo"+command.split(" ")[1],0));
            driveElements.add(new DriveElements("CubeTakeInRobot", 0));

            driveElements.add(new DriveElements("EndPalate"+command.split(" ")[1],0));

            driveElements.add(new DriveElements(stands[indexPalate][1]+"Palate"+command.split(" ")[1],0));
            driveElements.add(new DriveElements("CubePut",Integer.parseInt(command.split(" ")[3])));
            driveElements.add(new DriveElements("From"+command.split(" ")[1]+"ToClean",0));
            driveElements.add(new DriveElements("ToClean",0));
            driveElements.add(new DriveElements("Clean",0));

            if(command.equals(del.get(del.size()-1)))
            {
                driveElements.add(new DriveElements("CleanGoFinish",0));
            }
        }
        lastCommand = driveElements.get(driveElements.size()-1).getAction().split(" ")[0];
        if(lastCommand.equals("CubeIn") || lastCommand.equals("CubePut"))
        {
            driveElements.add(new DriveElements("From"+lastPalate+"ToFinish",0));

        }
        else if(lastCommand.equals("Clean"))
        {
            driveElements.add(new DriveElements("CleanGoFinish",0));

        }
        else if(lastCommand.equals("CleanGoWarehouse"))
        {
            driveElements.remove(driveElements.size()-1);
            driveElements.add(new DriveElements("CleanGoFinish",0));


        }

    }




    private void setDeliveryPathGreen(ArrayList<String> del, String commandPath)
    {
        SettingsForLogic set = new SettingsForLogic(warehouse,orders);

        for (int i = 0; i < del.size(); i++)
        {
            String command = del.get(i);
            int indexPalate = Arrays.asList(palace).indexOf(command.split(" ")[1]);

            if (driveElements.size() == 0 && commandPath.equals("F"))
            {
                driveElements.add(new DriveElements("FromFourthToWarehouse", 0));

            } else if (driveElements.size() == 0) {
                driveElements.add(new DriveElements("FromStartToWarehouse", 0));


            }

            if (i == 0)
            {
                for (int j = del.size(); j > 0; j--) {
                    String[] coordinate = set.getCubeForWarehouse(del.get(j - 1).split(" ")[2]).split(" ");
                    driveElements.add(new DriveElements(coordinate[0], Integer.parseInt(coordinate[1])));
                }
                driveElements.add(new DriveElements("FromWarehouseTo" + command.split(" ")[1], 0));
                driveElements.add(new DriveElements("CubeTakeInRobot", 0));
                driveElements.add(new DriveElements("EndPalate" + command.split(" ")[1], 0));
                driveElements.add(new DriveElements(stands[indexPalate][0] + "Palate" + command.split(" ")[1], 0));
            }
            else
            {
                String lastPalate = del.get(i - 1).split(" ")[1];
                String nextPalate = command.split(" ")[1];
                if (!lastPalate.equals(nextPalate))
                {
                    driveElements.add(new DriveElements("From" + lastPalate + "To" + nextPalate, 0));
                    driveElements.add(new DriveElements("CubeTakeInRobot", 0));
                    driveElements.add(new DriveElements("EndPalate" + command.split(" ")[1], 0));
                    driveElements.add(new DriveElements(stands[indexPalate][0] + "Palate" + command.split(" ")[1], 0));
                }
            }
            lastPalateMethod = command.split(" ")[1];
            int posLift = Integer.parseInt(command.split(" ")[3]);
            if (posLift == 1)
            {
                driveElements.add(new DriveElements("CubePut", posLift));
            }
            else
            {
                driveElements.add(new DriveElements("CubeIn", posLift));
            }
        }
    }

    private void setRetPathRed(ArrayList<String> ret)
    {
        String lastCommand = " ";
        for(String name : ret)
        {
            System.out.println(name);
        }
        for (String command : ret) {
            if (driveElements.size() != 0) {
                lastCommand = driveElements.get(driveElements.size() - 1).getAction().split(" ")[0];
            }

            int indexPalate = Arrays.asList(palace).indexOf(command.split(" ")[1]);
            if (driveElements.size() == 0) {
                driveElements.add(new DriveElements("FromStartTo" + command.split(" ")[1], 0));

                driveElements.add(new DriveElements("EndPalate" + command.split(" ")[1], 0));
                driveElements.add(new DriveElements(stands[indexPalate][1] + "Palate" + command.split(" ")[1], 0));
            } else if (lastCommand.equals("CubeIn") || lastCommand.equals("CubePut")) {
                String lastPalate = lastPalateMethod;
                String nextPalate = command.split(" ")[1];
                if (!lastPalate.equals(nextPalate)) {
                    driveElements.add(new DriveElements("From" + lastPalate + "To" + nextPalate, 0));
                    driveElements.add(new DriveElements("EndPalate" + command.split(" ")[1], 0));
                    driveElements.add(new DriveElements(stands[indexPalate][1] + "Palate" + command.split(" ")[1], 0));
                } else {
                    driveElements.add(new DriveElements(stands[indexPalate][1] + "Palate" + command.split(" ")[1], 0));
                    ;
                }
            } else {
                if (lastCommand.equals("CleanGoWarehouse")) {
                    driveElements.remove(driveElements.size() - 1);
                }
                driveElements.add(new DriveElements("FromCleanTo" + command.split(" ")[1], 0));
                driveElements.add(new DriveElements("EndPalate" + command.split(" ")[1], 0));
                driveElements.add(new DriveElements(stands[indexPalate][1] + "Palate" + command.split(" ")[1], 0));
            }
            driveElements.add(new DriveElements("CubeOut", Integer.parseInt(command.split(" ")[3])));
            driveElements.add(new DriveElements("From" + command.split(" ")[1] + "ToClean", 0));
            driveElements.add(new DriveElements("ToCleanLift", 0));
            driveElements.add(new DriveElements("CubeCleanInRobot", 0));
            driveElements.add(new DriveElements("Clean", 0));
        }
    }

    private void setRetPathReds(ArrayList<String> ret)
    {
        String lastCommand = " ";
        for(String name : ret)
        {
            System.out.println(name);
        }
        for (int i = 0; i < ret.size(); i++)
        {
            String command = ret.get(i);
            if(driveElements.size()!=0)
            {
                lastCommand = driveElements.get(driveElements.size()-1).getAction().split(" ")[0];
            }

            int indexPalate = Arrays.asList(palace).indexOf(command.split(" ")[1]);
            if (driveElements.size()==0)
            {
                driveElements.add(new DriveElements("FromStartTo"+command.split(" ")[1],0));

                driveElements.add(new DriveElements("EndPalate"+command.split(" ")[1],0));
                driveElements.add(new DriveElements(stands[indexPalate][1]+"Palate"+command.split(" ")[1],0));
            }
            else if (lastCommand.equals("CubeIn") || lastCommand.equals("CubePut") || (i!=0 && lastCommand.equals("CubeOut")))
            {

                String lastPalate = lastPalateMethod;
                String nextPalate = command.split(" ")[1];
                if(!lastPalate.equals(nextPalate))
                {
                    driveElements.add(new DriveElements("From" + lastPalate + "To" + nextPalate, 0));
                    driveElements.add(new DriveElements("EndPalate"+command.split(" ")[1],0));
                    driveElements.add(new DriveElements(stands[indexPalate][1]+"Palate"+command.split(" ")[1],0));
                }
                else
                {
                    driveElements.add(new DriveElements(stands[indexPalate][1]+"Palate"+command.split(" ")[1],0));
                }
            }
            else
            {
                if(lastCommand.equals("CleanGoWarehouse"))
                {
                    driveElements.remove(driveElements.size()-1);
                }
                driveElements.add(new DriveElements("FromCleanTo" + command.split(" ")[1], 0));
                driveElements.add(new DriveElements("EndPalate"+command.split(" ")[1],0));
                driveElements.add(new DriveElements(stands[indexPalate][1]+"Palate"+command.split(" ")[1],0));
            }
            driveElements.add(new DriveElements("CubeOut",Integer.parseInt(command.split(" ")[3])));

            if(i == ret.size()-1){
                driveElements.add(new DriveElements("From"+command.split(" ")[1]+"ToClean",0));
                driveElements.add(new DriveElements("ToCleanLift", 0));
                driveElements.add(new DriveElements("CubeCleanInRobot", 0));
                driveElements.add(new DriveElements("Clean",0));
            }else{
                lastPalateMethod = command.split(" ")[1];
            }

        }
    }

    private void setRetPathGreen(ArrayList<String> ret, String commandPath)
    {
        if(ret.size() != 0){
            for (int i = 0; i < ret.size(); i++)
            {
                String command = ret.get(i);
                int indexPalate = Arrays.asList(palace).indexOf(command.split(" ")[1]);
                if(driveElements.size()==0 && commandPath.equals("F")){
                    driveElements.add(new DriveElements("StartCubeOutScan",0));
                    driveElements.add(new DriveElements(stands[indexPalate][0]+"Palate"+command.split(" ")[1],0));
                }else if (driveElements.size()==0)
                {
                    driveElements.add(new DriveElements("FromStartTo"+command.split(" ")[1],0));
                    driveElements.add(new DriveElements("EndPalate"+command.split(" ")[1],0));
                    driveElements.add(new DriveElements(stands[indexPalate][0]+"Palate"+command.split(" ")[1],0));
                }
                else
                {
                    String lastPalate = ret.get(i - 1).split(" ")[1];
                    String nextPalate = command.split(" ")[1];
                    if(!lastPalate.equals(nextPalate))
                    {
                        driveElements.add(new DriveElements("From" + lastPalate + "To" + nextPalate, 0));
                        driveElements.add(new DriveElements("EndPalate"+command.split(" ")[1],0));
                        driveElements.add(new DriveElements(stands[indexPalate][0]+"Palate"+command.split(" ")[1],0));
                    }
                }
                driveElements.add(new DriveElements("CubeOut",Integer.parseInt(command.split(" ")[3])));
                if(command.equals(ret.get(ret.size()-1)))
                {
                    driveElements.add(new DriveElements("From"+command.split(" ")[1]+"ToClean",0));
                    driveElements.add(new DriveElements("ToCleanLift", 0));
                    driveElements.add(new DriveElements("CubeCleanInRobot", 0));
                    for (int j = 1; j < valueRetGreen; j++)
                    {
                        driveElements.add(new DriveElements("CubeClean",0));
                    }
                    driveElements.add(new DriveElements("CleanGoWarehouse",0));
                }
            }
        }else if(commandPath.equals("F")){
            if(valueRetGreen!=0){
                driveElements.add(new DriveElements("EndScan", 0));
                driveElements.add(new DriveElements("FromFourthToClean",0));
                driveElements.add(new DriveElements("ToCleanLift", 0));
                driveElements.add(new DriveElements("CubeCleanInRobot", 0));
                for (int j = 1; j < valueRetGreen; j++)
                {
                    driveElements.add(new DriveElements("CubeClean",0));
                }
                driveElements.add(new DriveElements("CleanGoWarehouse",0));
            }else{
                driveElements.add(new DriveElements("EndScan", 0));
                driveElements.add(new DriveElements("FromFourthToWarehouse",0));

            }

        }
    }


    public void generationLogic()
    {

        for (DriveElements s : driveElements)
        {
            int[] a;

            switch (s.getAction())
            {

                case "FromStartToFirst":
                    a = new int[]{0, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "greenPalateFirst":
                    a = new int[]{1, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "redPalateFirst":
                    a = new int[]{2, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "EndPalateFirst":
                    a = new int[]{3, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromFirstToSecond":
                    a = new int[]{4, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromFirstToThird":
                    a = new int[]{5, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromFirstToFourth":
                    a = new int[]{6, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromFirstToClean":
                    a = new int[]{7, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromFirstToWarehouse":
                    a = new int[]{8, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromFirstToFinish":
                    a = new int[]{9, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromStartToSecond":
                    a = new int[]{10, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "greenPalateSecond":
                    a = new int[]{11, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "redPalateSecond":
                    a = new int[]{12, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "EndPalateSecond":
                    a = new int[]{13, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromSecondToFirst":
                    a = new int[]{14, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromSecondToThird":
                    a = new int[]{15, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromSecondToFourth":
                    a = new int[]{16, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromSecondToClean":
                    a = new int[]{17, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromSecondToWarehouse":
                    a = new int[]{18, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromSecondToFinish":
                    a = new int[]{19, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromStartToThird":
                    a = new int[]{20, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "greenPalateThird":
                    a = new int[]{21, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "redPalateThird":
                    a = new int[]{22, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "EndPalateThird":
                    a = new int[]{23, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromThirdToFirst":
                    a = new int[]{24, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromThirdToSecond":
                    a = new int[]{25, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromThirdToFourth":
                    a = new int[]{26, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromThirdToClean":
                    a = new int[]{27, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromThirdToWarehouse":
                    a = new int[]{28, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromThirdToFinish":
                    a = new int[]{29, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromStartToFourth":
                    a = new int[]{30, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "greenPalateFourth":
                    a = new int[]{31, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "redPalateFourth":
                    a = new int[]{32, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "EndPalateFourth":
                    a = new int[]{33, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromFourthToFirst":
                    a = new int[]{34, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromFourthToSecond":
                    a = new int[]{35, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromFourthToThird":
                    a = new int[]{36, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromFourthToClean":
                    a = new int[]{37, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromFourthToWarehouse":
                    a = new int[]{38, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromFourthToFinish":
                    a = new int[]{39, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "CleanGoWarehouse":
                    a = new int[]{40, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromCleanToFirst":
                    a = new int[]{41, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromCleanToSecond":
                    a = new int[]{42, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromCleanToThird":
                    a = new int[]{43, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromCleanToFourth":
                    a = new int[]{44, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "Clean":
                    a = new int[]{45, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "CleanGoFinish":
                    a = new int[]{46, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromStartToWarehouse":
                    a = new int[]{47, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromWarehouseToFirst":
                    a = new int[]{48, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromWarehouseToSecond":
                    a = new int[]{49, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromWarehouseToThird":
                    a = new int[]{50, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "FromWarehouseToFourth":
                    a = new int[]{51, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "Warehouse1":
                    a = new int[]{52, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "Warehouse2":
                    a = new int[]{53, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "Warehouse3":
                    a = new int[]{54, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "Warehouse4":
                    a = new int[]{55, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "Warehouse5":
                    a = new int[]{56, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "CubeOut":
                    a = new int[]{57, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "CubeClean":
                    a = new int[]{58, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "CubePut":
                    a = new int[]{59, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "CubeIn":
                    a = new int[]{60, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "ScanSecond":
                    a = new int[]{61, s.getPositionLift()};
                    indexMas.add(a);
                    break;

                case "CubeTakeInRobot":
                    a = new int[]{62, s.getPositionLift()};
                    indexMas.add(a);
                    break;
                case "ScanThird":
                    a = new int[]{63, s.getPositionLift()};
                    indexMas.add(a);
                    break;
                case "ScanFourth":
                    a = new int[]{64, s.getPositionLift()};
                    indexMas.add(a);
                    break;
                case "CubeCleanInRobot":
                    a = new int[]{65, s.getPositionLift()};
                    indexMas.add(a);
                    break;
                case "ToCleanLift":
                    a = new int[]{66, s.getPositionLift()};
                    indexMas.add(a);
                    break;
                case "ToClean":
                    a = new int[]{67, s.getPositionLift()};
                    indexMas.add(a);
                    break;
                case "EndScan":
                    a = new int[]{68, s.getPositionLift()};
                    indexMas.add(a);
                    break;
                case "CubeOutScan":
                    a = new int[]{69, s.getPositionLift()};
                    indexMas.add(a);
                    break;
                case "StartCubeOutScan":
                    a = new int[]{70, s.getPositionLift()};
                    indexMas.add(a);
                    break;
                case "END":
                    a = new int[]{71, s.getPositionLift()};
                    indexMas.add(a);
                    break;
            }
        }
    }

    public static void changeStands(int palate, int count){
        stands[palate][count] = colorStand;
        for (String[] stand : stands) {
            for (String s : stand) {
                System.out.print(s + "   ");
            }
            System.out.print("\n");

        }
    }

    public void addOrders(int palate)
    {
        String[] elements;

        if(!qrCode.isEmpty())
        {
            elements = qrCode.split(", ");
        }
        else
        {
            elements = new String[] { "none", "none", "none" };
        }
        for (int i = 0; i < 7; i++)
        {
            if (i < 4)
            {
                if (elements.length > i)
                {
                    orders[palate][i] = elements[i];
                }
                else
                {
                    orders[palate][i] = "none";
                }
            }
            else
            {
                if (i == 4)
                {
                    orders[palate][i] = colorFirst;
                }
                else if(i == 5)
                {
                    orders[palate][i] = colorSecond;
                }else{
                    orders[palate][i] = colorThird;

                }
            }
        }
        for (String[] order : orders) {
            for (String s : order) {
                System.out.print(s + "   ");
            }
            System.out.print("\n");

        }
        qrCode = "none";
        colorFirst = "none";
        colorSecond = "none";
    }

    public void resetLogic()
    {
        driveElements.clear();
        indexMas.clear();
        for (int i = 0; i < orders.length; i++) {
            for (int j = 4; j < 7; j++){
                if(orders[i][j].equals("white") || orders[i][j].equals("blue")){
                    orders[i][j] = "none";
                }
            }

        }
    }
}
