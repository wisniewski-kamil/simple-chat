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
import pl.sda.client.ClientSocket;
import pl.sda.client.views.login.*;

public class ChatWindow extends Application {
    private VBox root = new VBox();
    private TextArea chat = new TextArea();
    private ClientSocket client;

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
        client = new ClientSocket();
        LoginWindow.openLoginWindow(this, stage, client);
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
            client.getOutput().println("SENDALL " + inputField.getText());
            inputField.clear();
        });
        chat.setEditable(false);
        // Add window content to respective containers
        inputBox.getChildren().addAll(inputField, sendBtn);
        root.getChildren().addAll(chat, inputBox);
    }

    public void beginListeningForMessages(){
        new Thread(() -> {
            while(client.getInput().hasNextLine()){
                chat.appendText(client.getInput().nextLine() + "\n");
            }
        }).start();
    }
}
