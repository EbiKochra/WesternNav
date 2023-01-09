package CS2212.group21;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static CS2212.group21.AddBuildingController.bname;
import static CS2212.group21.EditPOIController.*;

public class MainMapsController {
    private static List<ImageView> imageIcons = new ArrayList<>();

    /**
     * Anchor pane for the admin
     */
    @FXML
    private AnchorPane adminPane;

    /**
     * Map view for the display
     */
    @FXML
    private ImageView mapView;

    /**
     * The drop-down for all the maps
     */
    @FXML
    private ComboBox mapsDrop;

    /**
     * The drop-down for all the layers
     */
    @FXML
    private ComboBox layersDrop;

    /**
     * The drop-down for all the POIs
     */
    @FXML
    private ComboBox poiDrop;

    /**
     * The drop-down for all the favourites
     */
    @FXML
    private ComboBox favDrop;

    /**
     * The drop-down for all the floors
     */
    @FXML
    private ComboBox floorsDrop;

    /**
     * The label for the description text
     */
    @FXML
    private Label descText;

    /**
     * The pop-up information
     */
    private Stage popup;

    // Weather
    /**
     * The current temperature value
     */
    @FXML
    private Label curTempVal;

    /**
     * The current feels like temperature value
     */
    @FXML
    private Label feelsTempVal;

    /**
     * The low temperature value
     */
    @FXML
    private Label lowTempVal;

    /**
     * The high temperature value
     */
    @FXML
    private Label highTempVal;

    /**
     * The precipitation status
     */
    @FXML
    private Label precipStatus;

    /**
     * The precipitation description
     */
    @FXML
    private Label precipDesc;

    /**
     * The button for adding building
     */
    @FXML
    private Button addBuilding;

    /**
     * The map path
     */
    private String mapPath = "src/main/java/CS2212/group21/maps/";

    /**
     * The current floor number integer value
     */
    private int currentFloorNum;

    /**
     * The current account user
     */
    public Account user;

    /**
     * The JSON array of buildings
     */
    public JSONArray buildingFile;

    /**
     * The JSON array of current buildings
     */
    public JSONObject currentBuild;

    /**
     * The current floor
     */
    public JSONObject currFloor;

    /**
     * The current POIs array list
     */
    public JSONArray currPOIList;

    /**
     * The current POI
     */
    public JSONObject currPOI;

    /**
     * The current user file of JSON array
     */
    public JSONArray userFile;

    /**
     * The current user object of JSON array
     */
    public JSONObject userObject;

    /**
     * The search help function to assist users
     */
    public SearchHelper searchHelp;

    /**
     * The editing tool function
     */
    public EditingTool editTool;

    /**
     * The username String
     */
    public static String username;

    /**
     * The boolean admissions for admin
     */
    public static Boolean adminPermissions;

    /**
     * The event handler for the action event
     */
    EventHandler<ActionEvent> floorsDropHandler;

    /**
     * The event handler for the action event
     */
    EventHandler<ActionEvent> poiDropHandler;

    /**
     * The event handler for the action event
     */
    EventHandler<ActionEvent> favDropHandler;

    /**
     * The event handler for the action event
     */
    EventHandler<ActionEvent> layerDropHandler;

    /**
     * The double value of X-coordinate (off map)
     */
    double mapViewOffsetX = 36;

    /**
     * The double value of Y-coordinate (off map)
     */
    double mapViewOffsetY = 212;

    /**
     * The double value of X-coordinate
     */
    double mapViewSizeX = 1172;

    /**
     * The double value of Y-coordinate
     */
    double mapViewSizeY = 760;

    /**
     * The double value of Y-coordinate
     */
    double coordY;

    /**
     * The double value of X-coordinate
     */
    double coordX;

    /**
     * The place down icon
     */
    boolean canPlaceDownIcon = false;

    /**
     * The place down icon for POI
     */
    boolean canAddPOIIcon = false;

    /**
     * Temporary variable to replace icon
     */
    ImageView placedDownIcon;

