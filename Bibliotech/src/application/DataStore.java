package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Book;
import objects.User;
import objects.Transaction;
import java.time.LocalDate;

public class DataStore {
    // application state
    public static ObservableList<User> users = FXCollections.observableArrayList();
    public static ObservableList<Book> books = FXCollections.observableArrayList();
    public static ObservableList<Transaction> transactions = FXCollections.observableArrayList();
    
    //  settings
    public static boolean isSignupEnabled = true;
    public static boolean isBuyingEnabled = true;
    public static boolean isSellingEnabled = true;
    
    // Static initialization 
    static {
        initializeUsers();
        initializeBooks();
        initializeTransactions();
    }
    
    private static void initializeUsers() {
        // Admin user
        users.add(new User(1001, "admin123", true));
        
        // Regular users
        users.add(new User(1002, "password123", false));
        users.add(new User(1003, "mypass456", false));
        users.add(new User(1004, "student789", false));
    }
    
    private static void initializeBooks() {
        // Computer Science 
        books.add(new Book("Advanced Java Programming", "Herbert Schildt", "Computer Science", "Excellent", 65.99, 1002));
        books.add(new Book("Database Design Fundamentals", "Thomas Connolly", "Computer Science", "Good", 48.50, 1003));
        books.add(new Book("Web Development with React", "Mark Tielens", "Computer Science", "Fair", 42.00, 1002));
        books.add(new Book("Data Structures and Algorithms", "Robert Sedgewick", "Computer Science", "New", 89.99, 1004));
        
        // Math and Physics 
        books.add(new Book("Calculus: Early Transcendentals", "James Stewart", "Math and Physics", "Good", 125.00, 1003));
        books.add(new Book("Linear Algebra and Its Applications", "David Lay", "Math and Physics", "Excellent", 95.75, 1002));
        
        // English Language 
        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "English Language", "Fair", 12.99, 1004));
        books.add(new Book("To Kill a Mockingbird", "Harper Lee", "English Language", "Good", 15.50, 1003));
        
        // Natural Science 
        books.add(new Book("Campbell Biology", "Jane Reece", "Natural Science", "Excellent", 275.00, 1002));
        books.add(new Book("Chemistry: The Central Science", "Theodore Brown", "Natural Science", "Good", 198.99, 1004));
    }
    
    private static void initializeTransactions() {
        transactions.add(new Transaction("TXN001", 1002, 1003, "Introduction to Psychology", 85.50, LocalDate.of(2024, 12, 15), "Completed"));
        transactions.add(new Transaction("TXN002", 1003, 1004, "Organic Chemistry", 120.00, LocalDate.of(2024, 12, 10), "Completed"));
        transactions.add(new Transaction("TXN003", 1004, 1002, "Statistics for Engineers", 78.25, LocalDate.of(2024, 12, 8), "Completed"));
        transactions.add(new Transaction("TXN004", 1002, 1003, "Modern Physics", 165.75, LocalDate.of(2024, 12, 5), "Pending"));
    }
    
    // 
    public static String generateTransactionId() {
        return "TXN" + String.format("%03d", transactions.size() + 1);
    }
    
    public static int getNextUserId() {
        return users.stream().mapToInt(User::getAsuID).max().orElse(1000) + 1;
    }
}
