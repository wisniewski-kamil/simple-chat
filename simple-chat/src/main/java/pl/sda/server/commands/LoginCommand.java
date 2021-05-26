package pl.sda.server.commands;

import pl.sda.client.ChatClient;
import pl.sda.server.ChatServer;
import pl.sda.server.database.DatabaseConnector;

public class LoginCommand implements Command{
    private String username;
    private String password;
    private ChatClient client;
    private ChatServer server;

    public LoginCommand(String username, String password, ChatClient client, ChatServer server) {
        this.username = username;
        this.password = password;
        this.client = client;
        this.server = server;
    }

    @Override
    public boolean execute() {
        if(!server.checkIfUserIsLoggedInByUsername(username) && DatabaseConnector.login(username, password, client)){
            client.send("LOGIN-ACCEPTED");
            server.getLogger().info("Client at " + client.getClientSocket().getInetAddress() + ":" + client.getClientSocket().getLocalPort() +
            " successfully logged in to account: " + client.getUsername());
            return true;
        } else{
            client.send("LOGIN-DENIED");
            server.getLogger().info("Client at " + client.getClientSocket().getInetAddress() + ":" + client.getClientSocket().getLocalPort() +
                    " tried to log in but failed");
            return false;
        }
    }
}
