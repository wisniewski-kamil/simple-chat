package pl.sda.client.validators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientValidatorTest {

    @Test
    void shouldReturnTrueForNormalMessage(){
        // given
        String message = "hello";

        // when
        boolean result = ClientValidator.validateMessage(message);

        // then
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseForEmptyMessage(){
        // given
        String message = "";

        // when
        boolean result = ClientValidator.validateMessage(message);

        // then
        assertFalse(result);
    }

    @Test
    void shouldReturnFalseForOnlySpacesMessage(){
        // given
        String message = "   ";

        // when
        boolean result = ClientValidator.validateMessage(message);

        // then
        assertFalse(result);
    }

    @Test
    void shouldReturnTrueForNormalLoginData(){
        // given
        String username = "login";
        String password = "password";

        // when
        boolean result = ClientValidator.validateLogin(username, password);

        // then
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseForBothEmptyLoginData(){
        // given
        String username = "";
        String password = "";

        // when
        boolean result = ClientValidator.validateLogin(username, password);

        // then
        assertFalse(result);
    }

    @Test
    void shouldReturnFalseForBothSpaceOnlyLoginData(){
        // given
        String username = "   ";
        String password = "  ";

        // when
        boolean result = ClientValidator.validateLogin(username, password);

        // then
        assertFalse(result);
    }

    @Test
    void shouldReturnFalseForOneLoginDataEmpty(){
        // given
        String username = "";
        String password = "hello";

        // when
        boolean result = ClientValidator.validateLogin(username, password);

        // then
        assertFalse(result);
    }

    @Test
    void shouldReturnFalseForOneLoginDataSpaceOnly(){
        // given
        String username = "  ";
        String password = "hello";

        // when
        boolean result = ClientValidator.validateLogin(username, password);

        // then
        assertFalse(result);
    }

    @Test
    void shouldReturnFalseForOneLoginDataSpaceOnlyAndOneEmpty(){
        // given
        String username = "  ";
        String password = "";

        // when
        boolean result = ClientValidator.validateLogin(username, password);

        // then
        assertFalse(result);
    }

    @Test
    void shouldReturnEmptyStringForNormalRegisterData(){
        // given
        String username = "user";
        String password = "pass";
        String repeatedPassword = "pass";
        String expected = "";

        // when
        String result = ClientValidator.validateRegister(username, password, repeatedPassword);

        // then
        assertEquals(expected, result);
    }

    @Test
    void shouldReturnAppropriateInfoForDifferentPassword(){
        // given
        String username = "user";
        String password = "pass1";
        String repeatedPassword = "pass2";
        String expected = "You must repeat the same password";

        // when
        String result = ClientValidator.validateRegister(username, password, repeatedPassword);

        // then
        assertEquals(expected, result);
    }

    @Test
    void shouldReturnAppropriateInfoForEmptyPassword(){
        // given
        String username = "user";
        String password = "";
        String repeatedPassword = "pass2";
        String expected = "All fields must be filled";

        // when
        String result = ClientValidator.validateRegister(username, password, repeatedPassword);

        // then
        assertEquals(expected, result);
    }

    @Test
    void shouldReturnAppropriateInfoForEmptyUsername(){
        // given
        String username = "";
        String password = "pass";
        String repeatedPassword = "pass";
        String expected = "All fields must be filled";

        // when
        String result = ClientValidator.validateRegister(username, password, repeatedPassword);

        // then
        assertEquals(expected, result);
    }

    @Test
    void shouldReturnAppropriateInfoForUsernameContainingSpaces(){
        // given
        String username = "hello hello";
        String password = "pass";
        String repeatedPassword = "pass";
        String expected = "Username and password can't contain spaces!";

        // when
        String result = ClientValidator.validateRegister(username, password, repeatedPassword);

        // then
        assertEquals(expected, result);
    }

    @Test
    void shouldReturnAppropriateInfoForPasswordContainingSpaces(){
        // given
        String username = "user";
        String password = "pass test";
        String repeatedPassword = "pass test";
        String expected = "Username and password can't contain spaces!";

        // when
        String result = ClientValidator.validateRegister(username, password, repeatedPassword);

        // then
        assertEquals(expected, result);
    }
}