// mvn clean install
// mvn exec:java -Dexec.mainClass="com.example.SocketServer" 

package com.example;
import java.net.*;

public class SocketServer {

    public static void main(String[] args) {

        try {
            ServerSocket ss = new ServerSocket(6666);
            System.out.println("Waiting for a client connection request...");   

            while(true) {
                Socket s = ss.accept();
                System.out.println("Client connected");

                Runnable serverRunnable = new ServerRunnable(s);
                new Thread(serverRunnable).start();
            }

            // ss.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}