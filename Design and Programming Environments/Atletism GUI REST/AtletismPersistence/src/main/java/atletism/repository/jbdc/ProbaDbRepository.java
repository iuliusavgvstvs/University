package atletism.repository.jbdc;

import atletism.repository.IProbaRepository;
import model.Proba;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

@Component
public class ProbaDbRepository implements IProbaRepository<Proba> {
    private static final Logger logger = LogManager.getLogger();
    JdbcUtils jdbcUtils;

    public ProbaDbRepository() {

            Properties serverProps=new Properties();
            try {
                serverProps.load(ProbaDbRepository.class.getResourceAsStream("/atletismServer.properties"));
                System.out.println("Server properties set. ");
                serverProps.list(System.out);
            } catch (IOException e) {
                System.err.println("Cannot find chatserver.properties "+e);
            }
            jdbcUtils = new JdbcUtils(serverProps);

    }
    public JdbcUtils getJdbc(){
        return jdbcUtils;
    }



    @Override
    public ArrayList<Proba> getByCopilID(int id) {
        Connection conn=jdbcUtils.getConnection();
        String sql = "SELECT * FROM Proba WHERE idCopil is ?";
        ArrayList<Proba> list = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Proba p = new Proba(rs.getInt("id"), rs.getInt("idCopil"), rs.getInt("Distanta"));
                list.add(p);
            }
        } catch (SQLException ex) {
            logger.error(ex);
        }
        if (list.isEmpty()) {
            return null;
        } else
            return list;
    }

    @Override
    public ArrayList<Proba> getByDistanta(int distanta) {
        Connection conn=jdbcUtils.getConnection();
        String sql = "SELECT * FROM Proba WHERE Distanta is ?";
        ArrayList<Proba> list = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, distanta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Proba p = new Proba(rs.getInt("id"), rs.getInt("idCopil"), rs.getInt("Distanta"));
                list.add(p);
            }
        } catch (SQLException ex) {
            logger.error(ex);
        }
        if (list.isEmpty()) {
            return null;
        } else
            return list;
    }

    @Override
    public Proba findOneByProba(int idCopil, int distanta) {
        Connection conn=jdbcUtils.getConnection();
        String sql = "SELECT * FROM Proba WHERE idCopil is ? and Distanta is ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCopil);
            pstmt.setInt(2, distanta);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                return new Proba(rs.getInt("id"), rs.getInt("idCopil"), rs.getInt("Distanta"));
        } catch (SQLException ex) {
            logger.error(ex);
        }
        return null;
    }

    @Override
    public Proba findOne(int id) {
        Connection conn=jdbcUtils.getConnection();
        String sql = "SELECT * FROM Proba WHERE id is ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                return new Proba(rs.getInt("id"), rs.getInt("idCopil"), rs.getInt("Distanta"));
        } catch (SQLException ex) {
            logger.error(ex);
        }
        return null;
    }

    @Override
    public Iterable<Proba> findAll() {
        return null;
    }

    @Override
    public Proba save(Proba entity) {
        Connection conn=jdbcUtils.getConnection();
        String sql = "INSERT INTO Proba(idCopil,Distanta) VALUES(?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, entity.getCopilID());
            pstmt.setInt(2, entity.getDistanta());
            pstmt.executeUpdate();
            return findOneByProba(entity.getCopilID(), entity.getDistanta());
        } catch (SQLException ex) {
            logger.error(ex);
        }
        return null;
    }

    @Override
    public void clear() {
        Connection conn=jdbcUtils.getConnection();
        String sql = "DELETE from Proba";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
        }
    }

    public Proba[] getAll() {
        Connection conn = jdbcUtils.getConnection();
        String sql = "SELECT * FROM Proba";
        ArrayList<Proba> list = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Proba p = new Proba(rs.getInt("id"), rs.getInt("idCopil"), rs.getInt("Distanta"));
                list.add(p);
            }
        } catch (SQLException ex) {
            logger.error(ex);
        }
        if (list.isEmpty()) {
            return null;
        } else{
            Proba[] all = list.toArray(new Proba[0]);
            return all;
           // return (Proba[]) list.toArray();
        }

    }

    @Override
    public void update(int id, Proba proba) {
        Connection conn=jdbcUtils.getConnection();
        String sql = "UPDATE Proba set Distanta = ?, idCopil = ? where id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, proba.getDistanta());
            pstmt.setInt(2, proba.getCopilID());
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
        }
    }

    @Override
    public void delete(String probaname) {
        Connection conn=jdbcUtils.getConnection();
        String sql = "DELETE FROM Proba WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(probaname));
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
        }
    }
}