    /**
     * Selecting a new map from the drop-down
     */
    @FXML
    protected void onMapsAction()
    {
        removeAllIcons();   // Clear the board

        layersDrop.setOnAction(null);
        poiDrop.setOnAction(null);
        favDrop.setOnAction(null);
        layersDrop.setValue("");
        poiDrop.setValue("");
        favDrop.setValue("");
        layersDrop.setOnAction(floorsDropHandler);
        poiDrop.setOnAction(poiDropHandler);
        favDrop.setOnAction(favDropHandler);

        String imageName = searchHelp.findImage(mapsDrop.getValue().toString(), 0);
        this.currentBuild = searchHelp.getBuildObject(mapsDrop.getValue().toString());
        JSONArray tmpArray = (JSONArray) currentBuild.get("floors");
        this.currFloor = (JSONObject) tmpArray.get(0);
        try {
            mapView.setImage(new Image(new FileInputStream(mapPath + imageName)));
            if (floorsDrop.getItems().size() < tmpArray.size()){
                // Add missing floors from dropdown when switching maps
                for (int n = floorsDrop.getItems().size() + 1; n <= tmpArray.size(); n++){
                    floorsDrop.getItems().add(n);
                }
            } else if (floorsDrop.getItems().size() > tmpArray.size()){
                // Remove excess floors from dropdown when switching maps
                for (int n = floorsDrop.getItems().size(); n > tmpArray.size(); n--){
                    floorsDrop.getItems().remove(n - 1);
                }
            }

            int val = poiDrop.getItems().size();
            for (int n = 0; n < val;n++){
                poiDrop.getItems().remove(0);
            }
            JSONObject tmpObj = (JSONObject) tmpArray.get(0);
            currPOIList = (JSONArray) tmpObj.get("pointsOfInterest");
            for (int n = 0; n < currPOIList.size(); n++){
                tmpObj = (JSONObject) currPOIList.get(n);
                if((Boolean) tmpObj.get("builtInPOI")) {
                    poiDrop.getItems().add(tmpObj.get("name") + ":" + tmpObj.get("roomNum"));
                } else {
                    poiDrop.getItems().add("(User)" + tmpObj.get("name") + ":" + tmpObj.get("roomNum"));

                }
            }

            floorsDrop.setOnAction(null);
            floorsDrop.setValue("1"); // Reset selected floor to 1 when switching maps
            floorsDrop.setOnAction(floorsDropHandler);
            onLayersAction();
        } catch (FileNotFoundException e){
            PrintOutError(e);
        }
    }

    /**
     * Selecting a new layer from the drop-down
     */
    @FXML
    protected void onLayersAction()
    {
        removeAllIcons();

        poiDrop.setOnAction(null);
        favDrop.setOnAction(null);
        poiDrop.setValue("");
        favDrop.setValue("");
        poiDrop.setOnAction(poiDropHandler);
        favDrop.setOnAction(favDropHandler);

        String layerType = "Default";
        if (layersDrop.getValue() != null){
            layerType = layersDrop.getValue().toString();
        }
        JSONArray layerList = searchHelp.findAllLayerType(currFloor, layerType);
        try {
            for(int n = 0; n < layerList.size(); n++) {
                ImageView imgView = new ImageView(new Image(new FileInputStream("src/main/java/CS2212/group21/icons/" + layerType + ".png")));
                imgView.setPreserveRatio(true);
                imgView.setFitWidth(30);
                imgView.setX(((searchHelp.getCord("X", (JSONObject) layerList.get(n))/3400.0) * mapViewSizeX) + mapViewOffsetX - 15.0);
                imgView.setY(((searchHelp.getCord("Y", (JSONObject) layerList.get(n))/2200.0) * mapViewSizeY) + mapViewOffsetY - 15.0);
                adminPane.getChildren().add(imgView);
                imageIcons.add(imgView);
            }
        } catch (Exception e){
            PrintOutError(e);
        }
        // TODO: After selecting layers dropdown option
    }

