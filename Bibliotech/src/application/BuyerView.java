package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.Book;
import objects.User;

public class BuyerView {
    private User currentUser;
    private ObservableList<Book> allBooks;
    private ObservableList<Book> filteredBooks;
    
    public BuyerView(User user) {
        this.currentUser = user;
        OrderService.setCurrentBuyer(user.getAsuID());
    }
    
    public Scene getScene(Stage primaryStage, Runnable onBack) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        
        
        
        // Initialize data of books
        allBooks = BookService.getAllBooks();
        filteredBooks = FXCollections.observableArrayList(allBooks);
        
        
        
        
        // Search bar and filter
        
        
        HBox topBox = new HBox();
        topBox.setSpacing(10);
        topBox.setPadding(new Insets(10));
        
        Button backButton = new Button("← Back to Menu");
        backButton.setOnAction(e -> onBack.run());
        
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search books...");
        searchBar.setPrefWidth(300);
        
        ChoiceBox<String> genreFilter = new ChoiceBox<>();
        genreFilter.getItems().addAll(BookService.getAvailableGenres());
        genreFilter.setValue("All");
        
        Button searchButton = new Button("Search");
        
        topBox.getChildren().addAll(backButton, searchBar, genreFilter, searchButton);
        root.setTop(topBox);
        
        
        
        // Table for books
        
        TableView<Book> bookTable = new TableView<>();
        
        
        
        // columns
        
        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setPrefWidth(200);
        
        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        authorColumn.setPrefWidth(150);
        
        TableColumn<Book, String> genreColumn = new TableColumn<>("Genre");
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        genreColumn.setPrefWidth(120);
        
        TableColumn<Book, String> conditionColumn = new TableColumn<>("Condition");
        conditionColumn.setCellValueFactory(new PropertyValueFactory<>("condition"));
        conditionColumn.setPrefWidth(100);
        
        TableColumn<Book, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setPrefWidth(80);
        
        bookTable.getColumns().addAll(titleColumn, authorColumn, genreColumn, conditionColumn, priceColumn);
        
        
        
        
        // filtered books and table
        
        bookTable.setItems(filteredBooks);
        
        
        
        // Search function
        
        searchButton.setOnAction(e -> performSearch(searchBar.getText(), genreFilter.getValue()));
        
        searchBar.setOnAction(e -> performSearch(searchBar.getText(), genreFilter.getValue()));
        
        genreFilter.setOnAction(e -> performSearch(searchBar.getText(), genreFilter.getValue()));
        
        root.setCenter(bookTable);
        
        
        
        // Cart buttons
        
        
        VBox rightBox = new VBox();
        rightBox.setSpacing(10);
        rightBox.setPadding(new Insets(10));
        
        Label cartLabel = new Label("Shopping Cart");
        cartLabel.setStyle("-fx-font-weight: bold;");
        
        Label cartCountLabel = new Label("Items: " + OrderService.getCartItemCount());
        
        Label cartTotalLabel = new Label("Total: $" + String.format("%.2f", OrderService.getCartTotal()));
        
        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.setPrefWidth(120);
        
        Button viewCartButton = new Button("View Cart");
        viewCartButton.setPrefWidth(120);
        
        Button checkoutButton = new Button("Checkout");
        checkoutButton.setPrefWidth(120);
        checkoutButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        
        
        // Event handling
        
        addToCartButton.setOnAction(e -> {
            Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                boolean success = OrderService.addToCart(selectedBook);
                if (success) {
                    updateCartLabels(cartCountLabel, cartTotalLabel);
                    showAlert("Success", "Book added to cart: " + selectedBook.getTitle());
                } else {
                    showAlert("Error", "Could not add book to cart.");
                }
            } else {
                showAlert("Error", "Please select a book to add to cart.");
            }
        });
        
        viewCartButton.setOnAction(e -> {
            openCartView(primaryStage, cartCountLabel, cartTotalLabel);
        });
        
        checkoutButton.setOnAction(e -> {
            boolean success = OrderService.checkout();
            if (success) {
                updateCartLabels(cartCountLabel, cartTotalLabel);
                // Refresh the book list
                allBooks.setAll(BookService.getAllBooks());
                performSearch(searchBar.getText(), genreFilter.getValue());
                showAlert("Success", "Checkout completed successfully!");
            } else {
                showAlert("Error", "Checkout failed. Cart may be empty.");
            }
        });
        
        rightBox.getChildren().addAll(cartLabel, cartCountLabel, cartTotalLabel, 
                                     addToCartButton, viewCartButton, checkoutButton);
        root.setRight(rightBox);
        
        return new Scene(root, 900, 700);
    }
    
    private void performSearch(String searchTerm, String genre) {
        ObservableList<Book> searchResults = BookService.filterBooks(searchTerm, genre);
        filteredBooks.setAll(searchResults);
    }
    
    private void updateCartLabels(Label cartCountLabel, Label cartTotalLabel) {
        cartCountLabel.setText("Items: " + OrderService.getCartItemCount());
        cartTotalLabel.setText("Total: $" + String.format("%.2f", OrderService.getCartTotal()));
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void openCartView(Stage parentStage, Label cartCountLabel, Label cartTotalLabel) {
        Stage cartStage = new Stage();
        cartStage.initModality(Modality.WINDOW_MODAL);
        cartStage.initOwner(parentStage);
        cartStage.setTitle("Shopping Cart");
        
        VBox layout = new VBox();
        layout.setPadding(new Insets(15));
        layout.setSpacing(10);
        
        Label titleLabel = new Label("Shopping Cart");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        TableView<Book> cartTable = new TableView<>();
        
        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setPrefWidth(200);
        
        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        authorColumn.setPrefWidth(150);
        
        TableColumn<Book, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setPrefWidth(80);
        
        cartTable.getColumns().addAll(titleColumn, authorColumn, priceColumn);
        cartTable.setItems(OrderService.getCartContents());
        
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);
        
        Button removeButton = new Button("Remove Selected");
        Button clearCartButton = new Button("Clear Cart");
        Button closeButton = new Button("Close");
        
        removeButton.setOnAction(e -> {
            Book selected = cartTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                OrderService.removeFromCart(selected);
                updateCartLabels(cartCountLabel, cartTotalLabel);
            }
        });
        
        clearCartButton.setOnAction(e -> {
            OrderService.clearCart();
            updateCartLabels(cartCountLabel, cartTotalLabel);
        });
        
        closeButton.setOnAction(e -> cartStage.close());
        
        buttonBox.getChildren().addAll(removeButton, clearCartButton, closeButton);
        
        Label totalLabel = new Label("Total: $" + String.format("%.2f", OrderService.getCartTotal()));
        totalLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        layout.getChildren().addAll(titleLabel, cartTable, totalLabel, buttonBox);
        
        Scene cartScene = new Scene(layout, 500, 400);
        cartStage.setScene(cartScene);
        cartStage.show();
    }
}
