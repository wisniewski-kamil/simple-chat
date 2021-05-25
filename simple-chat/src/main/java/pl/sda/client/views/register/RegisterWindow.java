package pl.sda.client.views.register;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.sda.client.ClientSocket;

public class RegisterWindow {
    private static ClientSocket client;
    private static VBox root = new VBox();
    private static Stage registerStage = new Stage();

    public static void openRegisterWindow(Stage owner, ClientSocket clientSocket){
        client = clientSocket;

        prepareStageElements();

        Scene scene = new Scene(root, 300, 200);
        registerStage.setScene(scene);
        registerStage.setTitle("Chat Register");
        registerStage.initOwner(owner);
        registerStage.initModality(Modality.WINDOW_MODAL);
        registerStage.show();
    }

    private static void prepareStageElements(){
        Label usernameLabel = new Label("Username");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();
        Label repeatPasswordLabel = new Label("Repeat password");
        PasswordField repeatPasswordField = new PasswordField();
        Button registerBtn = new Button("Register");
        Label informationLabel = new Label();

        registerBtn.setDefaultButton(true);
        registerBtn.setOnAction(event -> {
            if(usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || repeatPasswordField.getText().isEmpty()){
                informationLabel.setText("");
                informationLabel.setText("All fields must be filled");
            } else if (!passwordField.getText().equals(repeatPasswordField.getText())){
                informationLabel.setText("");
                informationLabel.setText("You must repeat the same password");
            } else{
                client.getOutput().println("REGISTER " + usernameField.getText() + " " + passwordField.getText());
                usernameField.clear();
                passwordField.clear();
                repeatPasswordField.clear();
                String registerInfo = client.getInput().nextLine();
                if(registerInfo.equals("REGISTER-ACCEPTED")){
                    informationLabel.setText("");
                    informationLabel.setText("User registered correctly. You can close this window and log in");
                } else if(registerInfo.equals("REGISTER-DENIED")){
                    informationLabel.setText("");
                    informationLabel.setText("User with such username already exist! Try different username");
                }
            }
        });

        root.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField,
                repeatPasswordLabel, repeatPasswordField, registerBtn, informationLabel);
    }
}
