package pl.sda.server.commands;

import pl.sda.client.ChatClient;
import pl.sda.server.ChatServer;

import java.util.Optional;

public class SendDirectlyCommand implements Command{
    private String message;
    private ChatClient origin;
    private ChatServer server;
    private String usernameDestiny;

    public SendDirectlyCommand(String message, String usernameDestiny, ChatClient origin, ChatServer server) {
        this.message = message;
        this.origin = origin;
        this.server = server;
        this.usernameDestiny = usernameDestiny;
    }

    @Override
    public boolean execute() {
        Optional<ChatClient> destiny = server.findByUsername(usernameDestiny);
        if(destiny.isPresent()){
            destiny.get().send(origin.getUsername() + " -> [you]: " + message);
            origin.send("You -> [" + usernameDestiny + "]: " + message);
            server.getLogger().info("Client at " + origin.getClientSocket().getInetAddress() + ":" + origin.getClientSocket().getLocalPort() +
                    " sent message: " + message + " to user: " + usernameDestiny);
            return true;
        }
        origin.send("Your message wasn't delivered: " + usernameDestiny + " isn't available right now ");
        server.getLogger().info("Client at " + origin.getClientSocket().getInetAddress() + ":" + origin.getClientSocket().getLocalPort() +
                " tried to send message: " + message + " to user: " + usernameDestiny + " but failed");
        return false;
    }
}
