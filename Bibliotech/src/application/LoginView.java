package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import objects.User;
import java.util.function.Consumer;

public class LoginView {
    
    public Scene getScene(Stage primaryStage, Consumer<User> onLoginSuccess) {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setSpacing(15);
        
        
        // Components
        
        
        Label titleLabel = new Label("Biblo Tech");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        TextField asuIdField = new TextField();
        asuIdField.setPromptText("ASU ID");
        asuIdField.setMaxWidth(200);
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(200);
        
        Button loginButton = new Button("Log In");
        loginButton.setMinWidth(100);
        
        Button signUpButton = new Button("Sign Up");
        signUpButton.setMinWidth(100);
        
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");
        
        
        // handling for login 
        
        
        loginButton.setOnAction(e -> {
            String asuId = asuIdField.getText().trim();
            String password = passwordField.getText();
            
            if (asuId.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Please enter both ASU ID and password");
                return;
            }
            
            
            // authenticate
            
            
            User user = AuthService.login(asuId, password);
            
            if (user != null) {
                errorLabel.setText("");
                System.out.println("Login successful for user: " + user.getAsuID());
                onLoginSuccess.accept(user);
            } else {
                errorLabel.setText("Invalid ASU ID or password");
                passwordField.clear();
            }
        });
        
        
        // handling for sign up 
        
        signUpButton.setOnAction(e -> {
            openSignUpDialog(primaryStage, errorLabel);
        });
        
        
        
        // Add all components
        
        
        root.getChildren().addAll(titleLabel, asuIdField, passwordField, loginButton, signUpButton, errorLabel);
        
        return new Scene(root, 400, 400);
    }
    
    private void openSignUpDialog(Stage parentStage, Label errorLabel) {
        Stage signUpStage = new Stage();
        signUpStage.setTitle("Sign Up");
        signUpStage.initOwner(parentStage);
        
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setSpacing(15);
        
        Label titleLabel = new Label("Create New Account");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        TextField newAsuIdField = new TextField();
        newAsuIdField.setPromptText("ASU ID");
        newAsuIdField.setMaxWidth(200);
        
        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("Password");
        newPasswordField.setMaxWidth(200);
        
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");
        confirmPasswordField.setMaxWidth(200);
        
        Button createButton = new Button("Create Account");
        Button cancelButton = new Button("Cancel");
        
        Label signUpErrorLabel = new Label();
        signUpErrorLabel.setStyle("-fx-text-fill: red;");
        
        createButton.setOnAction(e -> {
            String asuId = newAsuIdField.getText().trim();
            String password = newPasswordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            
            if (asuId.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                signUpErrorLabel.setText("Please fill in all fields");
                return;
            }
            
            if (!password.equals(confirmPassword)) {
                signUpErrorLabel.setText("Passwords do not match");
                return;
            }
            
            if (password.length() < 6) {
                signUpErrorLabel.setText("Password must be at least 6 characters");
                return;
            }
            
            
            
            
            // create new acccount
            
            
            
            User newUser = AuthService.signUp(asuId, password);
            
            if (newUser != null) {
                errorLabel.setText("Account created successfully! Please log in.");
                errorLabel.setStyle("-fx-text-fill: green;");
                signUpStage.close();
            } else {
                signUpErrorLabel.setText("Failed to create account. ASU ID may already exist.");
            }
        });
        
        cancelButton.setOnAction(e -> signUpStage.close());
        
        layout.getChildren().addAll(
            titleLabel,
            new Label("ASU ID:"), newAsuIdField,
            new Label("Password:"), newPasswordField,
            new Label("Confirm Password:"), confirmPasswordField,
            createButton, cancelButton,
            signUpErrorLabel
        );
        
        Scene signUpScene = new Scene(layout, 350, 400);
        signUpStage.setScene(signUpScene);
        signUpStage.show();
    }
}
