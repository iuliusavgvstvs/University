package repository;

import Properties.DBProp;
import domain.Copil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class CopilDbRepository implements ICopilRepository<Copil> {

    private  Connection conn;
    private static final Logger logger = LogManager.getLogger();
    public CopilDbRepository() {
        this.conn = new DBProp().getNewConnection();
    }

    public Connection getConn() {
        return conn;
    }

    @Override
    public Copil findOne(int id) {
        String sql = "SELECT * FROM Copil WHERE id is ?";
        try (PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);
            ResultSet rs  = pstmt.executeQuery();
            if(rs.next())
                return new Copil(rs.getInt("id"), rs.getString("nume"), rs.getString("prenume"), rs.getInt("varsta"));
            } catch (SQLException ex) {
                logger.error(ex);
            }
        return null;
    }
    public Copil findOnebyName(String fname, String lname, int age) {
        String sql = "SELECT * FROM Copil WHERE nume is ? and prenume is ? and varsta is ?";
        try (PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setString(1,fname);
            pstmt.setString(2,lname);
            pstmt.setInt(3,age);
            ResultSet rs  = pstmt.executeQuery();
            if(rs.next())
                return new Copil(rs.getInt("id"), rs.getString("nume"), rs.getString("prenume"), rs.getInt("varsta"));
        } catch (SQLException ex) {
            logger.error(ex);
        }
        return null;
    }

    @Override
    public Iterable<Copil> findAll()  {
        return null;
    }

    @Override
    public Copil save(Copil entity) {
        String sql = "INSERT INTO Copil(nume,prenume,varsta) VALUES(?,?,?)";
        if(findOnebyName(entity.getFirstName(),entity.getLastName(),entity.getAge())==null)
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getFirstName());
            pstmt.setString(2, entity.getLastName());
            pstmt.setInt(3,entity.getAge());
            pstmt.executeUpdate();
            return findOnebyName(entity.getFirstName(),entity.getLastName(),entity.getAge());
        } catch (SQLException ex) {
                logger.error(ex);
        }
        return null;
    }


}
