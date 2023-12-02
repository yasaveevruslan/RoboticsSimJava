package com.example.demo2.connection;

import java.util.ArrayList;
import java.util.List;

public class ConnectionHelper {
    public static final int MAX_DATA_RECEIVE = 3;
    public static final int MAX_DATA_TRANSMIT = 10;

    private final int PORT_GET_DATA = 65431;
    private final int PORT_SET_DATA = 65432;

    private TalkPort talkChannel;
    private ListenPort listenChannel;

    public ConnectionHelper()
    {
        this.talkChannel = new TalkPort(PORT_SET_DATA);
        this.listenChannel = new ListenPort(PORT_GET_DATA);
    }

    public void startChannels()
    {
        this.talkChannel.startTalking();
        this.listenChannel.startListening();
    }

    public void stopChannels()
    {
        this.talkChannel.stopTalking();
        this.listenChannel.stopListening();
    }

    public void setData(List<Float> lst)
    {
        this.talkChannel.outString = JoinFloatChannel(lst);
    }

    public List<Float> getData()
    {
        return ParseFloatChannel(this.listenChannel.outString);
    }

    public static List<Float> ParseFloatChannel(String txt)
    {
        List<Float> outList = new ArrayList<>();
        try
        {
            String[] splitted = txt.replace(',', '.').split(";");
            for(String s : splitted)
            {
                outList.add(Float.parseFloat(s));
            }
        }
        catch (Exception e)
        {
            // could be a error :)
        }
        return outList;
    }

    public static String JoinFloatChannel(List<Float> lst)
    {
        List<String> strings = new ArrayList<>();
        for (float f : lst)
        {
            strings.add(Float.toString(f));
        }
        return String.join(";", strings);
    }
}
