package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import objects.User;

public class MainMenuView {
    
    public Scene getScene(Stage primaryStage, User currentUser, Runnable onBuyBooks, Runnable onSellerDashboard, Runnable onAdminPanel, Runnable onLogout) {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setSpacing(20);
        
        
        
        
        
        // Welcome
        
        
        Label welcomeLabel = new Label("Welcome, User " + currentUser.getAsuID());
        welcomeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        Label roleLabel = new Label("Role: " + currentUser.getRole());
        roleLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");
        
        
        
        // Components
        
        
        Button buyBooksButton = new Button("Buy Books");
        buyBooksButton.setMinSize(200, 50);
        buyBooksButton.setStyle("-fx-font-size: 14px;");
        
        Button sellerDashboardButton = new Button("Seller Dashboard");
        sellerDashboardButton.setMinSize(200, 50);
        sellerDashboardButton.setStyle("-fx-font-size: 14px;");
        
        Button adminPanelButton = new Button("Administrator Panel");
        adminPanelButton.setMinSize(200, 50);
        adminPanelButton.setStyle("-fx-font-size: 14px;");
        
        Button logoutButton = new Button("Log Out");
        logoutButton.setMinSize(120, 35);
        logoutButton.setStyle("-fx-font-size: 12px; -fx-background-color: #f44336; -fx-text-fill: white;");
        
        
        
        // Event handling
        
        
        buyBooksButton.setOnAction(e -> {
            System.out.println("Navigating to Buy Books");
            onBuyBooks.run();
        });
        
        sellerDashboardButton.setOnAction(e -> {
            System.out.println("Navigating to Seller Dashboard");
            onSellerDashboard.run();
        });
        
        adminPanelButton.setOnAction(e -> {
            System.out.println("Navigating to Administrator Panel");
            onAdminPanel.run();
        });
        
        logoutButton.setOnAction(e -> {
            System.out.println("Logging out");
            onLogout.run();
        });
        
        
        
        // components in root
        
        root.getChildren().addAll(welcomeLabel, roleLabel, buyBooksButton, sellerDashboardButton);
        
        
        
        // Only shows admin panel
        
        
        if (currentUser.getAdminPriveleges()) {
            root.getChildren().add(adminPanelButton);
        }
        
        root.getChildren().add(logoutButton);
        
        return new Scene(root, 450, 500);
    }
}
