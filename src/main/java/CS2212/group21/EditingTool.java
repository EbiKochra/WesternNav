package CS2212.group21;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

/**
 *  Represents an editing tool used to that allows the developers to easily edit the metadata of the built-in points of interest.
 *  This editing tool will allow the user to work with the buildings and point of interest metadata.
 *  For the buildings the user of the account will be able to add, edit and remove buildings.
 */
public class EditingTool {

  /**
     * Represents a group of constants to showcase different types of Points of Interest in the buildings.
     */
    enum layerType {
        Class,
        Lab,
        CollaborationSpace,
        Washroom,
        Elevator,
        Navigation,
        Default,
    }
  	 /**
     * Represents username of the user.
     */
    protected String username;
     /**
     * Represents permissions of the user.
     */
    protected boolean adminPermissions;
     /**
     * Represents JSON database of the buildings.
     */
    protected JSONArray buildingList;

    protected JSONArray accounts;
    final String adminName = "Admin";
    public EditingTool(JSONArray buildings, String username, Boolean admin){
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src/main/java/CS2212/group21/accounts.json")){
            this.accounts = (JSONArray) jsonParser.parse(reader);
        } catch(IOException e) {
            System.out.println("IOException");
        } catch (ParseException e){
            System.out.println("ParseException");
        }
        this.adminPermissions = admin;
        this.buildingList = buildings;
        this.username = username;

    }

  	/**
     * Adds a building to the database of buildings and points of interests.
     * @param buildName for the building name.
     */
    // Makes a new building with new building's name
    public void addBuilding(String buildName){
        if (adminPermissions) {
            JSONObject newBuild = new JSONObject();
            JSONArray newFloor = createFloor();
            //Building name
            newBuild.put("Building", buildName);
            // An empty floor with no data
            newBuild.put("floors", newFloor);
            // Adding the new building to buildinglist
            buildingList.add(newBuild);
            saveData();
        } else {
            System.out.println("Invalid Permissions to add building");
        }
    }
    /**
     * Edits a building name in the database of buildings and points of interests.
     * @param currentBuilding for the building name.
     * @param newBuildName for the new building name.
     */
    // Can only ever change building name
    public void editBuilding(JSONObject currentBuilding, String newBuildName){
        if(adminPermissions){
            int num = findIndex(currentBuilding.get("Building").toString());
            buildingList.remove(num);
            currentBuilding.replace("Building", newBuildName);
            buildingList.add(currentBuilding);
            saveData();
        } else {
            System.out.println("Invalid Permission to edit building");
        }
    }
	 /**
      /**
      * Removes a building from the database of buildings and points of interests.
      * @param buildName for the building name.
      */
    // Will need method for finding a building with this name.
    public void removeBuilding(String buildName){
        if(adminPermissions){
            buildingList.remove(buildingList.get(findIndex(buildName)));
            saveData();
        } else {
            System.out.println("Invalid Permission to Remove Building");
        }
    }
  	 /**
     * Adds floor layer to the building in the database.
     * @param imageName string for the building name.
     * @param currentBuilding JSONObject for building.
     */
    // Need get method for finding Building
    public void addFloor(JSONObject currentBuilding, String imageName){
        if(adminPermissions){
            // Create a new default floor
            JSONObject newFloor = new JSONObject();
            JSONArray poiList = createPOI();

            newFloor.put("imageFileName", imageName);
            newFloor.put("pointsOfInterest", poiList);

            JSONArray tmpArray = (JSONArray) currentBuilding.get("floors");
            tmpArray.add(tmpArray.size(),newFloor);
            saveData();
        } else {
            System.out.println("Invalid Permissions to add Floor");
        }
    }
	 /**
     * Removes a point of interest from the database of buildings and points of interests.
     * @param currentBuild JSONArray for the point of interest's name.
     * @param floorNum of floor to be removed
     */
    public void removeFloor(JSONArray currentBuild,int floorNum){
        if (adminPermissions){
            currentBuild.remove(floorNum);
            saveData();
        } else {
            System.out.println("Invalid Permissions to remove floor");
        }
    }
	/**
     * Adds point of interest to the building in the database.
     * @param poiList
     */
    public void addPOI(JSONArray poiList){
        // Create a new Point of interest
        JSONObject defaultPOI = new JSONObject();
        defaultPOI.put("layerType", "Default");
        defaultPOI.put("visibility", true);
        defaultPOI.put("favourite", false);
        defaultPOI.put("description", "DefaultDescription");
        defaultPOI.put("name", "Default POI Name");
        defaultPOI.put("xCord", 0);
        defaultPOI.put("yCord", 0);
        defaultPOI.put("roomNum", "DefaultRoomNum");
        if (adminPermissions){
            defaultPOI.put("builtInPOI", true);
        } else {
            defaultPOI.put("builtInPOI", false);

        }
        poiList.add(0, defaultPOI);
        saveData();

    }
    /**
     * Edits a point of interest name in the database of buildings and points of interests.
     * @param poi for the point of interest
     * @param newName for the name of the POI
     * @param newDescription for the description of the POI
     * @param newX for the x-coordinate
     * @param newY for the y-coordinate
     * @param newRoomNum for the room number
     * @param newLayerType the layer type of the POI
     */
    public void editPOI(JSONObject poi, String newName, String newDescription, int newX, int newY, String newRoomNum, String newLayerType){
        if(poi.get("builtInPOI").equals(true)){
            // BuiltInPOI and Admin account can edit
            if (adminPermissions){
                poi.replace("name", newName);
                poi.replace("description", newDescription);
                poi.replace("xCord", newX);
                poi.replace("yCord", newY);
                poi.replace("roomNum", newRoomNum);
                poi.replace("layerType", newLayerType);
                saveData();
            } else {
                System.out.println("You do not have the correct permissions to alter this point of interest");
            }
        } else {
            JSONObject userObj;
            for (int n = 0; n < accounts.size(); n++) {
                userObj = (JSONObject) accounts.get(n);
                String string = userObj.get("username").toString();
                if (string.equals(username)) {
                    JSONArray poiList = (JSONArray) userObj.get("userPOIs");
                    JSONObject tmpObj;
                    for (int k = 0; k < poiList.size(); k++) {
                        tmpObj = (JSONObject) poiList.get(k);
                        JSONObject newObj = new JSONObject();
                        if (tmpObj.get("name").equals(poi.get("name")) && tmpObj.get("roomNum").equals(poi.get("roomNum"))) {
                            System.out.println("Test");
                            poiList.remove(k);
                            poi.replace("building", poi.get("building"));
                            poi.replace("floorNum", poi.get("floorNum"));
                            poi.replace("name", newName);
                            poi.replace("description", newDescription);
                            poi.replace("xCord", newX);
                            poi.replace("yCord", newY);
                            poi.replace("roomNum", newRoomNum);
                            poi.replace("layerType", newLayerType);
                            poi.replace("builtInPOI", false);
                            poi.replace("favourite", poi.get("favourite"));
                            poiList.add(poi);
                            saveData();
                        }
                    }
                }
            }
        }
    }
    /**
     * Removes a point of interest from the database of buildings and points of interests.
     * @param poiList for the point of interest.
     * @param currentPOI for the current POI in the list.
     */
    // Will need the method for finding a building, find a floor and then finding a POI
    public void removePOI(JSONArray poiList, JSONObject currentPOI){
        JSONObject tmpObj;
        for (int n = 0; n < poiList.size(); n++){
            tmpObj = (JSONObject) poiList.get(n);
            if (tmpObj.get("name").equals(currentPOI.get("name")) && tmpObj.get("roomNum").equals(currentPOI.get("roomNum"))){
                poiList.remove(n);
                saveData();
            }
        }
    }
    public void addToUserPOI(String buildName, int floorNum){
        // Finding the JSONObject that has the same username
        JSONObject userObj;
        for (int n = 0; n < accounts.size(); n++){
            userObj = (JSONObject) accounts.get(n);
            String string = userObj.get("username").toString();
            if(string.equals(username)){
                JSONArray poiList = (JSONArray) userObj.get("userPOIs");
                JSONObject defaultPOI = new JSONObject();
                defaultPOI.put("building", buildName);
                defaultPOI.put("floorNum", floorNum);
                defaultPOI.put("name", "Default POI Name");
                defaultPOI.put("description", "DefaultDescrip");
                defaultPOI.put("roomNum", "DefaultRoomNum");
                defaultPOI.put("layerType", "Default");
                defaultPOI.put("visibility", true);
                defaultPOI.put("favourite", false);
                defaultPOI.put("builtInPOI", false);
                defaultPOI.put("xCord", 0);
                defaultPOI.put("yCord", 0);
                poiList.add(0, defaultPOI);
                saveData();
            }
        }
    }
    public void favouriteToggle(String currentBuild, int floorNum, JSONObject currPOI, Boolean favTF){
        JSONObject userObj;
        for (int n = 0; n < accounts.size(); n++){
            userObj = (JSONObject) accounts.get(n);
            String string = userObj.get("username").toString();
            if(string.equals(username)){
                JSONArray poiList = (JSONArray) userObj.get("favourites");
                poiList.remove(currPOI);
                currPOI.put("building", currentBuild);
                currPOI.put("floorNum", floorNum);
                currPOI.replace("favourite", favTF);
                poiList.add(currPOI);
                saveData();
            }
        }
    }
    public void removeUserPOI(JSONObject poi){
        JSONObject userObj;
        for (int n = 0; n < accounts.size(); n++) {
            userObj = (JSONObject) accounts.get(n);
            String string = userObj.get("username").toString();
            if (string.equals(username)) {
                JSONArray poiList = (JSONArray) userObj.get("userPOIs");
                JSONObject tmpObj;
                for (int k = 0; k < poiList.size(); k++){
                    tmpObj = (JSONObject) poiList.get(k);
                    if (tmpObj.get("name").toString().equals(poi.get("name").toString())){
                        poiList.remove(k);
                    }
                }
                saveData();
            }
        }
    }
    public JSONArray createFloor(){
        JSONArray floors = new JSONArray();
        JSONArray poiList = createPOI();
        JSONObject defaultFloorObj = new JSONObject();
        defaultFloorObj.put("imageFileName", "DefaultFileName");
        defaultFloorObj.put("pointsOfInterest", poiList);
        floors.add(0, defaultFloorObj);
        return floors;
    }
  	/**
     * Creates a point of interest to add to the database of buildings and points of interests.
     * @return poiList JSONArray that includes the new POI in database.
     */
    public JSONArray createPOI(){
        JSONArray poiList = new JSONArray();
        JSONObject defaultPOI = new JSONObject();
        defaultPOI.put("layerType", "Default");
        defaultPOI.put("visibility", true);
        defaultPOI.put("description", "Default");
        defaultPOI.put("name", "Unnamed Point of Interest");
        defaultPOI.put("xCord", 0);
        defaultPOI.put("yCord", 0);
        defaultPOI.put("roomNum", "");
        if (adminPermissions){
            defaultPOI.put("builtInPOI", true);
        } else {
            defaultPOI.put("builtInPOI", false);
        }
        poiList.add(0, defaultPOI);
        return poiList;
    }
    /**
     * Finds index of building name from database of buildings and points of interests.
     * @param buildName for the building name.
     * @return int the index of the building.
     */
    public int findIndex(String buildName){
        for (int i = 0; i < buildingList.size(); i++) {
            JSONObject buildings = (JSONObject) buildingList.get(i);
            String buildingname = (String) buildings.get("Building");
            if (buildName.equals(buildingname)) {
                return i;
            }
        }
        System.out.println("The building does not exist.");
        return -1;

    }
	/**
     *
     */
    public void saveData(){
        try {
            if (adminPermissions) {
                // Saving the data to builtInPOI.json
                BufferedWriter buffWriter = new BufferedWriter(new FileWriter("src/main/java/CS2212/group21/builtInPOI.json"));
                JSONObject tmpObj = null;
                JSONArray tmpArray = new JSONArray();
                buffWriter.write("{\"buildings\":[");
                for (int n = 0; n < buildingList.size(); n++) {
                    tmpObj = (JSONObject) buildingList.get(n);
                    tmpArray = (JSONArray) tmpObj.get("floors");
                    buffWriter.write("{\"Building\":\"" + (String) tmpObj.get("Building") + "\",\"floors\":[");
                    for (int f = 0; f < tmpArray.size(); f++) {
                        JSONObject tmpObject = (JSONObject) tmpArray.get(f);
                        JSONArray poiList = (JSONArray) tmpObject.get("pointsOfInterest");
                        for(int k = 0; k < poiList.size(); k++) {
                            JSONObject checkObj = (JSONObject) poiList.get(k);
                            if((Boolean) checkObj.get("builtInPOI") == true){
                                buffWriter.write(poiList.get(k).toString());
                                if (f + 1 != tmpArray.size() || k + 1 != poiList.size()) {
                                    buffWriter.write(",");
                                }
                            }
                        }
                        if (f + 1 != tmpArray.size()) {
                            buffWriter.write("\n");
                        } else {
                            buffWriter.flush();
                            if (n + 1 != buildingList.size()) {
                                buffWriter.write("]},\n");
                            } else {
                                buffWriter.write("]}]}\n");
                            }
                        }
                    }
                    buffWriter.write("\n");
                    buffWriter.flush();
                }
            } else {
                BufferedWriter userBuffWriter = new BufferedWriter(new FileWriter("src/main/java/CS2212/group21/accounts.json"));
                userBuffWriter.write("[");
                for (int n = 0; n < accounts.size(); n++){
                    userBuffWriter.write(accounts.get(n).toString());
                    if (n + 1 != accounts.size()) {
                        userBuffWriter.write(",\n");
                    } else {
                        userBuffWriter.write("]");
                    }
                    userBuffWriter.flush();
                }
            }
        } catch (IOException e){
            System.out.println("IOException");
            e.printStackTrace();
        }
    }
}