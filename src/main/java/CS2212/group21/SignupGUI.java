package CS2212.group21;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The graphical user interface for the sign-up screen
 * Users can sign up from this screen
 */
public class SignupGUI extends Application{

    /**
     * Creates the stage using the signup.fxml file
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginGUI.class.getResource("signup.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        stage.setTitle("WesternNav");
        stage.setScene(scene);
        stage.show();
    }
}
