package CS2212.group21;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class SearchHelper {

    /**
     * The boolean for admin permissions
     */
    Boolean adminPermissions;

    /**
     * The JSON array of the buildings list
     */
    JSONArray wholeBuilding;

    /**
     * The tmp Obj contained JSON array
     */
    JSONObject tmpObj;

    /**
     * Constructor for setting admin permissions to admin
     * Setting whole buildings to all buildings
     * @param allBuildings
     * @param admin
     */
    public SearchHelper(JSONArray allBuildings, Boolean admin){
        this.adminPermissions = admin;
        this.wholeBuilding = allBuildings;
    }

    /**
     * Getting the index for the particular building
     * Using the index value for the building index
     * @param indexValue
     */
    public String getBuildIndex(int indexValue){
        tmpObj = (JSONObject) wholeBuilding.get(indexValue);
        return tmpObj.get("Building").toString();

    }

    /**
     * Getting the POI index from the data
     * Using the index value for the POI index
     * @param poiList
     * @param name
     * @param roomNum
     */
    public int getPOIIndex(JSONArray poiList, String name, String roomNum){
        for (int n = 0; n < poiList.size(); n++){
            tmpObj = (JSONObject) poiList.get(n);
            if (tmpObj.get("name").equals(name) && tmpObj.get("roomNum").equals(roomNum)) {
                    return n;
            } else {
                String tmpString = "(User)" + tmpObj.get("name").toString();
                if(tmpString.equals(name) && tmpObj.get("roomNum").equals(roomNum)){
                    return n;
                }
            }
        }
        return -1;
    }

    /**
     * Getting the POI cord from the data
     * Using the XY value and the POI in the list
     * @param XY
     * @param poi
     */
    public int getCord(String XY, JSONObject poi){
        if(XY.equals("X")){
            String string = poi.get("xCord").toString();
            int i = Integer.parseInt(string);
            return i;
        } else {
            String string = poi.get("yCord").toString();
            int i = Integer.parseInt(string);
            return i;
        }
    }

    /**
     * Getting the building information
     * Building data from the JSON data
     * @param buildName
     */
    public JSONObject getBuildObject(String buildName){
        String tmp = "";
        for (int n = 0; n < wholeBuilding.size(); n++){
            tmpObj = (JSONObject) wholeBuilding.get(n);
            if (tmpObj.get("Building").equals(buildName)){ return tmpObj; }
        }
        return null;
    }

    /**
     * Getting the image from the JSON information
     * From the JSON array list, we draw a particular image file to display
     * @param buildName
     * @param floorNum
     */
    public String findImage(String buildName, int floorNum){
        tmpObj = (JSONObject) getBuildObject(buildName);
        JSONArray tmpArray = (JSONArray) tmpObj.get("floors");
        tmpObj = (JSONObject) tmpArray.get(floorNum);
        return tmpObj.get("imageFileName").toString();
    }

    /**
     * Getting all the layer types from the JSON Array data
     * For displaying the information on the GUI
     * @param currFloor
     * @param layerType
     */
    public JSONArray findAllLayerType(JSONObject currFloor, String layerType){
        JSONArray tmpArray = (JSONArray) currFloor.get("pointsOfInterest");
        JSONArray returnList = new JSONArray();
        JSONObject tmpObj;
        String tmpString = "";
        for (int n = 0; n < tmpArray.size(); n++){
            tmpObj = (JSONObject) tmpArray.get(n);
            tmpString = tmpObj.get("layerType").toString();
            if (tmpString.equals(layerType) == true){
                returnList.add(tmpObj);
            }
        }
        return returnList;
    }
}
