package com.example;
import java.sql.*; // JDBC stuff.
import java.util.Properties;

public class PortalConnection {
    private static PortalConnection instance;


    // Set this to e.g. "portal" if you have created a database named portal
    // Leave it blank to use the default database of your database user
    static final String DBNAME = "";
    // For connecting to the portal database on your local machine
    static final String DATABASE = "jdbc:postgresql://localhost/"+DBNAME;
    static final String USERNAME = "postgres";
    static final String PASSWORD = "postgres";

    // This is the JDBC connection object you will be using in your methods.
    private Connection conn;


    public PortalConnection() throws SQLException, ClassNotFoundException {
        this(DATABASE, USERNAME, PASSWORD);  
    }

    // Initializes the connection, no need to change anything here
    public PortalConnection(String db, String user, String pwd) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pwd);
        conn = DriverManager.getConnection(db, props);
    }

    public String login(String username) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO Users VALUES(?)")) {
            ps.setString(1, username);
            ps.executeUpdate();
            return "{\"success\": true, \"message\": \"User is now registered\"}";  // Fixed JSON format
        } catch (SQLException e) {
            return "{\"success\": false, \"error\": \"" + getError(e) + "\"}";  // Consistent JSON format
        }
    }

    public static String getError(SQLException e){
        String message = e.getMessage();
        int ix = message.indexOf('\n');
        if (ix > 0) message = message.substring(0, ix);
        message = message.replace("\"","\\\"");
        return message;
     }

     public static PortalConnection getInstance() {
        if (instance == null){
            try {
            instance = new PortalConnection();
            }catch (ClassNotFoundException e) {
                System.err.println("ERROR!\nYou do not have the Postgres JDBC driver (e.g. postgresql-42.5.1.jar) in your runtime classpath!");
             } catch (Exception e) {
                e.printStackTrace();
             }
          } 
          return instance;
        }

        public Connection getConnection() {
            return conn;
        }
    }
 