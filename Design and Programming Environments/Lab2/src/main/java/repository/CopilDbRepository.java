package repository;

import domain.Copil;
import domain.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class CopilDbRepository<ID, E extends Entity<ID>> implements ICrudRepository<ID, E> {

    private  Connection conn;
    private static final Logger logger = LogManager.getLogger();
    public CopilDbRepository() throws SQLException {
        this.conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/Iuliu/OneDrive/Desktop/Facultate/Sem IV/MPP/Laboratoare/Lab2/src/main/resources/atletism.db");
    }

    @Override
    public E findOne(ID id) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("select * from Copil")) {
                while (rs.next()) {
                    if(rs.getInt("id")==Integer.parseInt((String) id))
                        return (E) new Copil<String>(Integer.toString(rs.getInt("id")), rs.getString("nume"), rs.getString("prenume"), rs.getInt("varsta"));

                }
            } catch (SQLException ex) {
                logger.error(ex);
                System.err.println(ex.getSQLState());
                System.err.println(ex.getErrorCode());
                System.err.println(ex.getMessage());
            }
        }
        return null;
    }

    @Override
    public Iterable<E> findAll()  {
        return null;
    }

    @Override
    public E save(E entity) {
        return null;
    }

    @Override
    public E delete(Object o) {
        return null;
    }

    @Override
    public E update(E entity) {
        return null;
    }
}
