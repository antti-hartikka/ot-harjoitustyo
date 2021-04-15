package trainerapp.domain;

public class User {

    private String username;

    public User(String username) {
        this.username = username;
    }

    public String name() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
