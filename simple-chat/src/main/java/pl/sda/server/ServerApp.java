package pl.sda.server;

import java.io.IOException;

public class ServerApp {
    public static void main(String[] args) throws IOException {
        new ChatServer(5555, 30).start();
    }
}
