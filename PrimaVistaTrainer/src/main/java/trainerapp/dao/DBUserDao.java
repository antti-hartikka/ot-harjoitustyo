package trainerapp.dao;

import java.util.ArrayList;
import java.sql.*;

public class DBUserDao implements UserDao {

    Connection db;

    public DBUserDao(String url) {
        try {
            db = DriverManager.getConnection(url);
            Statement s = db.createStatement();
            s.execute("CREATE TABLE Users (id INTEGER PRIMARY KEY, username TEXT UNIQUE)");
        } catch (SQLException ignored) {
        }
    }

    public boolean createUser(String username) {
        if (username.length() > 20 || !username.matches("[a-zA-Z0-9]{3,20}")) return false;
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

    public ArrayList<String> getAllUsers() {
        ArrayList<String> users = new ArrayList<>();
        try {
            PreparedStatement s = db.prepareStatement("SELECT username FROM Users");
            ResultSet r = s.executeQuery();
            while (r.next()) {
                users.add(r.getString("username"));
            }

        } catch (SQLException e) {
            System.out.println("Somethin went wrong at DBUserDao.getAllUsers(): " + e.toString());
        }
        return users;
    }
}
