package pl.sda;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pl.sda.client.ClientSocket;
import pl.sda.client.views.login.*;

public class ChatWindow extends Application {
    private VBox root = new VBox();
    private TextArea chat = new TextArea();
    private ClientSocket client;
    private HBox emojiPicker = new HBox();

    @Override
    public void start(Stage stage) throws Exception {
        client = new ClientSocket("127.0.0.1", 5555);

        prepareWindowContent();
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Simple Chat");
        stage.show();
        // Making sure that threads will stop when window is closed
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                client.getOutput().println("LOGOUT ");
                System.exit(0);
            }
        });

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
        ToggleButton emojiBtn = new ToggleButton(":)");
        prepareEmojiPicker(inputField);
        // Customize content
        inputBox.setSpacing(10);
        inputField.setPrefWidth(500);
        emojiBtn.setMinWidth(30);
        emojiBtn.setOnAction(event -> {
            if(emojiBtn.isSelected()) {
                root.getChildren().add(emojiPicker);
            } else{
                root.getChildren().remove(emojiPicker);
            }
        });
        sendBtn.setMinWidth(50);
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
        chat.setFont(Font.font(16));
        // Add window content to respective containers
        inputBox.getChildren().addAll(inputField,emojiBtn, sendBtn);
        root.getChildren().addAll(chat, inputBox);
    }

    public void beginListeningForMessages(){
        new Thread(() -> {
            while(client.getInput().hasNextLine()){
                chat.appendText(client.getInput().nextLine() + "\n");
            }
        }).start();
    }

    private void prepareEmojiPicker(TextField textField){
        Button whiteSmileyFaceBtn = createEmojiButton("☺", textField);
        Button blackSmileyFaceBtn = createEmojiButton("☻", textField);
        Button sunBtn = createEmojiButton("☼", textField);
        Button moonBtn = createEmojiButton("☽", textField);
        Button pieceBtn = createEmojiButton("☮", textField);
        Button yinYangBtn = createEmojiButton("☯", textField);
        Button heartBtn = createEmojiButton("♥", textField);
        Button starBtn = createEmojiButton("☆", textField);

        emojiPicker.setSpacing(10);

        emojiPicker.getChildren().addAll(whiteSmileyFaceBtn, blackSmileyFaceBtn, sunBtn, moonBtn, pieceBtn, yinYangBtn,
                heartBtn, starBtn);
    }

    private Button createEmojiButton(String emoji, TextField textField){
        Button button = new Button(emoji);

        button.setFont(Font.font(16));

        button.setOnAction(event -> {
            textField.appendText(emoji);
        });

        return button;
    }
}
