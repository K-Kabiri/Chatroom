package models;

import mysql.MySQLConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Message {
    private String username;
    private int userID;
    private String message;
    private static int messageID = 1;
    private static int messageIDClient12 = 1;
    private static int messageIDClient13 = 1;
    private static int messageIDClient14 = 1;
    private static int messageIDClient23 = 1;
    private static int messageIDClient24 = 1;
    private static int messageIDClient34 = 1;

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
    public static ResultSet selectMessage() throws SQLException, ClassNotFoundException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        String query = String.format("SELECT username , message FROM `messages` ORDER BY messageID ASC");
        return mySQLConnection.executeQuery(query);
    }
    public void insertMessageIntoClient1_Client2() throws SQLException, ClassNotFoundException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        String sql = String.format("INSERT INTO `client1-client2` (`messageID`, `userID`, `username`, `message`) VALUES (%s, %s,'%s' ,'%s')" , messageIDClient12++ , userID , username , message );
        mySQLConnection.executeSQL(sql);
    }
    public void insertMessageIntoClient1_Client3() throws SQLException, ClassNotFoundException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        String sql = String.format("INSERT INTO `client1-client3` (`messageID`, `userID`, `username`, `message`) VALUES (%s, %s,'%s' ,'%s')" , messageIDClient13++ , userID , username , message );
        mySQLConnection.executeSQL(sql);
    }
    public void insertMessageIntoClient1_Client4() throws SQLException, ClassNotFoundException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        String sql = String.format("INSERT INTO `client1-client4` (`messageID`, `userID`, `username`, `message`) VALUES (%s, %s,'%s' ,'%s')" , messageIDClient14++ , userID , username , message );
        mySQLConnection.executeSQL(sql);
    }
    public void insertMessageIntoClient2_Client3() throws SQLException, ClassNotFoundException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        String sql = String.format("INSERT INTO `client2-client3` (`messageID`, `userID`, `username`, `message`) VALUES (%s, %s,'%s' ,'%s')" , messageIDClient23++ , userID , username , message );
        mySQLConnection.executeSQL(sql);
    }
    public void insertMessageIntoClient2_Client4() throws SQLException, ClassNotFoundException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        String sql = String.format("INSERT INTO `client2-client4` (`messageID`, `userID`, `username`, `message`) VALUES (%s, %s,'%s' ,'%s')" , messageIDClient24++ , userID , username , message );
        mySQLConnection.executeSQL(sql);
    }
    public void insertMessageIntoClient3_Client4() throws SQLException, ClassNotFoundException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        String sql = String.format("INSERT INTO `client3-client4` (`messageID`, `userID`, `username`, `message`) VALUES (%s, %s,'%s' ,'%s')" , messageIDClient34++ , userID , username , message );
        mySQLConnection.executeSQL(sql);
    }
}