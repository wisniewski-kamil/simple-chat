package pl.sda.server.commands;

import pl.sda.client.ChatClient;
import pl.sda.server.ChatServer;
import pl.sda.server.database.DatabaseConnector;

import java.security.NoSuchAlgorithmException;

public class RegisterCommand implements Command{
    private String username;
    private String password;
    private ChatClient client;
    private ChatServer server;

    public RegisterCommand(String username, String password, ChatClient client, ChatServer server) {
        this.username = username;
        this.password = password;
        this.client = client;
        this.server = server;
    }

    @Override
    public boolean execute() {
        try {
            if(DatabaseConnector.getUserService().register(username, password)){
                client.send("REGISTER-ACCEPTED");
                server.getLogger().info("Client at " + client.getClientSocket().getInetAddress() + ":" + client.getClientSocket().getLocalPort() +
                        " successfully registered account: " + username);
                return true;
            } else{
                client.send("REGISTER-DENIED");
                server.getLogger().info("Client at " + client.getClientSocket().getInetAddress() + ":" + client.getClientSocket().getLocalPort() +
                        " tried to register account but failed");
                return false;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            server.getLogger().info("Client at " + client.getClientSocket().getInetAddress() + ":" + client.getClientSocket().getLocalPort() +
                    " tried to register account but failed");
            return false;
        }
    }
}
