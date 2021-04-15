package trainerapp.domain;

import trainerapp.dao.DBSessionDao;
import trainerapp.dao.DBUserDao;
import trainerapp.dao.SessionDao;
import trainerapp.dao.UserDao;

import java.util.ArrayList;

public class DataAPI {

    UserDao userDao = new DBUserDao("jdbc:sqlite:database.db");
    SessionDao sessionDao = new DBSessionDao("jdbc:sqlite:database.db");

    public ArrayList<String> getAllUsers() {
        return userDao.getAllUsers();
    }

    public boolean createUser(String username) {
        return userDao.createUser(username);
    }

    ArrayList<Session> getSessions(String username) {
        return sessionDao.getSessions(username);
    }

    void addSession(Session session) {
        sessionDao.addSession(session);
    }

}
