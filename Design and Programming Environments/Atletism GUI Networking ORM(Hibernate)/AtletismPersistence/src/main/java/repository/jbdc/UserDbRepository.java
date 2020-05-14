package repository.jbdc;

import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.IUserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class UserDbRepository implements IUserRepository<User> {
    private static final Logger logger = LogManager.getLogger();
    JdbcUtils jdbcUtils;

    public UserDbRepository(Properties props) {
        jdbcUtils=new JdbcUtils(props);
    }

    @Override
    public User findOne(int id) {
        return null;
    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }


    @Override
    public User save(User entity) {
        Connection conn=jdbcUtils.getConnection();
        String sql = "INSERT INTO Users(username, password) VALUES(?,?)";
        if (findOnebyName(entity.getUser()) == null)
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, entity.getUser());
                pstmt.setString(2, entity.getPassword());
                pstmt.executeUpdate();
                return findOnebyName(entity.getUser());
            } catch (SQLException ex) {
                logger.error(ex);
            }
        return null;
    }


    @Override
    public User getLogin(User entity) {
        Connection conn=jdbcUtils.getConnection();
        String sql = "SELECT * FROM Users WHERE username is ? and password is ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getUser());
            pstmt.setString(2, entity.getPassword());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"));

        } catch (SQLException ex) {
            logger.error(ex);
        }
        logger.info("Login failed");
        return null;
    }

    @Override
    public User findOnebyName(String name) {
        Connection conn=jdbcUtils.getConnection();
        String sql = "SELECT * FROM Users WHERE username is ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
        } catch (SQLException ex) {
            logger.error(ex);
        }
        return null;
    }

    @Override
    public void clear() {
        Connection conn=jdbcUtils.getConnection();
        String sql = "DELETE from Users";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
        }
    }
}

