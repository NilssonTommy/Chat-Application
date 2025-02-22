
package com.example;

public class TestConnection {
    public static void main(String[] args) {
        PortalConnection db = PortalConnection.getInstance();
        if (db.getConnection() != null) {
            System.out.println("✅ Connected to PostgreSQL successfully!");
        } else {
            System.out.println("❌ Failed to connect!");
        }

        db.login("Patrick");
        db.createUser("Patrick");
        db.createUser("Tabort2");
        db.createUser("Tabort");
        db.deleteUser("Tabort");
    }
}
