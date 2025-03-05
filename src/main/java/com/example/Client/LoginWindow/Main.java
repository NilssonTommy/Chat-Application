// mvn clean install
// mvn exec:java -Dexec.mainClass="com.example.Main" 
package com.example.Client.LoginWindow;

import com.example.Client.ClientNetwork.ClientNetwork;

public class Main {

    public static void main(String[] args) {
        ClientNetwork.getInstance();
        new LoginController();
    }
}