package main;

import domain.Copil;

import domain.Proba;
import domain.validator.CopilValidator;
import domain.validator.ProbaValidator;
import domain.validator.UserValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.CopilDbRepository;
import repository.ProbaDbRepository;
import repository.UserDbRepository;
import service.CopilService;
import service.ProbaService;
import service.UserService;
import ui.LoginUI;

import java.io.IOException;
import java.sql.*;

public class MainApp extends Application {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        CopilDbRepository copilRepo = new CopilDbRepository();
        ProbaDbRepository probaRepo = new ProbaDbRepository();
        UserDbRepository userRepo = new UserDbRepository();

        CopilService copilServ = new CopilService(new CopilValidator(), copilRepo);
        ProbaService probaServ = new ProbaService(new ProbaValidator(), probaRepo);
        UserService userServ = new UserService(new UserValidator(), userRepo);

        Parent root = null;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            root = loader.load();
            LoginUI login = loader.getController();
            login.setServices(copilServ,probaServ,userServ);
            login.setStage(primaryStage);
        }catch (IOException e){
            logger.error(e);
        }

        Scene scene = new Scene(root);
        primaryStage.setTitle("Atletism for kids");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

