package CS2212.group21;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {

    /**
     * The introductory welcome test label
     */
    @FXML
    private Label welcomeText;

    /**
     * The introductory welcome text for the initilizing the application
     */
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}