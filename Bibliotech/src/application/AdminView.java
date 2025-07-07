package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import objects.User;
import objects.Transaction;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

public class AdminView {
    
    public Scene getScene(Stage primaryStage, Runnable onBack) {
        VBox mainContainer = new VBox();
        mainContainer.setPadding(new Insets(10));
        mainContainer.setSpacing(10);
        
        
        
        // Back button
        
        
        Button backButton = new Button("← Back to Menu");
        backButton.setOnAction(e -> onBack.run());
        
        TabPane root = new TabPane();
        
        
        
        // User Management Tab
        
        
        Tab userManagementTab = new Tab("User Management");
        userManagementTab.setClosable(false);
        
        VBox userTabContent = new VBox();
        userTabContent.setPadding(new Insets(15));
        userTabContent.setSpacing(10);
        
        
        
        // User table
        
        
        TableView<User> userTable = new TableView<>();
        
        TableColumn<User, Integer> userIdColumn = new TableColumn<>("ASU ID");
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("asuID"));
        userIdColumn.setPrefWidth(100);
        
        TableColumn<User, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        roleColumn.setPrefWidth(100);
        
        TableColumn<User, Boolean> adminColumn = new TableColumn<>("Admin");
        adminColumn.setCellValueFactory(new PropertyValueFactory<>("adminPriveleges"));
        adminColumn.setPrefWidth(80);
        
        TableColumn<User, Boolean> activeColumn = new TableColumn<>("Active");
        activeColumn.setCellValueFactory(new PropertyValueFactory<>("active"));
        activeColumn.setPrefWidth(80);
        
        userTable.getColumns().addAll(userIdColumn, roleColumn, adminColumn, activeColumn);
        
        
        
