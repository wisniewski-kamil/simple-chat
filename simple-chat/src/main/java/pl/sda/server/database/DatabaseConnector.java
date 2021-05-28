package pl.sda.server.database;

import pl.sda.client.ChatClient;
import pl.sda.server.database.entities.User;
import pl.sda.server.database.repositories.JpaUserRepository;
import pl.sda.server.database.services.UserService;
import pl.sda.server.encryptor.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class DatabaseConnector {
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("chat");
    private static UserService userService = new UserService(factory);

    public static UserService getUserService(){
        return userService;
    }
}
