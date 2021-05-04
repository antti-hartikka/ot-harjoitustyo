package trainerapp.domain;

import trainerapp.dao.SessionDao;

import java.util.ArrayList;

public class FakeSessionDao implements SessionDao {

    ArrayList<Session> sessions;

    public FakeSessionDao() {
        sessions = new ArrayList<>();
    }

    public ArrayList<Session> getSessions(String username){
        ArrayList<Session> list = new ArrayList<>();
        for (Session s : sessions) {
            if (s.getUser().equals(username)) {
                list.add(s);
            }
        }
        return list;
    }

    public void addSession(Session session){
        sessions.add(session);
    }
}
