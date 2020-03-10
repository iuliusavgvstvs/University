package Properties;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBProp {
    public Connection getNewConnection() {
        Properties prop = new Properties();
        String fileName = "Properties/atletismDB.properties";
        InputStream inputStream;

        try {
            inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
            prop.load(inputStream);
        }
        catch (IOException e) {
            System.out.println("Nu s-a putut incarca fisierul cu proprietati " + e.getMessage());
        }
        String url = prop.getProperty("jbdc.url");
        Connection con = null;
        try {
            con = DriverManager.getConnection(url);
        }
        catch (SQLException e) {
            System.out.println("Nu s-a putut stabili conexiunea " + e.getMessage());
        }
        return con;
    }
}
