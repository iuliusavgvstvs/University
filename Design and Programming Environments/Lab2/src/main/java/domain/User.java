package domain;

public class User extends Entity {
    private String user;
    private String password;

    public User(int id, String user, String password) {
        super(id);
        this.user = user;
        this.password = password;
    }

}
