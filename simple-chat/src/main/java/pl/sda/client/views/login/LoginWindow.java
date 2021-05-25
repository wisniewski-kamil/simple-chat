package pl.sda.client.views.login;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.sda.ChatWindow;
import pl.sda.client.ClientSocket;

public class LoginWindow {
    private static VBox root = new VBox();
    private static HBox buttonBox = new HBox();
    private static Stage loginStage = new Stage();
    private static ClientSocket client;
    private static TextField usernameField = new TextField();
    private static PasswordField passwordField = new PasswordField();
    private static Label informationLabel = new Label();
    private static ChatWindow parentWindow;

    public static void openLoginWindow(ChatWindow window, Stage owner, ClientSocket clientSocket){
        client = clientSocket;
        parentWindow = window;
        // Create window elements
        prepareInputFields();
        // Customize root and add all elements to it
        root.setPadding(new Insets(10));

        // Create and start a scene
        Scene scene = new Scene(root, 300, 150);
        loginStage.setScene(scene);
        loginStage.setTitle("Chat Log In");
        loginStage.initOwner(owner);
        loginStage.initModality(Modality.WINDOW_MODAL);
        loginStage.show();
    }

    private static void prepareButtonBox(){
        // Create box elements
        Button loginBtn = new Button("Log In");
        Button registerBtn = new Button("Register");
        // Customize elements
        loginBtn.setDefaultButton(true);
        loginBtn.setOnAction(event -> {
            client.getOutput().println("LOGIN " + usernameField.getText() + " " + passwordField.getText());
            usernameField.clear();
            passwordField.clear();
            String loginInfo = client.getInput().nextLine();
            if(loginInfo.equals("LOGIN-ACCEPTED")){
                loginStage.close();
                parentWindow.beginListeningForMessages();
            } else if(loginInfo.equals("LOGIN-DENIED")){
                informationLabel.setText("");
                informationLabel.setText("Something went wrong. Try again");
            }
        });
        // Customize box and add all elements to it
        buttonBox.setPadding(new Insets(10, 10, 0, 0));
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.BASELINE_CENTER);
        buttonBox.getChildren().addAll(loginBtn, registerBtn);
        root.getChildren().addAll(buttonBox, informationLabel);
    }

    private static void prepareInputFields(){
        Label usernameLabel = new Label("Username");
        Label passwordLabel = new Label("Password");
        root.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField);
        prepareButtonBox();
    }
}
