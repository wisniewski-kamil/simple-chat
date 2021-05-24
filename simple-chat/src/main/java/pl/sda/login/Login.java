package pl.sda.login;

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

public class Login {
    private static VBox root = new VBox();
    private static HBox buttonBox = new HBox();
    private static Stage loginStage = new Stage();

    public static void openLoginWindow(Stage owner){
        // Create window elements
        prepareInputFields();
        prepareButtonBox();
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
            loginStage.close();
        });
        // Customize box and add all elements to it
        buttonBox.setPadding(new Insets(10, 10, 0, 0));
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.BASELINE_CENTER);
        buttonBox.getChildren().addAll(loginBtn, registerBtn);
        root.getChildren().add(buttonBox);
    }

    private static void prepareInputFields(){
        Label usernameLabel = new Label("Username");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();
        root.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField);
    }
}