    /**
     * Selecting a new POI from the drop-down
     */
    @FXML
    protected void onpoiAction() {
        removeAllIcons();

        layersDrop.setOnAction(null);
        favDrop.setOnAction(null);
        layersDrop.setValue("");
        favDrop.setValue("");
        layersDrop.setOnAction(layerDropHandler);
        favDrop.setOnAction(favDropHandler);

        if (poiDrop.getValue() != null) {
            String string = poiDrop.getValue().toString();
            String[] subString = string.split(":");
            int indexVal = searchHelp.getPOIIndex(currPOIList, subString[0], subString[1]);
            currPOI = (JSONObject) currPOIList.get(indexVal);
            String layerType = currPOI.get("layerType").toString();
            try {
                ImageView imgView = new ImageView(new Image(new FileInputStream("src/main/java/CS2212/group21/icons/" + layerType + ".png")));
                imgView.setPreserveRatio(true);
                imgView.setFitWidth(30);
                imgView.setX(((searchHelp.getCord("X", (JSONObject) currPOI) / 3400.0) * mapViewSizeX) + mapViewOffsetX - 15.0);
                imgView.setY(((searchHelp.getCord("Y", (JSONObject) currPOI) / 2200.0) * mapViewSizeY) + mapViewOffsetY - 15.0);
                adminPane.getChildren().add(imgView);
                imageIcons.add(imgView);
                Object desc = currPOI.get("description");
                if (desc != null) { descText.setText(desc.toString()); }
            } catch (Exception e) {
                PrintOutError(e);
            }
        }
    }

    /**
     * Selecting a new favourite from the drop-down
     */
    @FXML
    protected void onFavAction()
    {
        removeAllIcons();

        layersDrop.setOnAction(null);
        poiDrop.setOnAction(null);
        layersDrop.setValue("");
        poiDrop.setValue("");
        layersDrop.setOnAction(layerDropHandler);
        poiDrop.setOnAction(poiDropHandler);

        // Displaying the poi icon
        if (favDrop.getValue() != null) {
            String string = favDrop.getValue().toString();
            String[] subString = string.split(":");
            int indexVal = searchHelp.getPOIIndex(currPOIList, subString[0], subString[1]);
            currPOI = (JSONObject) currPOIList.get(indexVal);
            String layerType = currPOI.get("layerType").toString();
            try {
                ImageView imgView = new ImageView(new Image(new FileInputStream("src/main/java/CS2212/group21/icons/" + layerType + ".png")));
                imgView.setPreserveRatio(true);
                imgView.setFitWidth(30);
                imgView.setX(((searchHelp.getCord("X", (JSONObject) currPOI) / 3400.0) * mapViewSizeX) + mapViewOffsetX - 15.0);
                imgView.setY(((searchHelp.getCord("Y", (JSONObject) currPOI) / 2200.0) * mapViewSizeY) + mapViewOffsetY - 15.0);
                adminPane.getChildren().add(imgView);
                imageIcons.add(imgView);
            } catch (Exception e) {
                PrintOutError(e);
            }
        }
    }

    /**
     * Switching maps and floors function
     */
    @FXML
    protected void onFloorsAction() // This runs when switching floors AND when switching maps after switching floors for some reason
    {
        this.currentFloorNum = Integer.parseInt(floorsDrop.getValue().toString()) - 1;

        JSONArray tmpArray = (JSONArray) currentBuild.get("floors");
        currFloor = (JSONObject) tmpArray.get(currentFloorNum);

        String imageName = searchHelp.findImage(currentBuild.get("Building").toString(), Integer.parseInt(floorsDrop.getValue().toString()) - 1);
        try {
            mapView.setImage(new Image(new FileInputStream(mapPath + imageName)));
            clearComboBox(poiDrop);
            JSONObject tmpObj = (JSONObject) tmpArray.get(currentFloorNum);
            currPOIList = (JSONArray) tmpObj.get("pointsOfInterest");
            for (int n = 0; n < currPOIList.size(); n++){
                tmpObj = (JSONObject) currPOIList.get(n);
                poiDrop.getItems().add(tmpObj.get("name") + ":" + tmpObj.get("roomNum"));
            }
            onLayersAction();
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        }
    }

