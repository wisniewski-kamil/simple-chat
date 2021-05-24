package pl.sda.client;

import pl.sda.server.ChatServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient implements Runnable{
    private final Socket clientSocket;
    private final ChatServer server;
    private Scanner input;
    private PrintWriter output;

    public ChatClient(Socket clientSocket, ChatServer server) throws IOException {
        this.clientSocket = clientSocket;
        this.output = new PrintWriter(clientSocket.getOutputStream(), true);
        this.input = new Scanner(clientSocket.getInputStream());
        this.server = server;
    }

    @Override
    public void run() {
        while (input.hasNextLine()) {
            String line = input.nextLine();
            server.process(line, this);
        }
    }

    public void send(String message) {
        output.println("Server echo: " + message);
    }
}
