package pl.sda.server.commands;

import pl.sda.client.ChatClient;
import pl.sda.server.ChatServer;

public class CommandFactory {
    public static Command createCommand(String rawMessage, ChatClient origin, ChatServer server){
        switch (rawMessage.substring(0, rawMessage.indexOf(" "))){
            case "LOGIN":
                String[] loginElements = rawMessage.split(" ");
                return new LoginCommand(loginElements[1], loginElements[2], origin, server);
            case "REGISTER":
                String[] registerElements = rawMessage.split(" ");
                return new RegisterCommand(registerElements[1], registerElements[2], origin, server);
            case "SENDALL":
                String commandElement = rawMessage.substring(rawMessage.indexOf(" ") + 1);
                return new SendToAllCommand(commandElement, origin, server);
            case "SENDTO":
                String destinyAndMessage = rawMessage.substring(rawMessage.indexOf(" ") + 1);
                String destiny = destinyAndMessage.substring(0, rawMessage.indexOf(" "));
                String message = destinyAndMessage.substring(rawMessage.indexOf(" ") + 1);
                return new SendDirectlyCommand(message, destiny, origin, server);
            case "LOGOUT":
                return new LogoutCommand(origin, server);
            default:
                String unknownCommandName = rawMessage.substring(0, rawMessage.indexOf(" ") + 1);
                return new UnknownCommand(unknownCommandName, origin, server);
        }
    }
}
