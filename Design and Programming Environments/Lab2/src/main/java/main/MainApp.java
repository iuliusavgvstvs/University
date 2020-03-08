package main;

import domain.Copil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.CopilDbRepository;

import java.sql.*;

public class MainApp {
    public static int suma(int a, int b) {
        return a + b;
    }

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws SQLException {
        Copil<String> c1 = new Copil<>("1", "A", "B", 12);
        Copil<String> c2 = new Copil<>("2", "AA", "BB", 8);
        Copil<String> c3 = new Copil<>("1", "AAA", "BBB", 9);
         //System.out.println(c1.toString());
        //System.out.println(c2.toString());
        //System.out.println(c1.equals(c3));


        /*
        //Conectarea la o baza de date SQLite
        Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/Iuliu/OneDrive/Desktop/Facultate/Sem IV/MPP/Laboratoare/Lab2/src/main/resources/atletism.db");

        //select
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("select * from Copil")) {
                while (rs.next()) {
                    System.out.println(rs.getInt("id") + " " + rs.getString("nume") +
                            " " + rs.getString("prenume") + " " + rs.getInt("varsta"));

                }
            } catch (SQLException ex) {
                System.err.println(ex.getSQLState());
                System.err.println(ex.getErrorCode());
                System.err.println(ex.getMessage());
            }


        }
         */
        CopilDbRepository<String, Copil<String>> repo = new CopilDbRepository<>();
        logger.info("Repo created");
        System.out.println(repo.findOne("1").toString());
        logger.info("Executed repo.findOne(\"1\")");
    }


}

