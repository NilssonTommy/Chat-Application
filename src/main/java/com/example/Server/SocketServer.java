// mvn clean install
// mvn exec:java -Dexec.mainClass="com.example.SocketServer" 

package com.example.Server;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    public SocketServer(){
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(6666);
            System.out.println("Waiting for a client connection request...");   
            while(true) {
                Socket s = ss.accept();
                System.out.println("Client connected");

                ServerRunnable serverRunnable = new ServerRunnable(s);
                new Thread(serverRunnable).start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }finally {
            if (ss != null) {
                try {
                    ss.close();
                    System.out.println("ServerSocket closed.");
                } catch (Exception e) {
                    System.out.println("Error closing ServerSocket: " + e);
                }
            }
        }
    }
    
    public static void main(String[] args) {
        new SocketServer();
    }
}






