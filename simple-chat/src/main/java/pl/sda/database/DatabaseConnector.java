package pl.sda.database;

import pl.sda.client.ChatClient;
import pl.sda.database.entities.User;
import pl.sda.database.repositories.JpaUserRepository;
import pl.sda.encryptor.PasswordEncryptor;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;

public class DatabaseConnector {
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("chat");
    private static JpaUserRepository userRepo = new JpaUserRepository(factory, User.class);

    public static boolean login(String username, String password, ChatClient client){
        Optional<User> optionalUser = userRepo.findById(username);
        if(optionalUser.isEmpty()){
            return false;
        }
        User user = optionalUser.get();
        if (checkPassword(user, password)){
            client.login(username);
            return true;
        }
        return false;
    }

    private static boolean checkPassword(User user, String password){
        return user.getCodedPassword().equals(PasswordEncryptor.encrypt(password));
    }

    public static boolean register(String username, String password){
        Optional<User> optionalUser = userRepo.findById(username);
        if(optionalUser.isPresent()){
            return false;
        }
        User userToRegister = new User(username, PasswordEncryptor.encrypt(password), false);
        userRepo.save(userToRegister);
        return true;
    }
}