        // to get user data
        
        
        ObservableList<User> users = AdminService.getUsers();
        userTable.setItems(users);
        
        
        
        
        // management buttons
        
        
        
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);
        
        Button removeUserButton = new Button("Remove User");
        Button toggleStatusButton = new Button("Toggle Status");
        Button refreshButton = new Button("Refresh");
        
        removeUserButton.setOnAction(e -> {
            User selectedUser = userTable.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                boolean success = AdminService.removeUser(selectedUser);
                if (success) {
                    showAlert("Success", "User removed: " + selectedUser.getAsuID());
                } else {
                    showAlert("Error", "Could not remove user. Admin users cannot be removed.");
                }
            } else {
                showAlert("Error", "No user selected for removal");
            }
        });
        
        toggleStatusButton.setOnAction(e -> {
            User selectedUser = userTable.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                boolean success = AdminService.toggleUserStatus(selectedUser);
                if (success) {
                    String status = selectedUser.isActive() ? "activated" : "deactivated";
                    showAlert("Success", "User " + selectedUser.getAsuID() + " has been " + status);
                    userTable.refresh(); // Refresh table to show updated status
                } else {
                    showAlert("Error", "Could not change user status. Admin users cannot be deactivated.");
                }
            } else {
                showAlert("Error", "No user selected");
            }
        });
        
        refreshButton.setOnAction(e -> {
            userTable.setItems(AdminService.getUsers());
            userTable.refresh();
        });
        
        buttonBox.getChildren().addAll(removeUserButton, toggleStatusButton, refreshButton);
        userTabContent.getChildren().addAll(userTable, buttonBox);
        userManagementTab.setContent(userTabContent);
        
        
        
        
        
        // System Transactionsz
        
        
        Tab transactionsTab = new Tab("System Transactions");
        transactionsTab.setClosable(false);
        
        VBox transactionTabContent = new VBox();
        transactionTabContent.setPadding(new Insets(15));
        transactionTabContent.setSpacing(15);
        
        // Bar chart for transaction analytics with real data
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> transactionChart = new BarChart<>(xAxis, yAxis);
        transactionChart.setTitle("Recent Transaction Volume");
        transactionChart.setPrefHeight(250);
        
        // Create chart data from actual transactions
        ObservableList<Transaction> allTransactions = AdminService.getTransactions();
        Map<String, Long> transactionsByMonth = allTransactions.stream()
            .collect(Collectors.groupingBy(
                t -> t.getDate().getMonth().toString(),
                Collectors.counting()));
        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Transactions");
        
        
        
        //  chart data added
        
        
        transactionsByMonth.forEach((month, count) -> {
            series.getData().add(new XYChart.Data<>(month, count));
        });
        
        
        
        
        // add sample data only if no real data exisits 
        
        
        if (series.getData().isEmpty()) {
            series.getData().add(new XYChart.Data<>("Recent", allTransactions.size()));
        }
        
        transactionChart.getData().add(series);
        
        
        
        
        // Transaction
        
        
        TableView<Transaction> transactionTable = new TableView<>();
        
        TableColumn<Transaction, String> transactionIdColumn = new TableColumn<>("Transaction ID");
        transactionIdColumn.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        transactionIdColumn.setPrefWidth(120);
        
        TableColumn<Transaction, Integer> buyerIdColumn = new TableColumn<>("Buyer ID");
        buyerIdColumn.setCellValueFactory(new PropertyValueFactory<>("buyerId"));
        buyerIdColumn.setPrefWidth(80);
        
        TableColumn<Transaction, Integer> sellerIdColumn = new TableColumn<>("Seller ID");
        sellerIdColumn.setCellValueFactory(new PropertyValueFactory<>("sellerId"));
        sellerIdColumn.setPrefWidth(80);
        
        TableColumn<Transaction, String> bookTitleColumn = new TableColumn<>("Book Title");
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        bookTitleColumn.setPrefWidth(150);
        
        TableColumn<Transaction, Double> amountColumn = new TableColumn<>("Amount");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amountColumn.setPrefWidth(80);
        
        TableColumn<Transaction, LocalDate> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setPrefWidth(100);
        
        TableColumn<Transaction, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setPrefWidth(80);
        
        transactionTable.getColumns().addAll(transactionIdColumn, buyerIdColumn, sellerIdColumn, 
                                           bookTitleColumn, amountColumn, dateColumn, statusColumn);
        
        
        
        // get actual transaction data
        
        ObservableList<Transaction> transactions = AdminService.getTransactions();
        transactionTable.setItems(transactions);
        
        transactionTabContent.getChildren().addAll(transactionChart, transactionTable);
        transactionsTab.setContent(transactionTabContent);
        
        
        
        
        // system config
        
        Tab configTab = new Tab("System Configuration");
        configTab.setClosable(false);
        
        VBox configTabContent = new VBox();
        configTabContent.setPadding(new Insets(15));
        configTabContent.setSpacing(15);
        
        Label configTitle = new Label("System Feature Configuration");
        configTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        CheckBox disableSignupsCheckBox = new CheckBox("Disable New User Sign-ups");
        disableSignupsCheckBox.setSelected(!AdminService.getSystemFeatureStatus("signup"));
        
        CheckBox disableListingsCheckBox = new CheckBox("Disable New Book Listings");
        disableListingsCheckBox.setSelected(!AdminService.getSystemFeatureStatus("selling"));
        
        CheckBox disableBuyingCheckBox = new CheckBox("Disable Book Purchasing");
        disableBuyingCheckBox.setSelected(!AdminService.getSystemFeatureStatus("buying"));
        
        Button saveConfigButton = new Button("Save Configuration");
        saveConfigButton.setPrefWidth(150);
        
        Button showStatsButton = new Button("Show System Statistics");
        showStatsButton.setPrefWidth(150);
        
        
        
        // Event handling for config
        
        saveConfigButton.setOnAction(e -> {
            AdminService.toggleSystemFeature("signup", !disableSignupsCheckBox.isSelected());
            AdminService.toggleSystemFeature("selling", !disableListingsCheckBox.isSelected());
            AdminService.toggleSystemFeature("buying", !disableBuyingCheckBox.isSelected());
            
            showAlert("Success", "Configuration saved successfully!");
        });
        
        showStatsButton.setOnAction(e -> {
            String stats = AdminService.getSystemStats();
            showAlert("System Statistics", stats);
        });
        
        configTabContent.getChildren().addAll(configTitle, disableSignupsCheckBox, 
                                            disableListingsCheckBox, disableBuyingCheckBox, 
                                            saveConfigButton, showStatsButton);
        configTab.setContent(configTabContent);
        
        
        
        // Add all tabs
        
        root.getTabs().addAll(userManagementTab, transactionsTab, configTab);
        
        
        
        // Add back button and tabppane 
        
        mainContainer.getChildren().addAll(backButton, root);
        
        return new Scene(mainContainer, 850, 700);
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
