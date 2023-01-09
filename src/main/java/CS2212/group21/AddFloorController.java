package CS2212.group21;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class AddFloorController implements Initializable {

    /**
     * The text field for the floor
     */
    @FXML
    private TextField floor;

    /**
     * The button the user presses to log in to the application on admin view
     */
    @FXML
    public Button button_login;

    /**
     * The button the user presses to log in to the application on map view
     */
    @FXML
    public Button button_login1;

    /**
     * Handles the action of the user pressing the submit button
     * Adds the floor with given name
     * @param event
     */
    private void submit(ActionEvent event) {
        System.out.println(floor.getText());
        Stage stage = (Stage)button_login.getScene().getWindow();
        stage.close();
    }

    /**
     * Handles the action of the user pressing the cancel button
     * Removes the user from the pop-up
     * @param event
     */
    private void cancel(ActionEvent event){
        Stage stage = (Stage)button_login1.getScene().getWindow();
        stage.close();
    }

    /**
     * Initializes and sets the next scene in the fxml
     * With the selection of either cancel or submit
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        button_login.setOnAction(this::submit);
        button_login1.setOnAction(this::cancel);
    }
}
