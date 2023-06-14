package models;

import controller.MySQLConnection;

import java.sql.SQLException;

public class User {
    int userID;
    String username;
    public User() {
    }
    public void insertUser(int userID ,String username) throws SQLException, ClassNotFoundException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        String sql = String.format("INSERT INTO `users` (userID, username) VALUES (%s , '%s')" , userID , username);
        mySQLConnection.executeSQL(sql);
    }
}
