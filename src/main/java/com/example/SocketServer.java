package com.example;

import java.net.*;

public class SocketServer {

    public static void main(String[] args) {

        PortalConnection pc = PortalConnection.getInstance();
        ClientHandler cHandler = new ClientHandler(pc);

        try {
            ServerSocket ss = new ServerSocket(6666);
            System.out.println("Waiting for a client connection request...");   

            while(true) {
                Socket s = ss.accept();
                System.out.println("Client connected");

                Runnable serverRunnable = new ServerRunnable(s, cHandler);
                new Thread(serverRunnable).start();
            }

            // ss.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}