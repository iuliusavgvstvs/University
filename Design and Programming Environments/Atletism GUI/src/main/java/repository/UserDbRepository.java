package repository;

import properties.DBProp;
import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDbRepository implements IUserRepository<User> {
    private Connection conn;
    private static final Logger logger = LogManager.getLogger();

    public UserDbRepository() {
        this.conn = new DBProp().getNewConnection();
    }

    @Override
    public User findOne(int id) {
        return null;
    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    public Connection getConn() {
        return conn;
    }

    @Override
    public User save(User entity) {
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
    public boolean getLogin(User entity) {
        String sql = "SELECT * FROM Users WHERE username is ? and password is ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getUser());
            pstmt.setString(2, entity.getPassword());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                return true;

        } catch (SQLException ex) {
            logger.error(ex);
        }
        logger.info("Login failed");
        return false;
    }

    @Override
    public User findOnebyName(String name) {
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
    public void clear(){
        String sql = "DELETE from Users";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
        }
    }
}

