package CS2212.group21;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class EditPOIController implements Initializable {

    /**
     * Represents the name for the POI.
     */
    @FXML
    public TextField name;

    /**
     * Represents description of the POI.
     */
    @FXML
    public TextField desc;

    /**
     * Represents the layer type of the POI.
     */
    @FXML
    public TextField layerType;

    /**
     * Represents roomnumber of the POI.
     */
    @FXML
    public TextField roomNum;

    /**
     * Represents the x-coordinate.
     */
    @FXML
    public TextField x;

    /**
     * Represents the y-coordinate.
     */

    /**
     * Represents the button login for map view.
     */
    @FXML
    public Button button_login;

    /**
     * Represents the button login for admin view.
     */
    @FXML
    public Button button_login1;

    /**
     * Represents the name of the POI.
     */
    public static String pname;

    /**
     * Represents the description of the POI.
     */
    public static String pdesc;

    /**
     * Represents the room.
     */
    public static String proom;

    /**
     * Represents the player.
     */
    public static String player;

    /**
     * Handles the action of the user pressing the submit button
     * Edits the POI description
     * @param event
     */
    private void submit(ActionEvent event) {
        pname = name.getText();
        pdesc = desc.getText();
        proom = roomNum.getText();
        player = layerType.getText();
        Stage stage = (Stage)button_login.getScene().getWindow();
        stage.close();
    }

    /**
     * Handles the action of the user pressing the cancel button
     * Cancels the POI description
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
