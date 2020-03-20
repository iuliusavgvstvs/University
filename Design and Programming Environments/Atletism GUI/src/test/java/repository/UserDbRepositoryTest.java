package repository;

import domain.Copil;
import domain.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class UserDbRepositoryTest {
    static UserDbRepository repo;
    static User u1, u2, u3;

    @BeforeClass
    public static void setup() throws SQLException {
        repo = new UserDbRepository();
        repo.getConn().setAutoCommit(false);
        repo.clear();
        u1 = repo.save(new User(1,"user1","123456"));
        u2 = repo.save(new User(2,"user2", "123456789"));
        u3 = repo.save(new User(3,"user3","password"));
    }

    @AfterClass
    public static void clear() throws SQLException {
        repo.getConn().rollback();
        repo.getConn().close();
    }

    @Test
    public void save() {
        User u4 = repo.save(new User(4,"user4","pass1234"));
        assertEquals(repo.findOnebyName(u4.getUser()), u4);
        //saving a user with the same username but diffrent password
        //SHOULD FAIL
        assertNull(repo.save(new User(5,"user4","abcdef1234")));
    }

    @Test
    public void getLogin() {
        User uu1 = new User(1,"user1", "12345");
        assertFalse(repo.getLogin(uu1));
        assertTrue(repo.getLogin(u1));
        assertTrue(repo.getLogin(u2));
        assertTrue(repo.getLogin(u3));
    }

    @Test
    public void findOnebyName() {
        assertEquals(repo.findOnebyName("user1"),u1);
        assertEquals(repo.findOnebyName("user2"),u2);
        assertEquals(repo.findOnebyName("user3"),u3);
    }
}