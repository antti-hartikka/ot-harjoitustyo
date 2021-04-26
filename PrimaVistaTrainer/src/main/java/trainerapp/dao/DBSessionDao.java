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
                            "user_id INTEGER, " +
                            "average_miss REAL, " +
                            "timestamp TIMESTAMP, " +
                            "FOREIGN KEY (user_id) REFERENCES Users(id)" +
                            ")"
            );
        } catch (SQLException ignored) {

        }
    }

    public ArrayList<Session> getSessions(String username) {
        ArrayList<Session> list = new ArrayList<>();
        try {
            PreparedStatement s = db.prepareStatement(
                    "SELECT Users.username, Sessions.timestamp, Sessions.average_miss " +
                    "FROM Sessions " +
                    "JOIN Users ON Users.id = Sessions.user_id " +
                    "WHERE Users.username=?"
            );
            s.setString(1, username);
            ResultSet r = s.executeQuery();
            while (r.next()) {
                Session session = new Session(
                        r.getString(1),
                        r.getTimestamp(2).toLocalDateTime(),
                        r.getDouble(3)
                );
                list.add(session);
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong at DBSessionDao.getSessions: " + e.toString());
            e.printStackTrace();
        }
        return list;
    }

    public void addSession(Session session) {
        try {
            PreparedStatement s = db.prepareStatement(
                    "INSERT INTO Sessions (user_id, average_miss, timestamp) " +
                            "VALUES ((SELECT id FROM Users WHERE username=?), ?, ?)"
            );
            s.setString(1, session.getUser());
            s.setDouble(2, session.getAverageMiss());
            s.setDate(3, new Date(System.currentTimeMillis()));
            s.execute();
        } catch (SQLException e) {
            System.out.println("Something went wrong at DBSessionDao.addSession: " + e.toString());
        }
    }
}
