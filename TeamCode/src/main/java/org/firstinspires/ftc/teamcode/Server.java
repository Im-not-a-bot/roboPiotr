package org.firstinspires.ftc.teamcode;

// File Name GreetingServer.java
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.net.*;

import java.io.*;
import java.util.Scanner;


public class Server extends Thread {
    private final ServerSocket serverSocket;
    Telemetry telemetry;
    int port;
    public byte[] data = new byte[0];


    public Server(int p, Telemetry t) throws IOException {
        port=p;
        telemetry=t;
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);
    }

    public void run() {

        while (true) {
            try {
                telemetry.addLine("Waiting for client on port " + serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();

                telemetry.addLine("Just connected to " + server.getRemoteSocketAddress());
                DataInputStream in = new DataInputStream(server.getInputStream());

                telemetry.addLine(in.readUTF());
                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF("you are now connected to " + server.getLocalSocketAddress());

                boolean exit = true;

                Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(exit)
                        {
                            if (data.length > 0) {
                                try {
                                    out.write(data);
                                } catch (IOException e) {
                                 e.printStackTrace();
                                }
                                data=new byte[0];
                            }
                        }
                        try {
                            server.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                t1.start();


            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
