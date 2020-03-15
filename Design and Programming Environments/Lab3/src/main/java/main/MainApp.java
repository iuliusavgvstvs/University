package main;

import domain.Copil;

import domain.Proba;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.CopilDbRepository;
import repository.ProbaDbRepository;

import java.sql.*;

public class MainApp {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        CopilDbRepository repo = new CopilDbRepository();
        ProbaDbRepository repo2 = new ProbaDbRepository();
        logger.info("Repo created");

        System.out.println(repo.findOne(4).toString());

        Copil c1= new Copil(1, "Vasilescu","Ciprian",12);
        repo.save(c1);
        System.out.println(repo.findOne(5).toString());
        System.out.println(repo.findOnebyName("Pop","Cristi",8).toString());
        Proba p1, p2, p3, p4, p5;
        p1 = repo2.save(new Proba(1,1, 50));
        p2 = repo2.save(new Proba(2,1,100));
        p3 = repo2.save(new Proba(3, 2, 1000));
        p4 = repo2.save(new Proba(4, 2, 1500));
        p5 = repo2.save(new Proba(5, 3, 50));
        System.out.println(repo2.getByCopilID(1).size());
        System.out.println(repo2.getByCopilID(2).size());
        System.out.println(repo2.getByCopilID(3).size());
    }


}

