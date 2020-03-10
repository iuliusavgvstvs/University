package repository;

import Properties.DBProp;
import domain.Copil;
import domain.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class CopilDbRepository< E extends Entity> implements ICrudRepository<Copil> {

    private  Connection conn;
    private static final Logger logger = LogManager.getLogger();
    public CopilDbRepository() throws SQLException {
        this.conn = new DBProp().getNewConnection();
    }

    @Override
    public Copil findOne(int id) throws SQLException {
        logger.info("S-a executat findOne("+id+")");
        String sql = "SELECT * FROM Copil WHERE id is ?";
        try (PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);
            ResultSet rs  = pstmt.executeQuery();
            if(rs.next()) {
                Copil c = new Copil(rs.getInt("id"), rs.getString("nume"), rs.getString("prenume"), rs.getInt("varsta"));
                logger.info("S-a gasit "+ c.toString());
                return c;
            }
            } catch (SQLException ex) {
                logger.error(ex);
            }
        logger.info("Nu s-a gasit nimic!");
        return null;
    }
    public Copil findOnebyName(String fname, String lname, int age) {
        logger.info("S-a executat findOnebyName("+fname+", "+lname+","+age+")");
        String sql = "SELECT * FROM Copil WHERE nume is ? and prenume is ? and varsta is ?";
        try (PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setString(1,fname);
            pstmt.setString(2,lname);
            pstmt.setInt(3,age);
            ResultSet rs  = pstmt.executeQuery();
            if(rs.next()) {
                Copil c = new Copil(rs.getInt("id"), rs.getString("nume"), rs.getString("prenume"), rs.getInt("varsta"));
                logger.info("S-a gasit " + c.toString());
                return c;
            }
        } catch (SQLException ex) {
            logger.error(ex);
        }
        logger.info("Nu s-a gasit nimic!");
        return null;
    }

    @Override
    public Iterable<Copil> findAll()  {
        return null;
    }

    @Override
    public Copil save(Copil entity) {
        logger.info("S-a apelat save la "+entity.toString());
        String sql = "INSERT INTO Copil(nume,prenume,varsta) VALUES(?,?,?)";
        if(findOnebyName(entity.getFirstName(),entity.getLastName(),entity.getAge())==null)
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getFirstName());
            pstmt.setString(2, entity.getLastName());
            pstmt.setInt(3,entity.getAge());
            pstmt.executeUpdate();
            logger.info("Salvat cu succes!");
        } catch (SQLException ex) {
                logger.error(ex);
        }
        logger.info("Nu s-a reusit salvarea.");
        return null;
    }

    @Override
    public Copil delete(int id) {
        logger.info("S-a apelat delete la id:"+id);
        String sql = "DELETE FROM Copil WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            logger.info("Sters cu succes!");

        } catch (SQLException ex) {
            logger.error(ex);
        }
        logger.info("Nu s-a reusit stergerea.");
        return null;
    }

    @Override
    public Copil update(Copil entity){
        logger.info("S-a apelat update la "+entity.toString());
        String sql = "UPDATE Copil SET nume = ? , "
                + "prenume = ? "
                + "varsta = ? "
                + "WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, entity.getFirstName());
            pstmt.setString(2, entity.getLastName());
            pstmt.setInt(3, entity.getAge());
            pstmt.setInt(4,  entity.getId());
            pstmt.executeUpdate();
            logger.info("Modificat cu succes!");
        } catch (SQLException ex) {
            logger.error(ex);
        }
        logger.info("Nu s-a reusit modificarea!");
        //return findOne(entity.getId());
        return null;
    }

}
