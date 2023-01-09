package CS2212.group21;
import java.io.FileReader;
import java.io.FileWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;

/**
 * Represents an Account Database stored within a JSON file.
 * The account database can store many different accounts.
 */
public class Account {
    // Create attributes for Account class
    /**
     * The filename of the JSON file that stores the accounts
     */
    private String fileName = "";
    /**
     * JSONObject that holds the details of each account such as username, password, and favourites
     */
    static JSONObject accountDetails = new JSONObject();
    // Constructor Class

    /**
     * Creates an account database using the given filename
     * @param file The name of the file
     */
    public Account(String file){
        // Uses the given file name to create the 'database'
        try{
            this.fileName = file;
            JSONParser jsonParser = new JSONParser();
            FileReader reader = new FileReader(this.fileName);
            Object obj = jsonParser.parse(reader);

            long favourites[] = new long[10];


        }catch(Exception e){
            System.out.println(e);
        }

    }

    /**
     * Gets the favourites JSONArray stored in the account database for the given username
     * @param user The username of the user for which we are getting their favourites
     * @return Returns the JSONArray favourites stored in the file for this database
     * @throws IOException
     * @throws ParseException
     */
    public JSONArray getFavourites(String user) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(this.fileName);
        Object obj = jsonParser.parse(reader);
        JSONObject accountDetails;
        JSONArray accountList = (JSONArray) obj;

        for (int i = 0; i < accountList.size(); i++) {
            accountDetails = (JSONObject) accountList.get(i);
            if (user.equals(accountDetails.get("username"))) {
                return (JSONArray) accountDetails.get("favourites");
            }
        } return null;
    }

    /**
     * Gets the password of the account for the given username
     * @param user The username of the user for which we are getting their favourites
     * @return Returns the passwords of the account
     * @throws IOException
     * @throws ParseException
     */
    public String getPassword(String user) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(this.fileName);
        Object obj = jsonParser.parse(reader);
        JSONObject accountDetails;
        JSONArray accountList = (JSONArray) obj;
        for (int i = 0; i < accountList.size(); i++) {
            accountDetails = (JSONObject) accountList.get(i);
            if (user.equals(accountDetails.get("username"))) {
                return  (String)accountDetails.get("password");
            }
        } return null;
    }
    /**
     * Gets the username of the account for the given username
     * @param user The username of the user for which we are getting their username
     * @return Returns the username of the account
     * @throws IOException
     * @throws ParseException
     */
    public String getUser(String user) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(this.fileName);
        Object obj = jsonParser.parse(reader);
        JSONObject accountDetails;
        JSONArray accountList = (JSONArray) obj;
        for (int i = 0; i < accountList.size(); i++) {
            accountDetails = (JSONObject) accountList.get(i);
            if (user.equals(accountDetails.get("username"))) {
                return  (String)accountDetails.get("username");
            }
        } return null;
    }

    /**
     * Checks if the account is an admin account able to access the admin view
     * @param user The username of the account to check
     * @return Boolean value of if it is an admin account
     * @throws IOException
     * @throws ParseException
     */
    public boolean checkAdmin(String user) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(this.fileName);
        Object obj = jsonParser.parse(reader);
        JSONObject accountDetails;
        JSONArray accountList = (JSONArray) obj;

        for (int i = 0; i < accountList.size(); i++) {
            accountDetails = (JSONObject) accountList.get(i);
            if (user.equals(accountDetails.get("username"))) {
                if(accountDetails.get("admin").equals("true")){
                    return true;
                }else if(accountDetails.get("admin").equals("false")){
                    return false;
                }
            }
        } return false;
    }
    /**
     * Checks if the given user is found in the database
     * @param user The username of the user we are searching for
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public boolean findUser(String user) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(this.fileName);
        Object obj = jsonParser.parse(reader);
        JSONObject accountDetails;
        JSONArray accountList = (JSONArray) obj;
        for(int i = 0; i <accountList.size(); i++) {
            accountDetails = (JSONObject)accountList.get(i);
            if(user.equals(accountDetails.get("username"))){
                return true;
            }
        }
        // The specified username could not be found in the file
        return false;
    }

    /**
     * Checks if the username is valid to create a new account for
     * @param newUser The new username to check if already taken
     * @return Boolean true if not taken, false if taken
     * @throws IOException
     * @throws ParseException
     */
    public boolean checkValidUsername(String newUser) throws IOException, ParseException {
        // There is not an existing user with given username
        if(findUser(newUser) == false){
            return true;
        } else{
            return false;
        }
    }

    /**
     * Creates an account using the given username and password and writes it to the database in the JSON file
     * @param username The username of the account to be created
     * @param password The password of the account to be created
     * @throws IOException
     * @throws ParseException
     */
    public void createAccount(String username, String password) throws IOException, ParseException {
        if(checkValidUsername(username)){
            JSONParser jsonParser = new JSONParser();
            FileReader reader = new FileReader(this.fileName);
            Object obj = jsonParser.parse(reader);
            JSONArray accountList = (JSONArray) obj;
            String user = (String)accountDetails.get("username");
            String pw = (String)accountDetails.get("password");
            accountDetails.put("username", user);
            accountDetails.put("password", pw);

            accountDetails.put("username", username);
            accountDetails.put("password", password);
            accountDetails.put("admin", "false");
            JSONArray favs = new JSONArray();
            accountDetails.put("favourites", favs);

            JSONArray userPOIs = new JSONArray();
            accountDetails.put("userPOIs", userPOIs);

            accountList.add(accountDetails);

            try(FileWriter file = new FileWriter(this.fileName)){
                file.write(accountList.toJSONString());
                file.flush();

            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    public static void PrintError(Exception e){
        System.out.println("Error: " + e);
    }

    public static void main(String[] args) throws IOException, ParseException {
        // Testing
        Account num1 = new Account("src/main/java/CS2212/group21/accounts.json");
        num1.findUser("Peter");
        num1.findUser("John");
        num1.findUser("Paul");
        num1.checkValidUsername("Joey");
        num1.checkValidUsername("Peter");
        num1.createAccount("Test", "Dummy");
        num1.findUser("Moe");
        num1.checkValidUsername("Moe");
        System.out.println(num1.getPassword("Josh"));
        System.out.println(num1.getFavourites("Paul"));
        System.out.println(num1.checkAdmin("Paul"));
        System.out.println(num1.checkValidUsername("Paul"));
    }
}