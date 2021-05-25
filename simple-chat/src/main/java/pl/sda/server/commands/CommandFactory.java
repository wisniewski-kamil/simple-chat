package pl.sda.server.commands;

import pl.sda.client.ChatClient;
import pl.sda.server.ChatServer;

public class CommandFactory {
    public static Command createCommand(String rawMessage, ChatClient origin, ChatServer server){
        switch (rawMessage.substring(0, rawMessage.indexOf(" "))){
            case "LOGIN":
                String[] commandElements = rawMessage.split(" ");
                return new LoginCommand(commandElements[1], commandElements[2], origin);
            case "SENDALL":
                String commandElement = rawMessage.substring(rawMessage.indexOf(" "));
                return new SendToAllCommand(commandElement, origin, server);
            default:
                String unknownCommandName = rawMessage.substring(0, rawMessage.indexOf(" "));
                return new UnknownCommand(unknownCommandName, origin, server);
        }
    }
}
