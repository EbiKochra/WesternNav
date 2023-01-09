package CS2212.group21;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    Account obj = new Account("src/main/java/CS2212/group21/accounts.json");

    @Test
    void getFavourites() throws IOException, ParseException {
        var test1 = obj.getFavourites("Paul");

        assertEquals("[]",test1);
    }

    @Test
    void getPassword() throws IOException, ParseException {
        var test2 = obj.getPassword("Paul");

        assertEquals("pw123", test2);

    }

    @Test
    void getUser() throws IOException, ParseException {
        var test3 = obj.getUser("Paul");

        assertEquals("Paul", test3);
    }

    @Test
    void checkAdmin() throws IOException, ParseException {
        var test4 = obj.checkAdmin("Paul");

        assertEquals(true, test4);
    }

    @Test
    void findUser() throws IOException, ParseException {
        var test5 = obj.findUser("Paul");

        assertEquals(true, test5);

    }

    @Test
    void checkValidUsername() throws IOException, ParseException {
        var test6 = obj.checkValidUsername("Paul");

        assertEquals(true, test6);

    }

//    @Test
//    void createAccount() {
//        
//
//    }
}
