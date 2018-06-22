package car.controller;

import car.dbConnection.DbHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    JFXTextField username;

    @FXML
    JFXPasswordField password;

    @FXML
    JFXCheckBox rememberMe;

    @FXML
    JFXButton loginBtn;

    @FXML
    JFXButton signUpBtn;

    @FXML
    JFXButton forgotPassBtn;

    @FXML
    ImageView progressBar;

    @FXML
    Text error;

    private DbHandler dbHandler;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private String userTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userTable = "users";
        progressBar.setVisible(false);
        username.setStyle("-fx-text-inner-color: #a0a2ab;");
        password.setStyle("-fx-text-inner-color: #a0a2ab;");
    }

    @FXML
    public void handleLoginAction(javafx.event.ActionEvent actionEvent) {

        dbHandler = new DbHandler();
        connection = dbHandler.getConnection();
        if (isName() && isPassword()) {
            if (dbHandler.isAccountExists(username.getText(), password.getText(), userTable)) {
                error.setVisible(false);
                progressBar.setVisible(true);
                PauseTransition pt = new PauseTransition();
                pt.setDuration(Duration.seconds(3));
                pt.setOnFinished(event -> {
                    System.out.println("Login Successfylly");
                    progressBar.setVisible(false);
                });
                pt.play();

                System.out.println("Login sucessfully");
            } else {
                error.setText("Username or password is incorrect");
                error.setFill(Color.RED);
            }
        } else {
            error.setText("Username or password are required");
            error.setFill(Color.RED);
        }
    }

    // check if username is filled
    public boolean isName() {
        if (username.getText() != null && !username.getText().isEmpty()) {
            return true;
        }
        return false;
    }

    // check if password is filled
    public boolean isPassword() {
        if (password.getText() != null && !password.getText().isEmpty()) {
            return true;
        }
        return false;
    }


    @FXML
    public void handleDirectSignupAction(javafx.event.ActionEvent actionEvent) throws IOException {
        loginBtn.getScene().getWindow().hide();
        Stage signupStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/signup.fxml"));
        signupStage.setTitle("Sign Up");
        signupStage.setScene(new Scene(root, 600, 450));
        signupStage.show();
    }
}
