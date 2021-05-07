package trainerapp.dao;

import java.util.ArrayList;
import java.sql.*;

/**
 * Data Access Object for storing and accessing user related data using SQLite database.
 */
public class DBUserDao implements UserDao {

    Connection db;

    /**
     *
     * @param url Url to database where this class will be connected
     */
    public DBUserDao(String url) {
        try {
            db = DriverManager.getConnection(url);
            Statement s = db.createStatement();
            s.execute("CREATE TABLE Users (id INTEGER PRIMARY KEY, username TEXT UNIQUE)");
        } catch (SQLException ignored) {
        }
    }

    /**
     * Method for creating new user to the database.
     * @param username Username to be added to database
     * @return Returns false if username already exists or don't pass validation([a-zA-Z0-9]{3,20})
     */
    public boolean createUser(String username) {
        if (!username.matches("[a-zA-Z0-9]{3,20}")) {
            return false;
        }
        try {
            PreparedStatement s = db.prepareStatement(
                    "INSERT INTO Users (username) VALUES (?)"
            );
            s.setString(1, username);
            s.execute();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    /**
     * Method to get all users as ArrayList
     * @return ArrayList contains all the usernames
     */
    public ArrayList<String> getAllUsers() {
        ArrayList<String> users = new ArrayList<>();
        try {
            PreparedStatement s = db.prepareStatement("SELECT username FROM Users");
            ResultSet r = s.executeQuery();
            while (r.next()) {
                users.add(r.getString("username"));
            }

        } catch (SQLException ignored) {
        }
        return users;
    }
}
