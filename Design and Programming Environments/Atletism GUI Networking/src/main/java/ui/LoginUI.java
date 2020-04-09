package ui;

import domain.User;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.CopilService;
import service.ProbaService;
import service.UserService;

import java.io.IOException;


public class LoginUI {
    private static final Logger logger = LogManager.getLogger();
    @FXML
    TextField userField;
    @FXML
    PasswordField passField;
    @FXML
    Label infoLogin;
    private CopilService copilServ;
    private ProbaService probaServ;
    private UserService userServ;
    private Stage stage;

    public void setServices(CopilService cServ, ProbaService pServ, UserService uServ) {
        this.copilServ = cServ;
        this.probaServ = pServ;
        this.userServ = uServ;
    }

    public void setStage(Stage s) {
        this.stage = s;
    }

    @FXML
    private void Login() {
        User user = new User(1, userField.getText(), passField.getText());
        if (!userServ.getLogin(user)) {
            infoLogin.setTextFill(Paint.valueOf("#ff0000"));
            infoLogin.setText("Login failed.");
        } else {
            infoLogin.setTextFill(Paint.valueOf("#00ff00"));
            infoLogin.setText("Login succesful");
            this.stage.close();
            showMainApp();
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
            MainAppUI mainappui = loader.getController();
            mainappui.setServices(copilServ, probaServ, mainAppStage);
            mainAppStage.show();
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
