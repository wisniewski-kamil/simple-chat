package pl.sda;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pl.sda.login.LoginWindow;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatWindow extends Application {
    private VBox root = new VBox();
    private TextArea chat = new TextArea();
    private Socket client;
    private PrintWriter output;

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(root, 400, 300);
        prepareWindowContent();
        stage.setScene(scene);
        stage.show();
        // Making sure that threads will stop when window is closed
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
        client = new Socket("127.0.0.1", 5555);
        Scanner input = new Scanner(client.getInputStream());
        output = new PrintWriter(client.getOutputStream(), true);
        new Thread(() -> {
            while(input.hasNextLine()){
                chat.appendText(input.nextLine() + "\n");
            }
        }).start();
        LoginWindow.openLoginWindow(stage, client);
    }

    public static void main(String[] args) {
        launch();
    }

    private void prepareWindowContent(){
        // Create window content
        HBox inputBox = new HBox();
        TextField inputField = new TextField();
        Button sendBtn = new Button("Send");
        // Customize content
        sendBtn.setDefaultButton(true);
        sendBtn.setOnAction(actionEvent -> {
            String message = inputField.getText();
            if (message.isEmpty()){
                return;
            }
            output.println(inputField.getText());
            inputField.clear();
        });
        chat.setEditable(false);
        // Add window content to respective containers
        inputBox.getChildren().addAll(inputField, sendBtn);
        root.getChildren().addAll(chat, inputBox);
    }
}
