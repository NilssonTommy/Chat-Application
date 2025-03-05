package com.example.Server;
import java.sql.Connection; // JDBC stuff.
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;// JDBC stuff.
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.example.Client.ChatroomWindow.ImageMessage;
import com.example.Client.ChatroomWindow.TextMessage;
import com.example.Client.LoginWindow.UserResponse;
import com.example.Helpers.Message;
import com.example.Helpers.UserInterface;


public class PortalConnection {
    private static PortalConnection instance;

    static final String DBNAME = "postgres";
    static final String DATABASE = "jdbc:postgresql://localhost/"+DBNAME;
    static final String USERNAME = "postgres";
    static final String PASSWORD = "postgres";

    private Connection conn;


    public PortalConnection() throws SQLException, ClassNotFoundException {
        this(DATABASE, USERNAME, PASSWORD);  
    }

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
        String insertUserSQL = "INSERT INTO Users (username, status) VALUES (?, 1)";
    
        try (PreparedStatement insertStmt = conn.prepareStatement(insertUserSQL)) {
            insertStmt.setString(1, username);
            int inserted = insertStmt.executeUpdate();
    
            if (inserted > 0) {
                System.out.println("User created successfully.");
                return true;
            }
        } catch (Exception e) {  
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
            e.printStackTrace();  
            return false;  
        }
    }
     
    public boolean createRoom(String username, String roomName) {
        String sql1 = "SELECT COUNT(*) FROM Rooms WHERE RoomName = ?";
        String sql2 = "INSERT INTO Rooms(UserID, RoomName) VALUES (?, ?)";
    
        try (PreparedStatement ps = conn.prepareStatement(sql1)) {
            ps.setString(1, roomName);
            ResultSet rs = ps.executeQuery();
    
            if (rs.next() && rs.getInt(1) > 0) {
                return false;
            }
    
            try (PreparedStatement ps2 = conn.prepareStatement(sql2)) {
                ps2.setString(1, username);
                ps2.setString(2, roomName);
                int inserted = ps2.executeUpdate();
                return inserted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean selectRoom(String roomName) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM Rooms WHERE RoomName = ?")) {
            ps.setString(1, roomName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public Boolean addRoom(String userId, String roomName) {
        try {
            String sql1 = "SELECT COUNT(*) FROM Rooms WHERE RoomName = ? AND UserID = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql1)) {
                ps.setString(1, roomName);
                ps.setString(2, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        return false;
                    }
                }
            }
            String sql2 = "SELECT COUNT(*) FROM Rooms WHERE RoomName = ?";
            int Roomcount = 0;
            try (PreparedStatement ps2 = conn.prepareStatement(sql2)) {
                ps2.setString(1, roomName);
                try (ResultSet rs2 = ps2.executeQuery()) {
                    if (rs2.next()) {
                        Roomcount = rs2.getInt(1);
                    }
                }
            }
            
            if (Roomcount > 0) {
                String sqlInsert = "INSERT INTO Rooms (UserID, RoomName) VALUES (?, ?)";
                try (PreparedStatement psInsert = conn.prepareStatement(sqlInsert)) {
                    psInsert.setString(1, userId);
                    psInsert.setString(2, roomName);
                    psInsert.executeUpdate();
                }
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addMsg(String username, String message, Timestamp timeMessage, String RoomName) {
        String sql = "INSERT INTO Message(MsgUser, Msg, timeMsg, RoomName) VALUES (?, ?, ?, ?)";
    
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username); 
            ps.setString(2, message);
            ps.setTimestamp(3, timeMessage);
            ps.setString(4, RoomName);
            int added = ps.executeUpdate();  

            if (added > 0) {
                return true;
            } else {
                return false;
            }    
        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }
    } 

    public boolean addImgMsg(String username, byte[] img, Timestamp timeMessage, String RoomName) {
        String sql = "INSERT INTO MsgImage(MsgUser, ImageData, timeMsg, RoomName) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username); 
            ps.setBytes(2, img);
            ps.setTimestamp(3, timeMessage);
            ps.setString(4, RoomName);
            int added = ps.executeUpdate(); 

            if (added > 0) {
                return true;
            } else {
                return false;
            }    
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    } 

   public List<String> getChatrooms(String username) {
        List<String> roomList = new ArrayList<>();
        String sql = "SELECT RoomName FROM Rooms WHERE Rooms.UserID = ? ORDER BY RoomName";
    
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
    
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String rooms = rs.getString("RoomName");
                    roomList.add(rooms);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomList;
    }
    
        
    public List<Message> getChatLog(String roomName) {
        List<Message> chatLogs = new ArrayList<>();
        String sql = "SELECT MsgUser, Msg, NULL AS ImageData, timeMsg FROM Message WHERE RoomName = ? " +
            "UNION ALL " +
            "SELECT MsgUser, NULL AS Msg, ImageData, timeMsg FROM MsgImage WHERE RoomName = ? " +
            "ORDER BY timeMsg ASC";
        System.out.println("Trying to fetch " + roomName);
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, roomName);
            ps.setString(2, roomName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String username = rs.getString("MsgUser");
                    byte[] bytes = rs.getBytes("ImageData");  
                    String message = rs.getString("Msg");      
                    Timestamp timestamp = rs.getTimestamp("TimeMsg");
                    if(bytes == null){
                        Message msg = new TextMessage(username, roomName, message, timestamp);
                        chatLogs.add(msg);
                        System.out.println(message);
                    } else if (message == null){
                        Message msg = new ImageMessage(username, roomName, bytes, timestamp);
                        chatLogs.add(msg);
                        System.out.println(bytes);
                    }  
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chatLogs;
    }


    public List<UserInterface> UserList(String roomName) {
        List<UserInterface> userList = new ArrayList<>();
        String sql = "SELECT UserID FROM Rooms WHERE RoomName = ? ORDER BY UserID";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, roomName);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String user = rs.getString("UserID");
                    userList.add(new UserResponse(user, true, null));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
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