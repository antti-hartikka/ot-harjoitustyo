package trainerapp.domain;

import trainerapp.dao.UserDao;

import java.util.ArrayList;

public class FakeUserDao implements UserDao {

    ArrayList<String> users;

    public FakeUserDao() {
        users = new ArrayList<>();
    }

    public boolean createUser(String username) {
        if (users.contains(username) || !username.matches("[a-zA-Z0-9]{3,20}")) {
            return false;
        }
        users.add(username);
        return true;
    }

    public ArrayList<String> getAllUsers() {
        return users;
    }

}
