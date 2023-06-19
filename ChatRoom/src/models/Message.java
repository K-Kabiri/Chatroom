package models;

import controller.MySQLConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Message {
    private String username;
    private int userID;
    private String message;
    private static int messageID = 1;

    public Message(String username , int userID, String message) {
        this.userID = userID;
        this.username = username;
        this.message = message;
    }

    public void insertMessage() throws SQLException, ClassNotFoundException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        String sql = String.format("INSERT INTO `messages` (`messageID`, `userID`, `username`, `message`) VALUES (%s, %s,'%s' ,'%s')" , messageID++ , userID , username , message );
        mySQLConnection.executeSQL(sql);
    }
    public ResultSet selectMessage(int userID ,String username) throws SQLException, ClassNotFoundException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        String query = String.format("SELECT username , message FROM `messages`");
        return mySQLConnection.executeQuery(query);
    }
}
