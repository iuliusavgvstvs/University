import atletism.repository.jbdc.ProbaDbRepository;
import atletism.rest.ProbaController;
import model.Proba;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
public class RestTest {
    static ProbaController controller;
        static Proba p1,p2,p3;

        @BeforeClass
        public static void setup() throws SQLException {
            ProbaDbRepository repo = new ProbaDbRepository();
            controller = new ProbaController();
            controller.setRepo(repo);
            controller.getRepo().getJdbc().getConnection().setAutoCommit(false);
            controller.getRepo().clear();
            p1 = controller.create(new Proba(1,1, 50));
            p2 = controller.create(new Proba(2,2, 100));
            p3 = controller.create(new Proba(3,3,1500));
        }

        @AfterClass
        public static void clear() throws SQLException {
            controller.getRepo().getJdbc().getConnection().rollback();
            controller.getRepo().getJdbc().getConnection().close();
        }

        @Test
        public void controllertest() {
            assertEquals(controller.getAll().length,3);
            ResponseEntity<?> p4 = controller.getById(p1.getId());
            assertEquals(p4.getStatusCodeValue(), 200);
            controller.delete(Integer.toString(p3.getId()));
            assertEquals(controller.getAll().length,2);
            p2.setCopilID(99);
            controller.update(p2);
            assertEquals(controller.getById(p2.getId()).getStatusCodeValue(),200);
            controller.delete(Integer.toString(p1.getId()));
            assertEquals(controller.getAll().length,1);
            assertEquals(controller.getById(p1.getId()).getStatusCodeValue(),404);
            assertEquals(controller.getById(p3.getId()).getStatusCodeValue(),404);
            controller.delete(Integer.toString(p2.getId()));
            assertEquals(controller.getById(p2.getId()).getStatusCodeValue(),404);
        }

    }
