
package com.example.bloodbankjava;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;

public class DonorDashboard extends Application {

    private DonorService donorService = new DonorService();
    private User donor;

    public DonorDashboard(User donor) {
        this.donor = donor;
    }

    @Override
    public void start(Stage primaryStage) {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Donor Dashboard - Welcome, " + donor.getName());
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        ImageView imageView = new ImageView();
        try {
            Image image = new Image("file:C:\\Users\\DELL\\Desktop\\BloodBank-java\\src\\main\\resources\\com\\example\\bloodbankjava\\donor.jpg");
            imageView.setImage(image);
            imageView.setFitWidth(300);
            imageView.setPreserveRatio(true);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
        }

        HBox buttonLayout = new HBox(15);
        buttonLayout.setAlignment(Pos.CENTER);

        Button viewHistoryButton = new Button("View Donation History");
        Button addDonationButton = new Button("Add Donation");
        Button logoutButton = new Button("Logout");

        String buttonStyle = "-fx-background-color: #4f1111; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-radius: 5px; -fx-background-radius: 5px;";
        viewHistoryButton.setStyle(buttonStyle);
        addDonationButton.setStyle(buttonStyle);
        logoutButton.setStyle(buttonStyle);

        viewHistoryButton.setPrefWidth(250);
        addDonationButton.setPrefWidth(200);
        logoutButton.setPrefWidth(150);

        buttonLayout.getChildren().addAll(viewHistoryButton, addDonationButton, logoutButton);
        VBox.setVgrow(buttonLayout, Priority.ALWAYS);

        layout.getChildren().addAll(titleLabel, imageView, buttonLayout);

        viewHistoryButton.setOnAction(e -> {
            try {
                String history = donorService.getDonationHistory(donor.getUserId());
                showAlert("Donation History", history, Alert.AlertType.INFORMATION);
            } catch (IOException ex) {
                ex.printStackTrace();
                showAlert("Error", "Could not load donation history.", Alert.AlertType.ERROR);
            }
        });

        addDonationButton.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add Donation");
            dialog.setHeaderText("Enter Blood Type:");
            dialog.setContentText("Blood Type:");

            dialog.showAndWait().ifPresent(bloodType -> {
                try {
                    donorService.addDonation(donor.getUserId(), bloodType);
                    showAlert("Success", "Donation added successfully.", Alert.AlertType.INFORMATION);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    showAlert("Error", "Could not add donation.", Alert.AlertType.ERROR);
                }
            });
        });

        logoutButton.setOnAction(e -> {
            primaryStage.close();
            new Main().start(new Stage());
        });

        Scene scene = new Scene(layout, 600, 500);
        primaryStage.setTitle("Donor Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}