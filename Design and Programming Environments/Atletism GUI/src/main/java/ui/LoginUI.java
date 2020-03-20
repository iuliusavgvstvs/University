package ui;

import domain.User;
import domain.exceptions.ValidationException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import service.CopilService;
import service.ProbaService;
import service.UserService;


public class LoginUI {
    private CopilService copilServ;
    private ProbaService probaServ;
    private UserService userServ;
    private Stage stage;


    @FXML
    TextField userField;
    @FXML
    PasswordField passField;
    @FXML
    Label infoLogin;
    
    public void setServices(CopilService cServ, ProbaService pServ, UserService uServ){
        this.copilServ = cServ;
        this.probaServ = pServ;
        this.userServ = uServ;
    }

    public void setStage(Stage s){
        this.stage = s;
    }

    @FXML
    private void Login() throws ValidationException, InterruptedException {
        User user = new User(1,userField.getText(),passField.getText());
        if(! userServ.getLogin(user)) {
            infoLogin.setTextFill(Paint.valueOf("#ff0000"));
            infoLogin.setText("Login failed.");
        }
        else{
            infoLogin.setTextFill(Paint.valueOf("#00ff00"));
            infoLogin.setText("Login succesful");
            stage.close();
        }

    }
}
