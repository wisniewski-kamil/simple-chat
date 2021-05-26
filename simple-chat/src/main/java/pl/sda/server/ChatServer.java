package pl.sda.server;

import pl.sda.client.ChatClient;
import pl.sda.server.commands.*;
import pl.sda.server.commands.LoginCommand;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class ChatServer{
    private final ServerSocket serverSocket;
    private final ExecutorService service;
    private Logger logger = Logger.getLogger(ChatServer.class.getName());
    private List<ChatClient> clients;

    public ChatServer(int port, int maxClients) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.service = Executors.newFixedThreadPool(maxClients);
        logger.info("Socket server at " + serverSocket.getInetAddress() + ":" + serverSocket.getLocalPort());
        clients = Collections.synchronizedList(new ArrayList<>());
    }

    public void start(){
        while(true) {
            try {
                Socket clientSocket = serverSocket.accept();
                logger.info("Client connected at " + clientSocket.getInetAddress() + ":" + clientSocket.getLocalPort());
                ChatClient client = new ChatClient(clientSocket, this);
                clients.add(client);
                service.execute(client);
            } catch (IOException e) {
                logger.warning("Can't connect to client: " + e.getMessage());
            }
        }
    }

    public void process(String rawMessage, ChatClient origin) {
        if(!origin.isLoggedIn()){
            Command potentialLogInOrRegister = CommandFactory.createCommand(rawMessage, origin, this);
            if(potentialLogInOrRegister.getClass() == LoginCommand.class || potentialLogInOrRegister.getClass() == RegisterCommand.class){
                potentialLogInOrRegister.execute();
            }
        } else {
            Command command = CommandFactory.createCommand(rawMessage, origin, this);
            command.execute();
        }
    }

    public boolean checkIfUserIsLoggedInByUsername(String username){
        for(ChatClient client : clients){
            if(client.getUsername() != null && username.equals(client.getUsername())){
                return true;
            }
        }
        return false;
    }

    public List<ChatClient> getClients(){
        return clients;
    }

    public Logger getLogger(){
        return logger;
    }
}
