package CS2212.group21;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Add building controller that gets the name of the new building
 * Only accessible in admin mode
 */
public class AddBuildingController implements Initializable {
    /**
     * The name of the new building which the user enters in the textfield
     */
    @FXML
    private TextField building;
    /**
     * The button the user presses to submit the name of the building
     */
    @FXML
    public Button button_login;
    /**
     * The button the user presses to cancel
     */
    @FXML
    public Button button_login1;
    public static String bname;
    /**
     * Handles the action of the user pressing the submit button
     * Adds the building with given name
     * @param event
     */
    private void submit(ActionEvent event) {
        bname = building.getText();
        Stage stage = (Stage)button_login.getScene().getWindow();
        stage.close();
    }

    /**
     * Handles the action of the user pressing the cancel button
     * Returns to admin view with no changes made
     * @param event
     */
    private void cancel(ActionEvent event){
        Stage stage = (Stage)button_login1.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb){
        button_login.setOnAction(this::submit);
        button_login1.setOnAction(this::cancel);
    }
}
