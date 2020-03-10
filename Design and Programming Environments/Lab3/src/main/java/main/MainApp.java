package main;

import domain.Copil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.CopilDbRepository;

import java.sql.*;

public class MainApp {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws SQLException {
        CopilDbRepository<Copil> repo = new CopilDbRepository<>();
        logger.info("Repo created");

        System.out.println(repo.findOne(4).toString());

        Copil c1= new Copil(1, "Vasilescu","Ciprian",12);
        repo.save(c1);
        System.out.println(repo.findOne(5).toString());
        System.out.println(repo.findOnebyName("Pop","Cristi",8).toString());
    }


}