    /**
     * Toggle through favourites function
     */
    @FXML
    protected void onToggleFavourites()
    {
        if (currPOI != null) {
            if (currPOI.get("favourite").equals(true)){
                editTool.favouriteToggle(currentBuild.get("Building").toString(), currentFloorNum, currPOI, false);
                favDrop.getItems().remove(currPOI);
            }else {
                editTool.favouriteToggle(currentBuild.get("Building").toString(), currentFloorNum, currPOI, true);
                if (currPOI.get("builtInPOI").equals((true))){
                    favDrop.getItems().add(currPOI.get("name") + ":" + currPOI.get("roomNum"));
                } else {
                    favDrop.getItems().add("(User)" + currPOI.get("name") + ":" + currPOI.get("roomNum"));
                }
            }
        } else {
            System.out.println("No POI selected");
        }
    }

    /**
     * The function for logging out of the application
     */
    @FXML
    protected void onLogout() throws IOException
    {
        try {
            // close the stage
            Stage stage = (Stage) adminPane.getScene().getWindow();
            stage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(LoginGUI.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 600);

            Stage newLogin = new Stage();
            newLogin.setTitle("WesternNav");
            newLogin.setScene(scene);
            newLogin.show();
        }
        catch (Exception e) {
            PrintOutError(e);
        }
    }

    /**
     * The open help function for assisting the user
     */
    @FXML
    protected void onOpenHelp()
    {
        try {
            popup = new Stage();
//            Parent root = FXMLLoader.load(getClass().getResource("HelpMainPopup.fxml"));
            Parent root = new FXMLLoader(MainMaps.class.getResource("HelpMainPopup.fxml")).load();
            Scene scene = new Scene(root, 625, 500);
            popup.setTitle("Help");
            popup.setScene(scene);
            popup.show();
        }
        catch (Exception e){
            PrintOutError(e);
        }
    }

