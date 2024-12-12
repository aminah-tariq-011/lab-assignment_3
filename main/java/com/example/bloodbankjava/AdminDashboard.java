package com.example.bloodbankjava;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AdminDashboard extends Application {

    private User admin;
    private RequestService requestService = new RequestService();

    public AdminDashboard(User admin) {
        this.admin = admin;
    }

    @Override
    public void start(Stage primaryStage) {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Admin Dashboard - Welcome, " + admin.getName());
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        ImageView imageView = new ImageView();
        try {
            Image image = new Image("file:C:\\Users\\DELL\\Desktop\\BloodBank-java\\src\\main\\resources\\com\\example\\bloodbankjava\\admin.png");
            imageView.setImage(image);
            imageView.setFitWidth(300);
            imageView.setFitHeight(300);
            imageView.setPreserveRatio(true);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
        }

        HBox buttonLayout = new HBox(15);
        buttonLayout.setAlignment(Pos.CENTER);

        Button viewReportsButton = new Button("View Donation Reports");
        Button manageUsersButton = new Button("Manage Users");
        Button logoutButton = new Button("Logout");

        String buttonStyle = "-fx-background-color: #390a3c; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-radius: 5px; -fx-background-radius: 5px;";
        viewReportsButton.setStyle(buttonStyle);
        manageUsersButton.setStyle(buttonStyle);
        logoutButton.setStyle(buttonStyle);

        viewReportsButton.setPrefWidth(250);
        manageUsersButton.setPrefWidth(200);
        logoutButton.setPrefWidth(200);

        buttonLayout.getChildren().addAll(viewReportsButton, manageUsersButton, logoutButton);
        VBox.setVgrow(buttonLayout, Priority.ALWAYS);

        layout.getChildren().addAll(titleLabel, imageView, buttonLayout);

        viewReportsButton.setOnAction(e -> {
            try {
                String reports = requestService.getAllRequestsFormatted();
                showAlert("Donation Reports", reports, Alert.AlertType.INFORMATION);
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert("Error", "Could not load donation reports.", Alert.AlertType.ERROR);
            }
        });

        manageUsersButton.setOnAction(e -> {
            openManageUsersWindow();
        });

        logoutButton.setOnAction(e -> {
            primaryStage.close();
            new Main().start(new Stage());
        });

        Scene scene = new Scene(layout, 600, 500);
        primaryStage.setTitle("Admin Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void openManageUsersWindow() {
        Stage manageUsersStage = new Stage();
        manageUsersStage.setTitle("Manage Users");

        VBox manageUsersLayout = new VBox(20);
        manageUsersLayout.setPadding(new Insets(20));
        manageUsersLayout.setAlignment(Pos.CENTER);
        Label manageUsersTitle = new Label("Manage Users");
        manageUsersTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        TableView<User> userTable = new TableView<>();
        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        userTable.getColumns().addAll(nameColumn, emailColumn);
        userTable.getItems().addAll(Userservice.getAllUsers());

        Button addUserButton = new Button("Add User");
        Button editUserButton = new Button("Edit User");
        Button removeUserButton = new Button("Remove User");

        String buttonStyle = "-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-radius: 5px; -fx-background-radius: 5px;";
        addUserButton.setStyle(buttonStyle);
        editUserButton.setStyle(buttonStyle);
        removeUserButton.setStyle(buttonStyle);
        HBox manageButtonLayout = new HBox(15);
        manageButtonLayout.setAlignment(Pos.CENTER);
        manageButtonLayout.getChildren().addAll(addUserButton, editUserButton, removeUserButton);
        manageUsersLayout.getChildren().addAll(manageUsersTitle, userTable, manageButtonLayout);
        Scene manageUsersScene = new Scene(manageUsersLayout, 600, 400);
        manageUsersStage.setScene(manageUsersScene);
        manageUsersStage.show();
    }
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
