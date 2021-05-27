package pl.sda.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CommandLineChatClient {
    public static void main(String[] args) throws IOException {
        Socket client = new Socket("127.0.0.1", 5555);
        Scanner input = new Scanner(client.getInputStream());
        Scanner scanner = new Scanner(System.in);
        PrintWriter output = new PrintWriter(client.getOutputStream(), true);
        System.out.println("Welcome to Simple Chat\n" +
                "You must write commands to use the chat\n" +
                "Possible commands are:\n" +
                "LOGIN [username] [password]\n" +
                "REGISTER [username] [password]\n" +
                "SENDALL [message]\n" +
                "SENDTO [username] [message]\n" +
                "QUIT\n" +
                "Note: in order to receive messages and use SENDALL or SENDTO you must be logged in\n" +
                "If you don't have account yet use REGISTER command to create one and then use LOGIN");
        new Thread(() -> {
            while(input.hasNextLine()){
                System.out.println(input.nextLine());
            }
        }).start();
        while(true){
            String line = scanner.nextLine();
            if (line.equals("QUIT")){
                output.println("LOGOUT ");
                client.close();
                break;
            }
            output.println(line);
        }
    }
}
