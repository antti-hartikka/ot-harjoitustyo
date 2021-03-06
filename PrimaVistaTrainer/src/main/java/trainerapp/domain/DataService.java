package trainerapp.domain;

import trainerapp.dao.SessionDao;
import trainerapp.dao.UserDao;

import java.util.ArrayList;

public class DataService {

    private final UserDao userDao;
    private final SessionDao sessionDao;

    public DataService(UserDao userDao, SessionDao sessionDao) {
        this.userDao = userDao;
        this.sessionDao = sessionDao;
    }

    public ArrayList<String> getAllUsers() {
        return userDao.getAllUsers();
    }

    public boolean createUser(String username) {
        return userDao.createUser(username);
    }

    public ArrayList<Session> getSessions(String username) {
        return sessionDao.getSessions(username);
    }

    public void addSession(Session session) {
        sessionDao.addSession(session);
    }

}
