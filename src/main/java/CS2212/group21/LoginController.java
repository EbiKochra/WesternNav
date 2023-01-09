package CS2212.group21;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Login Controller that controls buttons in the GUI and gets inputs from user
 */
public class LoginController implements Initializable {
    /**
     * The password entered by the user in the textfield
     */
    @FXML
    private TextField tf_password;
    /**
     * The username entered by the user in the textfield
     */
    @FXML
    private TextField tf_username;
    /**
     * The button used by the user to login
     */
    @FXML
    public Button button_login;
    /**
     * The button used by the user to go to the sign up screen
     */
    @FXML
    Button button_sign_up;
    /**
     * The account database used to check if the login is valid
     */
    Account num1 = new Account("src/main/java/CS2212/group21/accounts.json");
    /**
     * The boolean value for if the username is valid
     */
    boolean validUser = false;
    /**
     * The boolean value for if the password is valid
     */
    boolean validPw = false;
    /**
     * The boolean value for if the account is an admin account
     */
    boolean admin = false;

    /**
     * Handles the action of the login button being pressed.
     * If login is valid with admin permissions, taken to admin view
     * If login is valid with no admin permissions, taken to MainMaps view
     * If login is invalid, taken to invalid login screen where they can try again
     * @param event
     */
    private void submit(ActionEvent event){
        try {
            // Checks if given text is equal to a username found in JSON file
            if(tf_username.getText().equals(num1.getUser(tf_username.getText()))){
                validUser = true;
            }
            // Checks if given text is equal to the password associated with given user
            if (tf_password.getText().equals(num1.getPassword(tf_username.getText()))){
                validPw = true;
            }
            // If account has admin permissions, sets admin to true;
            admin = num1.checkAdmin(tf_username.getText());
            // If the username and password is valid, and account is an admin account
            if(validUser && validPw && admin){
                // Switches to Admin View
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AdminMaps.fxml"));
                    String username = tf_username.getText();
                    MainMapsController.username = username;
                    MainMapsController.adminPermissions = admin;
                    Scene scene = new Scene(fxmlLoader.load(), 1800, 1600);
                    Stage stage = new Stage();
                    stage.setTitle("WesternNav");
                    stage.setScene(scene);
                    stage.setMaximized(true);
                    stage.show();
                    ((Node)(event.getSource())).getScene().getWindow().hide();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else if(validUser && validPw){
                // Switches to Admin View
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMaps.fxml"));
                    MainMapsController.username = tf_username.getText();
                    MainMapsController.adminPermissions = admin;
                    Scene scene = new Scene(fxmlLoader.load(), 1800, 1600);
                    Stage stage = new Stage();
                    stage.setTitle("WesternNav");
                    stage.setScene(scene);
                    stage.setMaximized(true);
                    stage.show();
                    ((Node)(event.getSource())).getScene().getWindow().hide();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else{
                // Switches to Invalid Login Screen
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginInvalid.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
                    Stage stage = new Stage();
                    stage.setTitle("WesternNav");
                    stage.setScene(scene);
                    stage.show();
                    ((Node)(event.getSource())).getScene().getWindow().hide();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }catch(IOException | ParseException e){
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of the sign up button being pressed
     * Takes the user to the sign up screen
     * @param event
     */
    private void signUp(ActionEvent event) {
        // Switches to Sign Up screen
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("signup.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
            Stage stage = new Stage();
            stage.setTitle("WesternNav");
            stage.setScene(scene);
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb){
        button_login.setOnAction(this::submit);
        button_sign_up.setOnAction(this::signUp);
    }
}
