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
import pl.sda.client.controllers.ClientController;
import pl.sda.client.views.login.*;

public class ChatWindow extends Application {
    private HBox root = new HBox();
    private VBox rightSideOfWindow = new VBox();
    private VBox leftSideOfWindow = new VBox();
    private TextField inputField = new TextField();
    private TextArea chat = new TextArea();
    private ClientSocket client;
    private ClientController clientController;
    private HBox emojiPicker = new HBox();

    @Override
    public void start(Stage stage) throws Exception {
        client = new ClientSocket("127.0.0.1", 5555);
        clientController = new ClientController(client);

        prepareWindowContent();
        prepareDirectMessage();
        leftSideOfWindow.setPadding(new Insets(10));
        leftSideOfWindow.setSpacing(10);
        leftSideOfWindow.setMaxWidth(500);
        rightSideOfWindow.setMinWidth(120);
        rightSideOfWindow.setPadding(new Insets(10));
        rightSideOfWindow.setSpacing(10);
        root.getChildren().addAll(leftSideOfWindow, rightSideOfWindow);

        Scene scene = new Scene(root, 650, 350);
        stage.setScene(scene);
        stage.setTitle("Simple Chat");
        stage.show();
        // Making sure that threads will stop when window is closed
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                clientController.sendLogoutCommand();
                System.exit(0);
            }
        });

        LoginWindow.openLoginWindow(this, stage, clientController);
    }

    public static void main(String[] args) {
        launch();
    }

    private void prepareWindowContent(){
        // Create window content
        HBox inputBox = new HBox();
        Button sendBtn = new Button("Send");
        ToggleButton emojiBtn = new ToggleButton(":)");
        prepareEmojiPicker();
        // Customize content
        inputBox.setSpacing(10);
        inputField.setPrefWidth(400);
        inputField.setMaxWidth(450);
        emojiBtn.setMinWidth(30);
        emojiBtn.setOnAction(event -> {
            if(emojiBtn.isSelected()) {
                leftSideOfWindow.getChildren().add(emojiPicker);
            } else{
                leftSideOfWindow.getChildren().remove(emojiPicker);
            }
        });
        sendBtn.setMinWidth(50);
        sendBtn.setDefaultButton(true);
        sendBtn.setOnAction(actionEvent -> {
            String message = inputField.getText();
            if (message.isEmpty()){
                return;
            }
            clientController.sendSandToAllCommand(inputField.getText());
            inputField.clear();
        });
        chat.setEditable(false);
        chat.setFont(Font.font(16));
        // Add window content to respective containers
        inputBox.getChildren().addAll(inputField,emojiBtn, sendBtn);
        leftSideOfWindow.getChildren().addAll(chat, inputBox);
    }

    public void beginListeningForMessages(){
        new Thread(() -> {
            while(clientController.hasInputNextLine()){
                String line = clientController.getInputNextLine();
                chat.appendText(line + "\n");
            }
        }).start();
    }

    private void prepareEmojiPicker(){
        Button whiteSmileyFaceBtn = createEmojiButton("☺");
        Button blackSmileyFaceBtn = createEmojiButton("☻");
        Button sunBtn = createEmojiButton("☼");
        Button moonBtn = createEmojiButton("☽");
        Button pieceBtn = createEmojiButton("☮");
        Button yinYangBtn = createEmojiButton("☯");
        Button heartBtn = createEmojiButton("♥");
        Button starBtn = createEmojiButton("☆");

        emojiPicker.setSpacing(10);

        emojiPicker.getChildren().addAll(whiteSmileyFaceBtn, blackSmileyFaceBtn, sunBtn, moonBtn, pieceBtn, yinYangBtn,
                heartBtn, starBtn);
    }

    private void prepareDirectMessage(){
        Label availableUsersInfoLabel = new Label("Write user's nick\nand press the button\nto send message\ndirectly to them");
        TextField directMessageUserField = new TextField();
        Button dmBtn = new Button("Send DM");

        availableUsersInfoLabel.setMinHeight(50);
        dmBtn.setOnAction(event -> {
            String message = inputField.getText();
            if (message.isEmpty()){
                return;
            }
            clientController.sendSendDirectlyCommand(directMessageUserField.getText(), message);
            directMessageUserField.clear();
            inputField.clear();
        });

        rightSideOfWindow.getChildren().addAll(availableUsersInfoLabel, directMessageUserField, dmBtn);
    }

    private Button createEmojiButton(String emoji){
        Button button = new Button(emoji);

        button.setFont(Font.font(16));

        button.setOnAction(event -> {
            inputField.appendText(emoji);
        });

        return button;
    }
}
