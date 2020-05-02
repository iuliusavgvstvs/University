package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.TableEntity;
import model.User;
import model.exceptions.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.ChatException;
import services.IObserver;
import services.IService;

import java.io.IOException;


public class LoginUI implements IObserver {
    private static final Logger logger = LogManager.getLogger();
    @FXML
    TextField userField;
    @FXML
    PasswordField passField;
    @FXML
    Label infoLogin;
    private IService Service;
    private Stage stage;
    User user;

    public void setServices(IService serv) {
        this.Service = serv;
    }

    public void setStage(Stage s) {
        this.stage = s;
    }
    public MainAppUI mainappui = new MainAppUI();
    @FXML
    private void Login() throws ChatException, ValidationException {
        this.user = new User(1, userField.getText(), passField.getText());
        try {
            Service.login(user, this);
            infoLogin.setTextFill(Paint.valueOf("#00ff00"));
            infoLogin.setText("Login succesful");
            this.stage.close();
            showMainApp();

        }
        catch(ChatException | ValidationException e){

            infoLogin.setTextFill(Paint.valueOf("#ff0000"));
            infoLogin.setText("Login failed.");
        }
    }

    private void showMainApp() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("mainapp.fxml"));
            AnchorPane root = loader.load();

            Stage mainAppStage = new Stage();
            mainAppStage.setTitle("Atletism Manager v1.0");
            mainAppStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            mainAppStage.setScene(scene);
            this.mainappui = loader.getController();
            this.mainappui.setServices(Service, mainAppStage, user);
            mainAppStage.show();
        } catch (IOException | ChatException e) {
            logger.error(e);
        }
    }

    @Override
    public void enitityAdded(TableEntity entity) throws ValidationException, ChatException {
        this.mainappui.enitityAdded(entity);
    }
}
