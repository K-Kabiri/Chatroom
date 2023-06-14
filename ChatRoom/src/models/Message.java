package models;

import controller.MySQLConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Message {
    public void insertMessage(int userID ,String username) throws SQLException, ClassNotFoundException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        String sql = String.format("INSERT INTO `users` (userID, username) VALUES (%s , '%s')" , userID , username);
        mySQLConnection.executeSQL(sql);
    }
    public ResultSet selectMessage(int userID ,String username) throws SQLException, ClassNotFoundException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        String query = String.format("SELECT username , message FROM `messages`");
        return mySQLConnection.executeQuery(query);
    }
}
