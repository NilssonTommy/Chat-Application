// mvn clean install
// mvn exec:java -Dexec.mainClass="com.example.Main" 
package com.example;
public class Main {

    public static void main(String[] args) {
        ClientNetwork.getInstance();
        new LoginController();
    }
}