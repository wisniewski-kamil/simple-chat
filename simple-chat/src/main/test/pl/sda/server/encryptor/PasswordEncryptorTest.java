package pl.sda.server.encryptor;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class PasswordEncryptorTest {

    @Test
    void shouldEncryptSameStringsIntoSameEncryptedStrings() throws NoSuchAlgorithmException {
        // given
        String string1 = "Hello";
        String string2 = "Hello";

        // when
        String encryptedString1 = PasswordEncryptor.encrypt(string1);
        String encryptedString2 = PasswordEncryptor.encrypt(string2);

        // then
        assertEquals(encryptedString1, encryptedString2);
    }

    @Test
    void shouldEncryptDifferentStringsIntoDifferentEncryptedStrings() throws NoSuchAlgorithmException {
        // given
        String string1 = "Hello";
        String string2 = "Hello!";

        // when
        String encryptedString1 = PasswordEncryptor.encrypt(string1);
        String encryptedString2 = PasswordEncryptor.encrypt(string2);

        // then
        assertNotEquals(encryptedString1, encryptedString2);
    }
}