    /**
     * Combining the JSON array information
     */
    protected void combineJSON(){
        JSONArray userPOIs = (JSONArray) userObject.get("userPOIs");
        JSONArray favList = (JSONArray) userObject.get("favourites");
        JSONObject userTmpObj;
        JSONObject builtTmpObj;
        JSONArray tmpArray;
        JSONArray poiList;
        for (int k = 0; k < userPOIs.size(); k++) {
            userTmpObj = (JSONObject) userPOIs.get(k);
            for (int n = 0; n < buildingFile.size(); n++) {
                builtTmpObj = (JSONObject) buildingFile.get(n);
                if (userTmpObj.get("building").equals(builtTmpObj.get("Building").toString())){
                    tmpArray = (JSONArray) builtTmpObj.get("floors");
                    for (int f = 0; f < tmpArray.size(); f++){
                        builtTmpObj = (JSONObject) tmpArray.get(f);
                        if (Integer.parseInt(userTmpObj.get("floorNum").toString()) == f){
                            poiList = (JSONArray) builtTmpObj.get("pointsOfInterest");
//                            System.out.println(k);
//                            System.out.println(n);
//                            System.out.println(f);

                            editTool.addPOI(poiList);
                        }
                    }
                }
            }
        }
        for (int k = 0; k < favList.size(); k++) {
            userTmpObj = (JSONObject) favList.get(k);
            for (int n = 0; n < buildingFile.size(); n++) {
                builtTmpObj = (JSONObject) buildingFile.get(0);
                if (userTmpObj.get("building").equals(builtTmpObj.get("Building").toString())){
                    tmpArray = (JSONArray) builtTmpObj.get("floors");
                    for (int f = 0; f < tmpArray.size(); f++){
                        builtTmpObj = (JSONObject) tmpArray.get(f);
                        if (Integer.parseInt(userTmpObj.get("floorNum").toString()) == f){
                            poiList = (JSONArray) builtTmpObj.get("pointsOfInterest");
                            for (int p = 0; p < poiList.size(); p++){
                                JSONObject curPoi = (JSONObject) poiList.get(p);
                                if (curPoi.get("name").equals(userTmpObj.get("name")) && curPoi.get("roomNum").equals(userTmpObj.get("roomNum"))){
                                    editTool.favouriteToggle(currentBuild.get("Building").toString(), currentFloorNum, curPoi, true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Adding a new building to the information
     */
    @FXML
    protected void addBuilding(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddBuilding.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 550, 400);
            Stage stage = new Stage();
            stage.setTitle("WesternNav");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        editTool.addBuilding(bname);
        mapsDrop.getItems().add(bname);


        // TODO: Get the name the user wants,
        String buildName = "TestTest";
        // TODO: Complex. Need to take map name
    }

    /**
     * Editing the information of a particular building
     */
    @FXML
    protected void editBuilding(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditBuilding.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 550, 400);
            Stage stage = new Stage();
            stage.setTitle("WesternNav");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        editTool.editBuilding(currentBuild, bname);
        // TODO: Complex. Rename building
    }

    /**
     * Removing a particular building from the data
     */
    @FXML
    protected void removeBuilding(){
        // Remove the building from the builtInPOI.json file, for now removes it from the backUpBuiltInPOI.json file
        editTool.removeBuilding((String) currentBuild.get("Building"));
        // Remove the building from the Maps combo box

        mapsDrop.getItems().remove(currentBuild.get("Building"));
        currentBuild = (JSONObject) buildingFile.get(0);
        // Change the map
        JSONArray tmpArray = (JSONArray) currentBuild.get("floors");
        currFloor = (JSONObject) tmpArray.get(0);
        String imageName = currFloor.get("imageFileName").toString();
        try {
            mapView.setImage(new Image(new FileInputStream(mapPath + imageName)));
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        }
            // TODO: Simple. Just remove current building open. Floor does not matter. Switch to another building view
    }

    /**
     * Adding a POI to the JSON data
     */
    @FXML
    protected void addPOI(){
        alertUser();
        canAddPOIIcon = true;
        JSONObject tmpObj;
        if(adminPermissions){
            editTool.addPOI(currPOIList);
            tmpObj = (JSONObject) currPOIList.get(0);
            poiDrop.getItems().add(tmpObj.get("name") + ":" + tmpObj.get("roomNum"));
        } else {
            editTool.addPOI(currPOIList);
            editTool.addPOI((JSONArray) userObject.get("userPOIs"));
            editTool.addToUserPOI(currentBuild.get("Building").toString(), currentFloorNum);
            JSONArray tmpArray = (JSONArray) userObject.get("userPOIs");
            tmpObj = (JSONObject) tmpArray.get(0);
            poiDrop.getItems().add("(User)" + tmpObj.get("name") + ":" + tmpObj.get("roomNum"));
        }
        // TODO: Complex. Must specify layer type and x and y location (maybe by clicking)
    }

    /**
     * Editing a POI from the JSON information
     */
    @FXML
    protected void editPOI() throws IOException {
        // Enables the user to place an icon for new POI location
        alertUser();
        canPlaceDownIcon = true;
    }

    /**
     * Alerting a user
     */
    private void alertUser(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Alert.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 300);
            Stage stage = new Stage();
            stage.setTitle("WesternNav");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loading the EditPOI.fxml file
     */
    private void editPOIPopout(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditPOI.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 550, 400);
            Stage stage = new Stage();
            stage.setTitle("WesternNav");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        if (currPOI != null) {
            String nameInput = currPOI.get("name").toString();
            String descriptionInput = currPOI.get("description").toString();
            String roomNumInput = currPOI.get("roomNum").toString();
            String layerTypeInput = currPOI.get("layerType").toString();
            int xInput = Integer.parseInt(currPOI.get("xCord").toString());
            int yInput = Integer.parseInt(currPOI.get("yCord").toString());
            int newX = (int)coordX;
            int newY = (int)coordY;
//            System.out.println(newX);
//            System.out.println(newY);
//            System.out.println(pname);
//            System.out.println(pdesc);
//            System.out.println(proom);
//            System.out.println(player);
            editTool.editPOI(currPOI, pname, pdesc, newX, newY, proom, player);
            clearComboBox(poiDrop);
            JSONObject tmpObj;
            for (int n = 0; n < currPOIList.size(); n++) {
                tmpObj = (JSONObject) currPOIList.get(n);
                poiDrop.getItems().add(tmpObj.get("name") + ":" + tmpObj.get("roomNum"));
            }
        } else {
            System.out.println("No Current POI Selected");
        }
        adminPane.getChildren().remove(placedDownIcon);
        canPlaceDownIcon = false;
    }

    /**
     * Removing a POI from the JSON data
     */
    @FXML
    protected void removePOI(){
        if (currPOI != null){
            // TODO: Check to see if you are admin before removing a built in poi icon
            removeAllIcons();
            if (adminPermissions) {
                editTool.removePOI(currPOIList, currPOI);
                poiDrop.getItems().remove(currPOI.get("name") + ":" + currPOI.get("roomNum"));
                currPOI = null;
            } else {
                editTool.removePOI(currPOIList, currPOI);
                editTool.removePOI((JSONArray) userObject.get("userPOIs"), currPOI);
                editTool.removeUserPOI(currPOI);
                poiDrop.getItems().remove("(User)" + currPOI.get("name") + ":" + currPOI.get("roomNum"));
                currPOI = null;
            }
        } else {
            System.out.println("No POI selected");
        }
    }

    /**
     * Adding a floor to the POI data
     */
    @FXML
    protected void addFloor(){
        // Add the new floor to the builtInPOI.json but for now all data goes to backUpBuiltInPOI.json
        editTool.addFloor(currentBuild, "DefaultFile.png");
        // Add the new floor to the combo box
        floorsDrop.getItems().add(floorsDrop.getItems().size() + 1);
        // TODO: Complex. Specify floor number.
    }

    /**
     * Remove a floor to the POI data
     */
    @FXML
    protected void removeFloor(){
        // Remove the floor from builtInPOI.json, for now remove it from backUpBuiltInPOI.json
        JSONArray tmpArray = (JSONArray) currentBuild.get("floors");
        editTool.removeFloor(tmpArray,currentFloorNum);
        // Remove the floor from the combobox
        floorsDrop.getItems().remove(floorsDrop.getItems().size() - 1);
        // Change the map
        tmpArray = (JSONArray) currentBuild.get("floors");
        currFloor = (JSONObject) tmpArray.get(0);
        try {
            mapView.setImage(new Image(new FileInputStream(mapPath + currFloor.get("imageFileName").toString())));
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        }
        // TODO: Simple. Remove current floor open.
    }

    /**
     * Removes all icons from the map
     */
    private void removeAllIcons(){
        for (ImageView icon: imageIcons) {
            adminPane.getChildren().remove(icon);
//            imageIcons.remove(icon);
        }
        // Reset description
        descText.setText("");
    }

    /**
     * Clears the data from the drop-down
     */
    private void clearComboBox(ComboBox cBox){
        int val = cBox.getItems().size();
        for (int n = 0; n < val;n++){
            cBox.getItems().remove(0);
        }
    }

    /**
     * Initializing the data
     */
    @FXML
    public void initialize() throws FileNotFoundException {
        floorsDropHandler = floorsDrop.getOnAction();
        poiDropHandler = poiDrop.getOnAction();
        favDropHandler = favDrop.getOnAction();
        layerDropHandler = layersDrop.getOnAction();
        floorsDrop.setValue("1");
        // Track location to know where to graphically change location of POI when editing
        mapView.setOnMouseClicked(e -> {
            try {
                GetMouseDown(e);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        //adminPermissions =  false;
        this.currentFloorNum = 0;
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src/main/java/CS2212/group21/backUpBuiltInPOI.json")) {
            try (FileReader accountReader = new FileReader("src/main/java/CS2212/group21/accounts.json")) {
                JSONObject currentBuilding = (JSONObject) jsonParser.parse(reader);
                searchHelp = new SearchHelper((JSONArray) currentBuilding.get("buildings"), adminPermissions);
                this.buildingFile = (JSONArray) currentBuilding.get("buildings");
                this.currentBuild = (JSONObject) buildingFile.get(0);
                JSONArray tmpArray = (JSONArray) currentBuild.get("floors");
                this.currFloor = (JSONObject) tmpArray.get(currentFloorNum);
                SearchHelper search = new SearchHelper(buildingFile, adminPermissions);
//                System.out.println(buildingFile);
//                System.out.println(username);
//                System.out.println(adminPermissions);

                this.editTool = new EditingTool(buildingFile, username, adminPermissions);
                this.userFile = (JSONArray) jsonParser.parse(accountReader);
                for (int n = 0; n < userFile.size(); n++){
                    JSONObject tmpObj = (JSONObject) userFile.get(n);
                    if (tmpObj.get("username").toString().equals(username)){
                        this.userObject = tmpObj;
                    }
                }
                combineJSON();
                this.currPOIList = (JSONArray) currFloor.get("pointsOfInterest");
                for (int n = 0; n < buildingFile.size(); n++) {
                    mapsDrop.getItems().add(search.getBuildIndex(n));
                }
                for (int n = 0; n < currPOIList.size(); n++) {
                    JSONObject tmp = (JSONObject) currPOIList.get(n);
                    if ((Boolean) tmp.get("builtInPOI")) {
                        poiDrop.getItems().add(tmp.get("name") + ":" + tmp.get("roomNum"));
                    } else {
                        poiDrop.getItems().add("(User)" + tmp.get("name")+ ":" + tmp.get("roomNum"));
                    }
                }
                tmpArray = (JSONArray) userObject.get("favourites");
                JSONObject tmpObj;
                for (int n = 0; n < tmpArray.size(); n++){
                    tmpObj = (JSONObject) tmpArray.get(n);
                    if(tmpObj.get("builtInPOI").equals(true)){
                        favDrop.getItems().add(tmpObj.get("name") + ":" + tmpObj.get("roomNum"));
                    } else {
                        favDrop.getItems().add("(User)" + tmpObj.get("name") + ":" + tmpObj.get("roomNum"));
                    }
                }
                layersDrop.getItems().addAll("Classroom", "CollaborationSpace", "Elevator", "Lab", "Navigation", "Washroom");
                // D
                floorsDrop.getItems().addAll("1", "2", "3", "4", "5");

                // Set first value to first map
                mapsDrop.setValue(search.getBuildIndex(0));

            } catch (ParseException e) {
                System.out.println("Parse Exception");
            }
        } catch (IOException e){

        }

        Weather.SetAllWeatherData();
        curTempVal.setText(Weather.GetTempCurrent() + " °C");
        feelsTempVal.setText(Weather.GetTempFeelsLike() + " °C");
        lowTempVal.setText(Weather.GetTempMin() + " °C");
        highTempVal.setText(Weather.GetTempMax() + " °C");
        precipStatus.setText(Weather.GetPrecipitationStatus() + " °C");
        precipDesc.setText(Weather.GetPrecipitationDescription() + " °C");

        String pp = "Middlesex_College-1.png";
        mapView.setImage(new Image(new FileInputStream(mapPath + pp)));

    }

    /**
     * Sets a place holder at a particular location
     */
    @FXML
    private void GetMouseDown(MouseEvent e) throws FileNotFoundException {
        // TODO: To be used when moving poi through editing
        coordX = e.getX() / mapViewSizeX * 3400;
        coordY = e.getY() / mapViewSizeY * 2200;

        if (!canPlaceDownIcon && !canAddPOIIcon) {
            return;
        }

        try {
            ImageView imgView = new ImageView(new Image(new FileInputStream("src/main/java/CS2212/group21/icons/Placeholder.png")));
            imgView.setPreserveRatio(true);
            imgView.setX(e.getX() + mapViewOffsetX - 15);
            imgView.setY(e.getY() + mapViewOffsetY - 15);
            imgView.setFitWidth(30);
            placedDownIcon = imgView;

            // TODO: Delete after submitting edit/add poi

            adminPane.getChildren().add(imgView);
            imageIcons.add(imgView);
            if(canPlaceDownIcon){
                editPOIPopout();
            }
            if(canAddPOIIcon){
                addPOIPopout();
            }

        }
        catch (Exception error){
            PrintOutError(error);
        }
    }

    /**
     * Adds a POI popout
     */
    private void addPOIPopout() {
        // newX = new X Coord
        // newY = new Y Coord
        // newpname = new POI name from user
        // newpdesc = new POI desc from user

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ADDPOI.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 550, 400);
            Stage stage = new Stage();
            stage.setTitle("WesternNav");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int newX = (int)coordX;
        int newY = (int)coordY;
//        System.out.println(newX);
//        System.out.println(newY);
//        System.out.println(newpname);
//        System.out.println(newpdesc);
        adminPane.getChildren().remove(placedDownIcon);
        canAddPOIIcon = false;
    }

    /**
     * Occurs after an occurence of an error
     */
    private void PrintOutError(Exception e){
        System.out.println("Error occurred: " + e);
    }
}
