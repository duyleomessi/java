package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Controller {

    @FXML private Text actiontarget ;

    @FXML private TextField username;
    @FXML private Text usernameError;

    public void handleSubmitAction(ActionEvent actionEvent) {

        if (username.getText() != null && !username.getText().isEmpty()) {
            actiontarget.setText(username.getText());
            usernameError.setText("");
        } else {
           usernameError.setText("Username is required");
        }

    }
}
