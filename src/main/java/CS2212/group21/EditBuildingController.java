package CS2212.group21;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class EditBuildingController implements Initializable {

    /**
     * The text field name for the building
     */
    @FXML
    public TextField name;

    /**
     * The button for logging into to map view
     */
    @FXML
    public Button button_login;

    /**
     * The button for logging into to admin view
     */
    @FXML
    public Button button_login1;

    /**
     * The String for the new altered name
     */
    public static String editbname;

    /**
     * Handles the action of the user pressing the submit button
     * Edits the building description
     * @param event
     */
    private void submit(ActionEvent event) {
        editbname = name.getText();
        Stage stage = (Stage)button_login.getScene().getWindow();
        stage.close();
    }

    /**
     * Handles the action of the user pressing the cancel button
     * Cancels the new the building description pop-up
     * @param event
     */
    private void cancel(ActionEvent event){
        Stage stage = (Stage)button_login1.getScene().getWindow();
        stage.close();
    }

    /**
     * Initializes the next scene for two selections
     * Cancelling a particular pop-up or submitting a new change
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        button_login.setOnAction(this::submit);
        button_login1.setOnAction(this::cancel);
    }



}
