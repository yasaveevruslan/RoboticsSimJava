package com.example.demo2.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class ListenPort {
    private final int port;

    private boolean stopThread = false;
    public String outString = "";

    private ServerSocket sct;
    private Socket clientSocket;
    private Thread thread;

    public ListenPort(int port){
        this.port = port;
    }

    public void startListening()
    {
        this.thread = new Thread(this::listening);
        this.thread.start();
    }

    private void listening()
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
                this.outString = new String(message, StandardCharsets.UTF_16LE);

                out.write(new byte[4]);

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

    public void stopListening()
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
