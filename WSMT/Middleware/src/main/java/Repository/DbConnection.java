package Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    public static Connection getConnection() {
        Connection conn = null;
        try{
            Class.forName("org.sqlite.JDBC");
            System.out.println("Driver loaded");
            String url = "jdbc:sqlite:C:\\Users\\Iuliu\\OneDrive\\Desktop\\Middleware\\middleware_db";
            conn = DriverManager.getConnection(url);

        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }
        return  conn;
    }
}
