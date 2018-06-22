package car.controller;

import car.dbConnection.DbHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class SignupController implements Initializable {
    @FXML
    JFXButton loginBtn;

    @FXML
    JFXButton signupBtn;

    @FXML
    TextField username;

    @FXML
    PasswordField password;

    @FXML
    ImageView progressBar;

    @FXML
    ToggleGroup gender;

    @FXML
    JFXRadioButton male;

    @FXML
    JFXRadioButton female;

    @FXML
    Text error;

    private Connection connection;
    private DbHandler dbHandler;
    private PreparedStatement preparedStatement;
    private String userTable;
    private ResultSet resultSet;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username.setStyle("-fx-text-inner-color: #a0a2ab;");
        password.setStyle("-fx-text-inner-color: #a0a2ab;");
        progressBar.setVisible(false);
        userTable = "users";
        dbHandler = new DbHandler();
        connection = dbHandler.getConnection();
    }

    @FXML
    public void handleDirectLoginAction(ActionEvent actionEvent) throws IOException {
        username.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/login.fxml"));
        Stage loginStage = new Stage();
        loginStage.setTitle("Login");
        loginStage.setScene(new Scene(root, 600, 450));
        loginStage.show();
    }

    @FXML
    public void handleSignUpAction(ActionEvent actionEvent) {
        String insert = "INSERT INTO " + userTable + "(username, password, gender)" + " VALUES(?, ?, ?)";
        dbHandler = new DbHandler();
        connection = dbHandler.getConnection();
        if (isName() && isPassword()) {
            if (!dbHandler.isUserExists(username.getText(), userTable)) {
                try {
                    preparedStatement = connection.prepareStatement(insert);
                    preparedStatement.setString(1, username.getText());
                    preparedStatement.setString(2, password.getText());
                    preparedStatement.setString(3, getGender());
                    preparedStatement.executeUpdate();
                    progressBar.setVisible(true);

                    PauseTransition pt = new PauseTransition();
                    pt.setDuration(Duration.seconds(3));
                    pt.setOnFinished(event -> {
                        // System.out.println("Sign Up successfully");
                        progressBar.setVisible(false);
                    });
                    pt.play();

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                error.setText("username is already exists");
                error.setFill(Color.RED);
                error.setVisible(true);
            }

        } else {
            error.setText("username and password are required");
            error.setFill(Color.RED);
            error.setVisible(true);
            System.out.println("username and password are required");
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

    // get gender
    public String getGender() {
        String gender = null;
        if (male.isSelected()) {
            gender = "male";
        } else if (female.isSelected()) {
            gender = "female";
        }
        return gender;
    }
}
