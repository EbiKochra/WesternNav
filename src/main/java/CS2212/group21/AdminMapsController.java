package CS2212.group21;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminMapsController implements Initializable {

    @FXML
    public Button button_login;
    @FXML
    public Button button_login1;

    private void submit(ActionEvent event) {
        Stage stage = (Stage)button_login.getScene().getWindow();
        stage.close();
    }

    private void cancel(ActionEvent event){
        Stage stage = (Stage)button_login1.getScene().getWindow();
        stage.close();
    }
    public void addBuilding(ActionEvent actionEvent) {

    }
    public void onMapsAction(ActionEvent actionEvent) {
    }

    public void onLayersAction(ActionEvent actionEvent) {
    }

    public void onpoiAction(ActionEvent actionEvent) {
    }

    public void onFavAction(ActionEvent actionEvent) {
    }

    public void onFloorsAction(ActionEvent actionEvent) {
    }
    @Override
    public void initialize(URL url, ResourceBundle rb){
     //   button_login.setOnAction(this::submit);
       // button_login1.setOnAction(this::cancel);
    }



}
