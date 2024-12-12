package com.example.bloodbankjava;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SignUpScreen extends Application {
    private static final String FILE_PATH = "C:\\Users\\DELL\\Desktop\\BloodBank-java\\src\\main\\java\\com\\example\\bloodbankjava\\user.txt";

    @Override
    public void start(Stage primaryStage) {
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(20);
        layout.setPadding(new Insets(50));
        layout.setStyle("-fx-background-color: #000000;");

        Label titleLabel = new Label("Sign-Up");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #ffffff");

        TextField nameField = new TextField();
        nameField.setPromptText("Enter Name");
        nameField.setPrefWidth(300);

        TextField emailField = new TextField();
        emailField.setPromptText("Enter Email");
        emailField.setPrefWidth(300);

        TextField roleField = new TextField();
        roleField.setPromptText("Enter Role (Admin/Donor/Hospital)");
        roleField.setPrefWidth(300);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");
        passwordField.setPrefWidth(300);

        Button signUpButton = new Button("Sign Up");
        signUpButton.setStyle("-fx-background-color: #0d47a1; -fx-text-fill: white; -fx-font-size: 18;");
        signUpButton.setPrefWidth(300);

        signUpButton.setOnAction(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String role = roleField.getText();
            String password = passwordField.getText();

            if (name.isEmpty() || email.isEmpty() || role.isEmpty() || password.isEmpty()) {
                showAlert("Error", "All fields are required!", Alert.AlertType.ERROR);
                return;
            }

            int newUserId = generateUserId();

            User newUser = new User(newUserId, name, role, email, password);

            try {
                writeUserToFile(newUser);
                showAlert("Success", "Sign-Up Successful! You can now log in.", Alert.AlertType.INFORMATION);
                primaryStage.close(); // Close the Sign-Up window
            } catch (IOException ex) {
                showAlert("Error", "Could not save user data. Please try again.", Alert.AlertType.ERROR);
                ex.printStackTrace();
            }
        });

        layout.getChildren().addAll(titleLabel, nameField, emailField, roleField, passwordField, signUpButton);

        Scene scene = new Scene(layout, 800, 500);
        primaryStage.setTitle("Sign-Up");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private int generateUserId() {

        return (int) (Math.random() * 10000);
    }

    private void writeUserToFile(User user) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String userData = user.getUserId() + "," + user.getName() + "," + user.getRole() + "," + user.getEmail() + "," + user.getPassword();
            writer.write(userData);
            writer.newLine();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}