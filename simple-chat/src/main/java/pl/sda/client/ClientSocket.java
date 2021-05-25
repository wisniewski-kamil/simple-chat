package pl.sda.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientSocket {
    private Socket client;
    private PrintWriter output;
    private Scanner input;

    public ClientSocket() throws IOException {
        this.client = new Socket("127.0.0.1", 5555);
        this.output = new PrintWriter(client.getOutputStream(), true);
        this.input = new Scanner(client.getInputStream());
    }

    public Socket getClient() {
        return client;
    }

    public PrintWriter getOutput() {
        return output;
    }

    public Scanner getInput() {
        return input;
    }
}
