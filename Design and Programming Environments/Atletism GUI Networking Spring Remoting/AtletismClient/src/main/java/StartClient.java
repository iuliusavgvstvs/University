import gui.LoginUI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.IService;

import java.io.IOException;


public class StartClient extends Application {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
        IService server=(IService)factory.getBean("chatService");
        System.out.println("Obtained a reference to remote chat server");
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(StartClient.class.getResource("/login.fxml"));
            root = loader.load();
            LoginUI login = loader.getController();
            login.setServices(server);
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
