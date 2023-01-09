package CS2212.group21;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.stage.Popup;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.IOException;


public class MainMaps extends Application {

    /**
     * Creates the stage using the AdminMaps.fxml file
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader root = new FXMLLoader(MainMaps.class.getResource("MainMaps.fxml"));
        FXMLLoader root = new FXMLLoader(MainMaps.class.getResource("AdminMaps.fxml"));
//        Scene scene = new Scene(root.load());


        Scene scene = new Scene(root.load());

        String css = this.getClass().getResource("MainMaps.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setTitle("User Map View");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}