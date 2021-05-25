package pl.sda.server.commands;

import pl.sda.client.ChatClient;
import pl.sda.server.database.DatabaseConnector;
import pl.sda.server.commands.*;

public class LoginCommand implements Command{
    private String username;
    private String password;
    private ChatClient client;

    public LoginCommand(String username, String password, ChatClient client) {
        this.username = username;
        this.password = password;
        this.client = client;
    }

    @Override
    public boolean execute() {
        if(DatabaseConnector.login(username, password, client)){
            client.send("LOGIN-ACCEPTED");
            return true;
        } else{
            client.send("LOGIN-DENIED");
            return false;
        }
    }
}
