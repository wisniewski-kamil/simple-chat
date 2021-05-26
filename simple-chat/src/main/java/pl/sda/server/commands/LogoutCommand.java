package pl.sda.server.commands;

import pl.sda.client.ChatClient;
import pl.sda.server.ChatServer;

public class LogoutCommand implements Command{
    private ChatClient client;
    private ChatServer server;

    public LogoutCommand(ChatClient client, ChatServer server) {
        this.client = client;
        this.server = server;
    }

    @Override
    public boolean execute() {
        server.getLogger().info("Client at " + client.getClientSocket().getInetAddress() + ":" + client.getClientSocket().getLocalPort() +
                " successfully logged out from account: " + client.getUsername());
        new SendToAllCommand("~left the chat~", client, server).execute();
        server.getClients().remove(client);
        return true;
    }
}
