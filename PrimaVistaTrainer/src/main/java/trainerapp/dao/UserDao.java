package trainerapp.dao;

import java.util.ArrayList;

public interface UserDao {

    boolean createUser(String username);
    ArrayList<String> getAllUsers();

}
