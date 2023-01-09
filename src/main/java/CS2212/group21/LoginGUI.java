package CS2212.group21;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The graphical user interface for the login screen
 * Users can login or access the signup page from this screen
 */
public class LoginGUI extends Application{
    /**
     * Creates the stage using the login.fxml file
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginGUI.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        stage.setTitle("WesternNav");
        stage.setScene(scene);
        stage.show();
    }
}
