package repository.jbdc;

import model.Copil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.ICopilRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class CopilDbRepository implements ICopilRepository<Copil> {

    private static final Logger logger = LogManager.getLogger();
    JdbcUtils jdbcUtils;

    public CopilDbRepository(Properties props) {
        jdbcUtils = new JdbcUtils(props);
    }


    @Override
    public Copil findOne(int id) {
        Connection conn =jdbcUtils.getConnection();
        String sql = "SELECT * FROM Copil WHERE id is ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                return new Copil(rs.getInt("id"), rs.getString("nume"), rs.getString("prenume"), rs.getInt("varsta"));
        } catch (SQLException ex) {
            logger.error(ex);
        }
        return null;
    }

    public Copil findOnebyName(String fname, String lname, int age) {
        Connection conn =jdbcUtils.getConnection();
        String sql = "SELECT * FROM Copil WHERE nume is ? and prenume is ? and varsta is ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fname);
            pstmt.setString(2, lname);
            pstmt.setInt(3, age);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                return new Copil(rs.getInt("id"), rs.getString("nume"), rs.getString("prenume"), rs.getInt("varsta"));
        } catch (SQLException ex) {
            logger.error(ex);
        }
        return null;
    }

    @Override
    public ArrayList<Copil> findByAge(int minAge, int maxAge) {
        Connection conn =jdbcUtils.getConnection();
        ArrayList<Copil> list = new ArrayList<>();
        String sql = "SELECT * FROM Copil WHERE varsta between ? and ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, minAge);
            pstmt.setInt(2, maxAge);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
                list.add(new Copil(rs.getInt("id"), rs.getString("nume"), rs.getString("prenume"), rs.getInt("varsta")));
        } catch (SQLException ex) {
            logger.error(ex);
        }
        return list;
    }

    @Override
    public Iterable<Copil> findAll() {
        return null;
    }

    @Override
    public Copil save(Copil entity) {
        Connection conn =jdbcUtils.getConnection();
        String sql = "INSERT INTO Copil(nume,prenume,varsta) VALUES(?,?,?)";
        if (findOnebyName(entity.getFirstName(), entity.getLastName(), entity.getAge()) == null)
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, entity.getFirstName());
                pstmt.setString(2, entity.getLastName());
                pstmt.setInt(3, entity.getAge());
                pstmt.executeUpdate();
                return findOnebyName(entity.getFirstName(), entity.getLastName(), entity.getAge());
            } catch (SQLException ex) {
                logger.error(ex);
            }
        return null;
    }

    @Override
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
