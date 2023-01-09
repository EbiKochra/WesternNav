package CS2212.group21;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class Weather {
    // region Attributes

    /**
     * Default error value
     */
    static private final String error = "NA";

    /**
     * Latitude for London, Ontario
     */
    static private final String lat = "42.9849";

    /**
     * Longitude for London, Ontario
     */
    static private final String lon = "-81.2453";

    /**
     * Current temperature (in C°)
     */
    static private String tempCurrent = error;

    /**
     * Lowest temperature recorded today (in C°)
     */
    static private String tempMin = error;

    /**
     * Highest temperature recorded today (in C°)
     */
    static private String tempMax = error;

    /**
     * Current "feels like" temperature (in C°)
     */
    static private String tempFeelsLike = error;

    /**
     * Current humidity (as a %)
     */
    static private String humidity = error;

    /**
     * Precipitation status
     */
    static private String precipitationStatus = error;

    /**
     * Precipitation description
     */
    static private String precipitationDescription = error;

    /**
     * Current weather data formatted as a JSONObject
     */
    static private JSONObject jsonData = null;

    // endregion

    // region Getters

    /**
     * @return Current temperature (in C°)
     */
    public static String GetTempCurrent() {
        return tempCurrent;
    }

    /**
     * @return Lowest recorded temperature today (in C°)
     */
    public static String GetTempMin() {
        return tempMin;
    }

    /**
     * @return Highest recorded temperature today (in C°)
     */
    public static String GetTempMax() {
        return tempMax;
    }

    /**
     * @return Current "feels like" temperature today (in C°)
     */
    public static String GetTempFeelsLike() {
        return tempFeelsLike;
    }

    /**
     * @return Humidity today (as a %)
     */
    public static String GetHumidity() {
        return humidity;
    }

    /**
     * @return Precipitation status
     */
    public static String GetPrecipitationStatus() {
        return precipitationStatus;
    }

    /**
     * @return Precipitation description
     */
    public static String GetPrecipitationDescription() {
        return precipitationDescription;
    }

    // endregion

    // region Setters

    /**
     * Sets current weather data as a JSON string in London Ontario through HTTPRequest.
     * Fetches from OpenWeather API.
     */
    private static void SetCurrentWeatherData(){
        try {
            String apiKey = "6ae73b11767febcd9a4c5b850246e0f1";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + apiKey + ""))
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            jsonData = new JSONObject(response.body());
        }
        catch (Exception e) {
            PrintError(e);
            jsonData = null;
        }
    }

    /**
     *  Sets precipitation status and description
     */
    private static void SetPrecipitation() {
        try {
            JSONArray weatherArray = jsonData.getJSONArray("weather");  // Weather data is stored in array of size 1 for some reason
            JSONObject inner = weatherArray.getJSONObject(0);
            var precipitationStatus = inner.getString("main");
            var precipitationDescription = inner.getString("description");
            Weather.precipitationStatus = precipitationStatus;
            Weather.precipitationDescription = precipitationDescription;
        }
        catch (Exception e) {
            PrintError(e);
            Weather.precipitationStatus = error;
            Weather.precipitationDescription = error;
        }
    }

    /**
     * Set current temperature (in C°)
     */
    private static void SetTemperature(){
        try {
            var main = jsonData.getJSONObject("main");
            float temp = main.getFloat("temp") - 273.15f;
            tempCurrent = String.valueOf(Math.round(temp));
        }
        catch (Exception e) {
            PrintError(e);
            tempCurrent = error;
        }
    }

    /**
     * Sets minimum recorded temperature for the day (in C°)
     */
    private static void SetTemperatureMin(){
        try {
            var main = jsonData.getJSONObject("main");
            float temp = main.getFloat("temp_min") - 273.15f;
            tempMin = String.valueOf(Math.round(temp));
        }
        catch (Exception e) {
            PrintError(e);
            tempMin = error;
        }
    }

    /**
     * Sets max recorded temperature for the day (in C°)
     */
    private static void SetTemperatureMax(){
        try {
            var main = jsonData.getJSONObject("main");
            float temp = main.getFloat("temp_max") - 273.15f;
            tempMax = String.valueOf(Math.round(temp));
        }
        catch (Exception e) {
            PrintError(e);
            tempMax = error;
        }
    }

    /**
     * Sets "feels like" temperature (in C°)
     */
    private static void SetFeelsLikeTemperature(){
        try {
            var innerJSONData = jsonData.getJSONObject("main");
            float temp = innerJSONData.getFloat("feels_like") - 273.15f;
            tempFeelsLike = String.valueOf(Math.round(temp));
        }
        catch (Exception e) {
            PrintError(e);
            tempFeelsLike = error;
        }
    }

    /**
     * Sets humidity (as a %)
     */
    private static void SetHumidity() {
        try {
            var innerJSONData = jsonData.getJSONObject("main");
            float humidity = innerJSONData.getFloat("humidity");
            Weather.humidity = String.valueOf(humidity);
        }
        catch (Exception e) {
            PrintError(e);
            humidity = error;
        }
    }

    // endregion

    /**
     * Prints a well formatted error message.
     * @param e Exception that is thrown
     */
    private static void PrintError(Exception e) {
        System.out.println("Did not succeed");
        System.out.println("Error: " + e);
    }

    /**
     * Sets all weather data beginning with an HTTP request
     */
    public static void SetAllWeatherData() {
        SetCurrentWeatherData();
        SetTemperature();
        SetTemperatureMax();
        SetTemperatureMin();
        SetFeelsLikeTemperature();
        SetHumidity();
        SetPrecipitation();
    }

    // TODO: Delete later. For testing only.
    private static void TestPrintAllWeather() {
        SetAllWeatherData();
        System.out.println("Current temperature: " + GetTempCurrent());
        System.out.println("Max temperature: " + GetTempMax());
        System.out.println("Min temperature: " + GetTempMin());
        System.out.println("Feels like temperature: " + GetTempFeelsLike());
        System.out.println("Humidity: " + GetHumidity());
        System.out.println("Precipitation status: " + GetPrecipitationStatus());
        System.out.println("Precipitation description: " + GetPrecipitationDescription());
    }

    // TODO: Delete later. For testing only.
    public static void main(String[] args) {
        TestPrintAllWeather();
    }
}