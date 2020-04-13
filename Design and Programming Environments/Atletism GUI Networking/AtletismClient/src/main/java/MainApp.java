import model.validator.CopilValidator;
import model.validator.ProbaValidator;
import model.validator.UserValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import .CopilDbRepository;
import repository.ProbaDbRepository;
import repository.UserDbRepository;
import service.CopilService;
import service.ProbaService;
import service.UserService;
import gui.LoginUI;

import java.io.IOException;

public class MainApp extends Application {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Application.launch(args);
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            root = loader.load();
            LoginUI login = loader.getController();
            login.setServices(copilServ, probaServ, userServ);
            login.setStage(primaryStage);
        } catch (IOException e) {
            logger.error(e);
        }

        Scene scene = new Scene(root);
        primaryStage.setTitle("Atletism for kids");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}

