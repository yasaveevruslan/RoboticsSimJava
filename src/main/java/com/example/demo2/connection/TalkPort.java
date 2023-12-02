package com.example.demo2.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class TalkPort {
    private final int port;

    private boolean stopThread = false;
    public String outString = "";

    private ServerSocket sct;
    private Socket clientSocket;
    private Thread thread;

    public TalkPort(int port){
        this.port = port;
    }

    public void startTalking()
    {
        this.thread = new Thread(this::talking);
        this.thread.start();
    }

    private void talking()
    {
        try
        {
            this.sct = new ServerSocket(this.port);
            this.clientSocket = this.sct.accept();
        }
        catch (IOException e)
        {
            // there could be a error
        }

        this.stopThread = false;

        try
        {
            DataInputStream in = new DataInputStream(this.clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(this.clientSocket.getOutputStream());

            while (!this.stopThread)
            {
                byte[] message = new byte[256];
                in.readFully(message);
                String check = new String(message, StandardCharsets.UTF_16LE);

                if (check.equals("Wait for data")){
                    byte[] data = this.outString.getBytes(StandardCharsets.UTF_16LE);
                    int len = data.length;
                    byte[] lenghtAsBytes = new byte[] {
                            (byte)(len & 0xff),
                            (byte)((len >> 8) & 0xff),
                            (byte)((len >> 16) & 0xff),
                            (byte)((len >> 24) & 0xff)
                    };
                    out.write(lenghtAsBytes);
                    out.write(data);
                }

                Thread.sleep(4);
            }

            this.clientSocket.shutdownInput();
            this.clientSocket.shutdownOutput();
            this.sct.close();
        }
        catch (IOException | InterruptedException e)
        {
            // there could be a error
        }
    }

    private void resetOut()
    {
        this.outString = "";
    }

    public void stopTalking()
    {
        this.stopThread = true;
        this.resetOut();
        if (this.sct != null && this.clientSocket != null)
        {
            try
            {
                this.clientSocket.shutdownInput();
                this.clientSocket.shutdownOutput();
                this.sct.close();
            }
            catch (IOException e)
            {
                // there could be a error
            }

            if (this.thread != null)
            {
                int stTime = LocalDateTime.now().toLocalTime().toSecondOfDay();
                while (this.thread.isAlive())
                {
                    if (LocalDateTime.now().toLocalTime().toSecondOfDay() - stTime > 1)
                    {
                        try
                        {
                            this.sct.close();
                        }
                        catch (IOException e)
                        {
                            // there could be a error
                        }

                    }
                }
            }
        }
    }
}
