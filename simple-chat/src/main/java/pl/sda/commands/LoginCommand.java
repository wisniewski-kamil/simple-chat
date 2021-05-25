package pl.sda.commands;

import pl.sda.client.ChatClient;
import pl.sda.database.DatabaseConnector;

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
        return DatabaseConnector.login(username, password, client);
    }
}
