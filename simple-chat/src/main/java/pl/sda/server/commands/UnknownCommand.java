package pl.sda.server.commands;

import pl.sda.client.ChatClient;
import pl.sda.server.ChatServer;

public class UnknownCommand implements Command{
    private String message;
    private ChatClient origin;
    private ChatServer server;

    public UnknownCommand(String message, ChatClient origin, ChatServer server) {
        this.message = message;
        this.origin = origin;
        this.server = server;
    }

    @Override
    public boolean execute() {
        origin.send("UNKNOWN COMMAND: There is no such command as \"" + message +"\"");
        return true;
    }
}
