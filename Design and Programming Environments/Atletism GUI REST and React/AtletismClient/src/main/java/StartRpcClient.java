

import gui.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rpcprotocol.AtletismServicesRpcProxy;
import services.IService;

import java.io.IOException;
import java.util.Properties;


public class StartRpcClient extends Application {
    private static int defaultChatPort=55555;
    private static String defaultServer="localhost";
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Properties clientProps=new Properties();
        try {
            clientProps.load(StartRpcClient.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatclient.properties "+e);
            return;
        }
        String serverIP=clientProps.getProperty("chat.server.host",defaultServer);
        int serverPort=defaultChatPort;
        try{
            serverPort=Integer.parseInt(clientProps.getProperty("chat.server.port"));
        }catch(NumberFormatException ex){
            System.err.println("Wrong port number "+ex.getMessage());
            System.out.println("Using default port: "+defaultChatPort);
        }
        System.out.println("Using server IP "+serverIP);
        System.out.println("Using server port "+serverPort);
        IService server=new AtletismServicesRpcProxy(serverIP, serverPort);


        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(StartRpcClient.class.getResource("login.fxml"));
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
