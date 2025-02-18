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

    public Boolean login(String username) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM Users WHERE username = ?")) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("Yes");
                    return true;
                } else {
                    System.out.println("no");
                    return false;
                }
            }
        } catch (SQLException e) {
            return false;
        }
    }
    
    
    public boolean createUser(String username) {
        String insertUserSQL = "INSERT INTO Users (username) VALUES (?)";
    
        try (PreparedStatement insertStmt = conn.prepareStatement(insertUserSQL)) {
            insertStmt.setString(1, username);
            int inserted = insertStmt.executeUpdate();
    
            if (inserted > 0) {
                System.out.println("User created successfully.");
                return true;
            }
        } catch (SQLIntegrityConstraintViolationException e) {  // Catch duplicate username error
            System.out.println("User already exists.");
        } catch (SQLException e) {
            System.out.println("Error inserting user.");
            e.printStackTrace();
        }
        return false;
    }
    

    public boolean deleteUser(String username) {
        String sql = "DELETE FROM Users WHERE username = ?";
    
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username); 
            int deleted = ps.executeUpdate(); 
            System.out.println("Deleted");
    
            return deleted > 0; 
        } catch (SQLException e) {
            System.out.println("Failed to delete");
            e.printStackTrace();  // Print error details
            return false;  // Return false if an error occurs
        }
    }
    /* 
    public boolean createRoom(String roomName, String username) {
        String sql = "INSERT INTO Rooms VALUES (?)";
    
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, roomName); 
            int inserted = ps.executeUpdate(); 
            System.out.println("Created");
    
            return inserted > 0; 
        } catch (SQLException e) {
            System.out.println("Failed to create user");
            e.printStackTrace();  // Print error details
            return false;  // Return false if an error occurs
        }
    } ---------------------Nästa gång lägg ett sätt så man även skriver vem som skapade rummet
*/ 
    

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
 