package models;

import mysql.MySQLConnection;

import java.sql.SQLException;

public class User {
    private int userID;
    private String username;
    public User(String username , int userID) {
        this.username = username;
        this.userID = userID;
    }
    public void insertUser(int userID ,String username) throws SQLException, ClassNotFoundException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        String sql = String.format("INSERT INTO `users` (userID, username) VALUES (%s , '%s')" , userID , username);
        mySQLConnection.executeSQL(sql);
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
