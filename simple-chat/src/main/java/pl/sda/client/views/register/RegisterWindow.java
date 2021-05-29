package pl.sda.client.views.register;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.sda.client.ClientSocket;
import pl.sda.client.controllers.ClientController;
import pl.sda.client.validators.ClientValidator;

public class RegisterWindow {
    private static ClientController client;
    private static VBox root = new VBox();
    private static Stage registerStage = new Stage();

    public static void openRegisterWindow(Stage owner, ClientController clientController){
        client = clientController;

        prepareStageElements();
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        Scene scene = new Scene(root, 300, 300);
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
            String validateMessage = ClientValidator.validateRegister(usernameField.getText(), passwordField.getText(), repeatPasswordField.getText());
            if(!validateMessage.isEmpty()){
                informationLabel.setText("");
                informationLabel.setText(validateMessage);
            }else{
                client.sendRegisterCommand(usernameField.getText(), passwordField.getText());
                usernameField.clear();
                passwordField.clear();
                repeatPasswordField.clear();
                String registerInfo = client.getInputNextLine();
                if(registerInfo.equals("REGISTER-ACCEPTED")){
                    informationLabel.setText("");
                    informationLabel.setText("User registered correctly.\nYou can close this window and log in");
                } else if(registerInfo.equals("REGISTER-DENIED")){
                    informationLabel.setText("");
                    informationLabel.setText("User with such username already exist!\nTry different username");
                }
            }
        });

        root.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField,
                repeatPasswordLabel, repeatPasswordField, registerBtn, informationLabel);
    }
}
