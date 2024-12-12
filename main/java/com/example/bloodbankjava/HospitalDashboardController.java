
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

class HospitalDashboard extends Application {

    private User hospital;
    private RequestService requestService = new RequestService();

    public HospitalDashboard(User hospital) {
        this.hospital = hospital;
    }

    @Override
    public void start(Stage primaryStage) {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #f4f4f4;");

        Label titleLabel = new Label("Hospital Dashboard - " + hospital.getName());
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setStyle("-fx-text-fill: #333333;");

        Image hospitalImage = new Image("file:C:\\Users\\DELL\\Desktop\\BloodBank-java\\src\\main\\java\\com\\example\\bloodbankjava\\admin.webp"); // Replace with your image path
        ImageView imageView = new ImageView(hospitalImage);
        imageView.setFitWidth(200);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);

        Button viewRequestsButton = new Button("View Request Status");
        Button makeRequestButton = new Button("Make New Blood Request");
        Button logoutButton = new Button("Logout");

        String buttonStyle = "-fx-background-color: #2a4b63; -fx-text-fill: white; -fx-font-size: 16; -fx-padding: 10;";
        viewRequestsButton.setStyle(buttonStyle);
        makeRequestButton.setStyle(buttonStyle);
        logoutButton.setStyle(buttonStyle);

        HBox buttonLayout = new HBox(10, viewRequestsButton, makeRequestButton, logoutButton);
        buttonLayout.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(titleLabel, imageView, buttonLayout);

        viewRequestsButton.setOnAction(e -> viewRequests());
        makeRequestButton.setOnAction(e -> makeNewRequest());
        logoutButton.setOnAction(e -> {
            primaryStage.close();
            new Main().start(new Stage());
        });

        Scene scene = new Scene(layout, 600, 500);
        primaryStage.setTitle("Hospital Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void viewRequests() {
        try {
            String rawStatus = requestService.getRequestStatusForHospital(hospital.getUserId());
            if (rawStatus == null || rawStatus.isEmpty()) {
                showAlert("Request Status", "No requests found.", Alert.AlertType.INFORMATION);
            } else {
                String[] requests = rawStatus.split(";");
                StringBuilder formattedStatus = new StringBuilder();
                for (String request : requests) {
                    String[] details = request.split(",");
                    formattedStatus.append("Blood Type: ").append(details[0].trim()).append("\n")
                            .append("Quantity: ").append(details[1].trim()).append("\n")
                            .append("Priority: ").append(details[2].trim()).append("\n")
                            .append("Status: ").append(details[3].trim()).append("\n")
                            .append("Date: ").append(details[4].trim()).append("\n\n");
                }
                showAlert("Request Status", formattedStatus.toString(), Alert.AlertType.INFORMATION);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Error", "Could not load request status.", Alert.AlertType.ERROR);
        }
    }

    private void makeNewRequest() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Blood Request");
        dialog.setHeaderText("Enter Blood Type and Quantity:");
        dialog.setContentText("Blood Type, Quantity (comma separated):");

        dialog.showAndWait().ifPresent(input -> {
            try {
                String[] requestDetails = input.split(",");
                String bloodType = requestDetails[0].trim();
                int quantity = Integer.parseInt(requestDetails[1].trim());

                requestService.addRequest(new BloodRequest(
                        requestService.getNextRequestId(),
                        hospital.getUserId(),
                        bloodType,
                        quantity,
                        "High",  // Default Priority
                        "Pending",  // Default Status
                        java.time.LocalDate.now()
                ));
                showAlert("Success", "Request for " + bloodType + " created successfully.", Alert.AlertType.INFORMATION);
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert("Error", "Could not create request.", Alert.AlertType.ERROR);
            }
        });
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
