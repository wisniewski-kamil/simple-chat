package pl.sda.server.database.services;

import pl.sda.client.ChatClient;
import pl.sda.server.database.entities.User;
import pl.sda.server.database.repositories.JpaUserRepository;
import pl.sda.server.encryptor.PasswordEncryptor;

import javax.persistence.EntityManagerFactory;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class UserService {
    private static JpaUserRepository userRepo;

    public UserService(EntityManagerFactory factory){
        userRepo = new JpaUserRepository(factory, User.class);
    }

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
        try {
            return user.getCodedPassword().equals(PasswordEncryptor.encrypt(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean register(String username, String password) throws NoSuchAlgorithmException {
        Optional<User> optionalUser = userRepo.findById(username);
        if(optionalUser.isPresent()){
            return false;
        }
        User userToRegister = new User(username, PasswordEncryptor.encrypt(password));
        userRepo.save(userToRegister);
        return true;
    }
}
