package pl.sda.server.commands;

import pl.sda.client.ChatClient;
import pl.sda.server.ChatServer;

public class SendToAllCommand implements Command{
    private String message;
    private ChatClient origin;
    private ChatServer server;

    public SendToAllCommand(String message, ChatClient origin, ChatServer server) {
        this.message = message;
        this.origin = origin;
        this.server = server;
    }

    @Override
    public boolean execute() {
        server.getClients().forEach(client -> {
            if (client == origin) {
                client.send("You: " + message);
            } else {
                client.send(origin.getUsername() + ": " + message);
            }
        });
        return true;
    }
}
