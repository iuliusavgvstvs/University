package repository.jbdc;

import model.Copil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.ICopilRepository;

import javax.persistence.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CopilDbRepository implements ICopilRepository<Copil> {

    private static final Logger logger = LogManager.getLogger();
    JdbcUtils jdbcUtils;
    static SessionFactory sessionFactory;

    public CopilDbRepository(Properties props) {
        jdbcUtils = new JdbcUtils(props);
        initialize();
    }

    static void initialize() {

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    public Copil findOne(int id) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Copil> messages =
                        session.createQuery("from Copil", Copil.class).
                                //  setFirstResult(1).setMaxResults(5).
                                        list();
                tx.commit();
                return messages.get(0);
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    public Copil findOnebyName(String fname, String lname, int age) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String sql = "from Copil c where c.firstName = :fn and c.lastName = :ln and c.age = :ag";

                Query query = session.createQuery(sql);
                query.setParameter("fn", fname);
                query.setParameter("ln", lname);
                query.setParameter("ag", age);

                List<Copil> messages =query.getResultList();
                tx.commit();
                return messages.get(0);
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }


    @Override
    public ArrayList<Copil> findByAge(int minAge, int maxAge) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String sql = "from Copil c where c.age between :min and :max";
                Query query = session.createQuery(sql);
                query.setParameter("min", minAge);
                query.setParameter("max", maxAge);
                List<Copil> messages =query.getResultList();
                tx.commit();
                if(!messages.isEmpty())
                return (ArrayList<Copil>) messages;
                else
                    return null;
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    public Copil save(Copil entity) {
        Copil c = entity;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(c);
                tx.commit();
                return c;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    public void clear() {
        Connection conn =jdbcUtils.getConnection();
        String sql = "DELETE from Copil";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
        }
    }


}
