package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.Book;
import objects.User;
import objects.Transaction;



public class SellerView {
    private User currentUser;
    
    public SellerView(User user) {
        this.currentUser = user;
    }
    
    public Scene getScene(Stage primaryStage, Runnable onBack) {
        VBox root = new VBox();
        root.setPadding(new Insets(15));
        root.setSpacing(15);
        
        
        // go back 
        
        Button backButton = new Button("← Back to Menu");
        backButton.setOnAction(e -> onBack.run());
        
        
        
        
        //  seller revenue and stats
        
        
        ObservableList<Transaction> sales = OrderService.getSalesHistory(currentUser.getAsuID());
        double totalRevenue = sales.stream().mapToDouble(Transaction::getAmount).sum();
        
        
        
        // Revenue and rating 
        
        
        HBox statsBox = new HBox();
        statsBox.setSpacing(30);
        statsBox.setAlignment(Pos.CENTER);
        
        Label revenueLabel = new Label("Total Revenue: $" + String.format("%.2f", totalRevenue));
        revenueLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        Label salesCountLabel = new Label("Total Sales: " + sales.size());
        salesCountLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        statsBox.getChildren().addAll(revenueLabel, salesCountLabel);
        
        
        
        // List new book
        
        Button listBookButton = new Button("List a New Book");
        listBookButton.setPrefWidth(150);
        listBookButton.setStyle("-fx-font-size: 12px;");
        
        
        
        // listings table
        
        
        Label listingsLabel = new Label("Current Listings:");
        listingsLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        TableView<Book> listingsTable = new TableView<>();
        
        
        
        //  columns
        
        
        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setPrefWidth(200);
        
        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        authorColumn.setPrefWidth(150);
        
        TableColumn<Book, String> conditionColumn = new TableColumn<>("Condition");
        conditionColumn.setCellValueFactory(new PropertyValueFactory<>("condition"));
        conditionColumn.setPrefWidth(100);
        
        TableColumn<Book, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setPrefWidth(80);
        
        TableColumn<Book, Boolean> soldColumn = new TableColumn<>("Sold");
        soldColumn.setCellValueFactory(new PropertyValueFactory<>("sold"));
        soldColumn.setPrefWidth(60);
        
        listingsTable.getColumns().addAll(titleColumn, authorColumn, conditionColumn, priceColumn, soldColumn);
        
        
        
        
        // seller's actual books
        
        
        ObservableList<Book> sellerBooks = BookService.getBooksBySeller(currentUser.getAsuID());
        listingsTable.setItems(sellerBooks);
        
        
        
        // handling for listing new book
        
        
        
        listBookButton.setOnAction(e -> {
            openBookRegistrationForm(primaryStage, sellerBooks);
        });
        
        
        
        // Add all components
        
        root.getChildren().addAll(backButton, statsBox, listBookButton, listingsLabel, listingsTable);
        
        return new Scene(root, 700, 500);
    }
    
    private void openBookRegistrationForm(Stage parentStage, ObservableList<Book> sellerBooks) {
        Stage bookFormStage = new Stage();
        bookFormStage.initModality(Modality.WINDOW_MODAL);
        bookFormStage.initOwner(parentStage);
        bookFormStage.setTitle("List a New Book");
        
        VBox formLayout = new VBox();
        formLayout.setPadding(new Insets(20));
        formLayout.setSpacing(10);
        
        TextField titleField = new TextField();
        titleField.setPromptText("Book Title");
        
        TextField authorField = new TextField();
        authorField.setPromptText("Author");
        
        ComboBox<String> genreComboBox = new ComboBox<>();
        genreComboBox.getItems().addAll("Computer Science", "Math and Physics", "English Language", "Natural Science", "Other Genre");
        genreComboBox.setPromptText("Select Genre");
        genreComboBox.setPrefWidth(200);
        
        ComboBox<String> conditionComboBox = new ComboBox<>();
        conditionComboBox.getItems().addAll("New", "Excellent", "Good", "Fair");
        conditionComboBox.setPromptText("Select Condition");
        conditionComboBox.setPrefWidth(200);
        
        TextField priceField = new TextField();
        priceField.setPromptText("Price (e.g., 25.99)");
        
        Button submitButton = new Button("List Book");
        Button cancelButton = new Button("Cancel");
        
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(submitButton, cancelButton);
        
        submitButton.setOnAction(e -> {
            try {
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();
                String genre = genreComboBox.getValue();
                String condition = conditionComboBox.getValue();
                String priceText = priceField.getText().trim();
                
                // Validate inputs
                if (title.isEmpty() || author.isEmpty() || genre == null || condition == null || priceText.isEmpty()) {
                    showAlert("Error", "Please fill in all fields.");
                    return;
                }
                
                double price = Double.parseDouble(priceText);
                if (price <= 0) {
                    showAlert("Error", "Price must be greater than 0.");
                    return;
                }
                
                
                // list the new book
                
                
                
                boolean success = BookService.listNewBook(title, author, genre, condition, price, currentUser.getAsuID());
                
                if (success) {
                    
                	
                	
                	// Refresh
                	
                    sellerBooks.setAll(BookService.getBooksBySeller(currentUser.getAsuID()));
                    showAlert("Success", "Book listed successfully!");
                    bookFormStage.close();
                } else {
                    showAlert("Error", "Failed to list book. Please try again.");
                }
                
            } catch (NumberFormatException ex) {
                showAlert("Error", "Please enter a valid price.");
            }
        });
        
        cancelButton.setOnAction(e -> {
            bookFormStage.close();
        });
        
        formLayout.getChildren().addAll(
            new Label("Book Title:"), titleField,
            new Label("Author:"), authorField,
            new Label("Genre:"), genreComboBox,
            new Label("Condition:"), conditionComboBox,
            new Label("Price:"), priceField,
            buttonBox
        );
        
        Scene formScene = new Scene(formLayout, 350, 450);
        bookFormStage.setScene(formScene);
        bookFormStage.show();
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
