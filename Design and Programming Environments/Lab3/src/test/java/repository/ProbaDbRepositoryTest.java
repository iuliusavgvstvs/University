package repository;

import domain.Proba;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class ProbaDbRepositoryTest {
    static Proba p1, p2, p3, p4, p5;
    static ProbaDbRepository repo;

    @BeforeClass
    public static void setup() throws SQLException {
        repo = new ProbaDbRepository();
        repo.getConn().setAutoCommit(false);
        p1 = repo.save(new Proba(1,1, 50));
        p2 = repo.save(new Proba(2,1,100));
        p3 = repo.save(new Proba(3, 2, 1000));
        p4 = repo.save(new Proba(4, 2, 1500));
        p5 = repo.save(new Proba(5, 3, 50));
    }

    @AfterClass
    public static void clear() throws SQLException {
        repo.getConn().rollback();
        repo.getConn().close();
    }

    @Test
    public void getByCopilID() {
        assertEquals(repo.getByCopilID(1).size(),2);
        assertEquals(repo.getByCopilID(2).size(),2);
        assertEquals(repo.getByCopilID(3).size(),1);
    }

    @Test
    public void getByDistanta() {
        assertEquals(repo.getByDistanta(50).size(),2);
        assertEquals(repo.getByDistanta(100).size(), 1);
        assertEquals(repo.getByDistanta(1000).size(),1);
        assertEquals(repo.getByDistanta(1500).size(),1);
        assertEquals(repo.getByDistanta(100).get(0),p2);
        assertEquals(repo.getByDistanta(1000).get(0),p3);
        assertEquals(repo.getByDistanta(1500).get(0),p4);
    }

    @Test
    public void findOneByProba() {
        assertEquals(repo.findOneByProba(1,50),p1);
        assertEquals(repo.findOneByProba(1,100),p2);
        assertEquals(repo.findOneByProba(2,1000),p3);
        assertEquals(repo.findOneByProba(2,1500),p4);
        assertEquals(repo.findOneByProba(3,50),p5);
    }

    @Test
    public void findOne() {
        assertEquals(repo.findOne(p1.getId()),p1);
        assertEquals(repo.findOne(p2.getId()),p2);
        assertEquals(repo.findOne(p3.getId()),p3);
        assertEquals(repo.findOne(p4.getId()),p4);
        assertEquals(repo.findOne(p5.getId()),p5);
    }

    @Test
    public void save() {
        Proba p6 = repo.save( new Proba(6, 4, 1000));
        assertEquals(repo.findOne(p6.getId()),p6);
        assertEquals(repo.findOneByProba(p6.getCopilID(),p6.getDistanta()),p6);
    }

}