package trainerapp.dao;

import trainerapp.domain.Session;
import trainerapp.domain.User;

import java.util.ArrayList;

public interface SessionDao {

    ArrayList<Session> getSessions(String username);
    void addSession(Session session);

}
