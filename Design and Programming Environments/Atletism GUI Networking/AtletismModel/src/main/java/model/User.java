package model;

import java.util.Objects;

public class User extends Entity {
    private String user;
    private String password;

    public User(int id, String user, String password) {
        super(id);
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user1 = (User) o;
        return user.equals(user1.user) &&
                password.equals(user1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, password);
    }
}
