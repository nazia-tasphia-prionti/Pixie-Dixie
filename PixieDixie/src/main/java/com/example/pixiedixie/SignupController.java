package com.example.pixiedixie;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Hyperlink;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SignupController {


    @FXML
    private TextField emailField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label errorLabel;

    @FXML
    private Hyperlink loginLink;


    @FXML
    private void initialize() {
        emailField.textProperty().addListener((observable, oldValue, newValue) -> validateEmail());
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> validatePassword());
        confirmPasswordField.textProperty().addListener((observable, oldValue, newValue) -> validatePasswordMatch());
        loginLink.setOnAction(event -> handleLoginLink());
    }

    @FXML
    private void handleSignup() {
        if (isFormValid()) {
            if (isAccountExists(emailField.getText(), usernameField.getText(), passwordField.getText())) {
                errorLabel.setTextFill(Color.RED);
                errorLabel.setText("This account already exists.");
            } else {
                errorLabel.setTextFill(Color.GREEN);
                errorLabel.setText("Account Created Successfully");
                writeSignupInfoToFile(emailField.getText(), usernameField.getText(), passwordField.getText());
            }
        }
    }

    private boolean isFormValid() {
        if (emailField.getText().isEmpty() || usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty()) {
            errorLabel.setTextFill(Color.RED);
            errorLabel.setText("All fields are required.");
            return false;
        }
        return validateEmail() && validatePassword() && validatePasswordMatch();
    }

    private boolean validateEmail() {
        String email = emailField.getText();
        if (email.isEmpty() || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            errorLabel.setTextFill(Color.RED);
            errorLabel.setText("Invalid email format.");
            return false;
        }
        errorLabel.setText("");
        return true;
    }

    private boolean validatePassword() {
        String password = passwordField.getText();
        if (password.length() < 6) {
            errorLabel.setTextFill(Color.RED);
            errorLabel.setText("Password must be at least 6 characters.");
            return false;
        }
        errorLabel.setText("");
        return true;
    }

    private boolean validatePasswordMatch() {
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            errorLabel.setTextFill(Color.RED);
            errorLabel.setText("Passwords do not match.");
            return false;
        }
        errorLabel.setText("");
        return true;
    }

    private boolean isAccountExists(String email, String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("signup_info.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Email: " + email) && line.contains("Username: " + username) && line.contains("Password: " + password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void writeSignupInfoToFile(String email, String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("signup_info.txt", true))) {
            writer.write("Email: " + email + ", Username: " + username + ", Password: " + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleLoginLink() {
        Stage stage = (Stage) loginLink.getScene().getWindow();
        ScreenController screenController = new ScreenController(stage);
        screenController.changeScreen("/com/example/pixiedixie/spaceLogin.fxml");
    }
}