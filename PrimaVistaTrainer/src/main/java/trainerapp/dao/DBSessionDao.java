package trainerapp.dao;

import trainerapp.domain.Session;

import java.sql.*;
import java.util.ArrayList;

public class DBSessionDao implements SessionDao {

    Connection db;

    public DBSessionDao(String url) {
        try {
            db = DriverManager.getConnection(url);
            Statement s = db.createStatement();
            s.execute(
                    "CREATE TABLE Sessions (" +
                            "id INTEGER PRIMARY KEY, " +
                            "user_id INTEGER FOREIGN KEY REFERENCES Users, " +
                            "average_miss INTEGER, " +
                            "date DATE" +
                            ")"
            );
        } catch (SQLException ignored) {

        }
    }

    public ArrayList<Session> getSessions(String username) {
        try {
            PreparedStatement s = db.prepareStatement(
                    "SELECT Users.username, Sessions.average_miss, Sessions.date " +
                    "FROM Sessions " +
                    "JOIN Users ON Users.id = Sessions.user_id " +
                    "WHERE Users.username=?"
            );
            s.setString(1, username);
            s.executeQuery();
        } catch (SQLException e) {
            System.out.println("Something went wrong at DBSessionDao.getSessions: " + e.toString());
        }
        return new ArrayList<>();
    }

    public void addSession(Session session) {
        try {
            PreparedStatement s = db.prepareStatement(
                    "INSERT INTO Sessions (user_id, average_miss, date) " +
                            "VALUES ((SELECT id FROM Users WHERE username=?), ?, ?)"
            );
            s.setString(1, session.getUser());
            s.setInt(2, session.getAverageMiss());
            s.setDate(3, Date.valueOf(session.getDate()));
            s.execute();
        } catch (SQLException e) {
            System.out.println("Something went wrong at DBSessionDao.addSession: " + e.toString());
        }
    }
}
