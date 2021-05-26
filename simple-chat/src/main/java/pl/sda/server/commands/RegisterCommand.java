package pl.sda.server.commands;

import pl.sda.client.ChatClient;
import pl.sda.server.database.DatabaseConnector;

import java.security.NoSuchAlgorithmException;

public class RegisterCommand implements Command{
    private String username;
    private String password;
    private ChatClient client;

    public RegisterCommand(String username, String password, ChatClient client) {
        this.username = username;
        this.password = password;
        this.client = client;
    }

    @Override
    public boolean execute() {
        try {
            if(DatabaseConnector.register(username, password)){
                client.send("REGISTER-ACCEPTED");
                return true;
            } else{
                client.send("REGISTER-DENIED");
                return false;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }
}
