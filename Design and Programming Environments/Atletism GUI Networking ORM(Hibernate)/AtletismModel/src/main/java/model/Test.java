package model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class Test {
    public static void main(String[] args) {

        initialize();
        Test t = new Test();
        //t.addMessage();
        t.getMessages();
        close();
    }


    void getMessages(){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                System.out.println("inainte");
                List<Copil> messages =
                        session.createQuery("from Copil", Copil.class).
                                //  setFirstResult(1).setMaxResults(5).
                                        list();
                System.out.println("dupa");
                System.out.println(messages.size() + " message(s) found:");

                for (Copil m : messages) {
                    System.out.println(m.toString());
                }
                tx.commit();
            } catch (RuntimeException ex) {
                System.out.println("S-a intrat in catch");
                System.out.println(ex.getMessage());
                if (tx != null)
                    tx.rollback();
            }
        }

    }



    void addMessage(){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Copil of= new Copil(1,"A","B",11);
                session.save(of);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }

        static SessionFactory sessionFactory;
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

        static void close(){
            if ( sessionFactory != null ) {
                sessionFactory.close();
            }

        }
    }
