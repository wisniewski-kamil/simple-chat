package pl.sda.commands;

import pl.sda.client.ChatClient;

public class CommandFactory {
    public static Command createCommand(String rawMessage, ChatClient origin){
        switch (rawMessage.substring(0, rawMessage.indexOf(" "))){
            case "LOGIN":
                String[] commandElements = rawMessage.split(" ");
                return new LoginCommand(commandElements[1], commandElements[2], origin);
            default:
                return null;
        }
    }
}
