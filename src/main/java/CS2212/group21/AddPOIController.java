package CS2212.group21;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPOIController implements Initializable {

    /**
     * The text field name for the POI
     */
    @FXML
    public TextField name;

    /**
     * The text field description for the POI
     */
    @FXML
    public TextField desc;

    /**
     * The button for user log-in functionality (for admin view)
     */
    @FXML
    public Button button_login;

    /**
     * The button for user log-in functionality (for map view)
     */
    @FXML
    public Button button_login1;

    /**
     * The String for the new name
     */
    public static String newpname;

    /**
     * The String for the new description to be added
     */
    public static String newpdesc;

    /**
     * Handles the action of the user pressing the submit button
     * Adds the POI description
     * @param event
     */
    private void submit(ActionEvent event) {
        newpname = name.getText();
        newpdesc = desc.getText();
        Stage stage = (Stage)button_login.getScene().getWindow();
        stage.close();
    }

    /**
     * Handles the action of the user pressing the cancel button
     * Exits the particular pop-up
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
