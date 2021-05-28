package pl.sda.client.controllers;

import pl.sda.client.ClientSocket;

public class ClientController {
    private ClientSocket client;

    public ClientController(ClientSocket client) {
        this.client = client;
    }

    public void sendSandToAllCommand(String message){
        client.getOutput().println("SENDALL " + message);
    }

    public void sendLoginCommand(String username, String password){
        client.getOutput().println("LOGIN " + username + " " + password);
    }

    public void sendRegisterCommand(String username, String password){
        client.getOutput().println("REGISTER " + username + " " + password);
    }

    public void sendSendDirectlyCommand(String username, String message){
        client.getOutput().println("SENDTO " + username + " " + message);
    }

    public void sendLogoutCommand(){
        client.getOutput().println("LOGOUT ");
    }

    public boolean hasInputNextLine(){
        return client.getInput().hasNextLine();
    }

    public String getInputNextLine(){
        return client.getInput().nextLine();
    }
}
