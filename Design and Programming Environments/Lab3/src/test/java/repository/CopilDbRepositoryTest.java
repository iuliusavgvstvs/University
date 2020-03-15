package repository;

import domain.Copil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class CopilDbRepositoryTest {
    static CopilDbRepository repo;
    static Copil c1, c2, c3;

    @BeforeClass
    public static void setup() throws SQLException {
        repo = new CopilDbRepository();
        repo.getConn().setAutoCommit(false);
        c1 = repo.save(new Copil(1,"A", "X", 11));
        c2 = repo.save(new Copil(2,"B", "Y", 8));
        c3 = repo.save(new Copil(3,"C","Z",9));
    }

    @AfterClass
    public static void clear() throws SQLException {
        repo.getConn().rollback();
        repo.getConn().close();
    }

    @Test
    public void findOne() {
        assertEquals(repo.findOne(c1.getId()),c1);
        assertEquals(repo.findOne(c2.getId()),c2);
        assertEquals(repo.findOne(c3.getId()),c3);
    }

    @Test
    public void findOnebyName() {
        assertEquals(repo.findOnebyName("A","X",11),c1);
        assertEquals(repo.findOnebyName("B","Y",8),c2);
        assertEquals(repo.findOnebyName("C","Z",9),c3);
    }

    @Test
    public void save() {
        Copil c4 = repo.save(new Copil(4,"D","T",13));
        assertEquals(repo.findOne(c4.getId()),c4);
        assertEquals(repo.findOnebyName(c4.getFirstName(),c4.getLastName(),c4.getAge()),c4);
    }


}

