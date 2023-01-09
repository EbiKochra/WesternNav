package CS2212.group21;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The graphical user interface for the add maps view
 * Admin pop up view
 */
public class AdminMapsGUI extends Application{

    /**
     * Creates the stage for the admin view using the AdminMaps.fxml file
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AddFloorGUI.class.getResource("AdminMaps.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 975);
        stage.setTitle("WesternNav");
        stage.setScene(scene);
        stage.show();
    }
}
