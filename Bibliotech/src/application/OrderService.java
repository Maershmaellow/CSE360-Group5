package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Book;
import objects.Transaction;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private static ObservableList<Book> cart = FXCollections.observableArrayList();
    private static int currentBuyerId = 0;
    
   
    public static void setCurrentBuyer(int buyerId) {
        currentBuyerId = buyerId;
    }
    
   
    public static boolean addToCart(Book book) {
        if (!DataStore.isBuyingEnabled) {
            System.out.println("Buying is currently disabled");
            return false;
        }
        
        if (book == null || book.getSold()) {
            System.out.println("Book is not available for purchase");
            return false;
        }
        
        if (book.getSellerId() == currentBuyerId) {
            System.out.println("You cannot buy your own book");
            return false;
        }
        
        if (cart.contains(book)) {
            System.out.println("Book is already in cart");
            return false;
        }
        
        cart.add(book);
        System.out.println("Book added to cart: " + book.getTitle());
        return true;
    }
    
   
  
    public static boolean removeFromCart(Book book) {
        boolean removed = cart.remove(book);
        if (removed) {
            System.out.println("Book removed from cart: " + book.getTitle());
        }
        return removed;
    }
    
   
   
    public static ObservableList<Book> getCartContents() {
        return cart;
    }
    
 
   
    public static double getCartTotal() {
        double total = 0.0;
        for (Book book : cart) {
            total += book.getPrice();
        }
        return total;
    }
    
  
    public static int getCartItemCount() {
        return cart.size();
    }
    
  
    public static void clearCart() {
        cart.clear();
        System.out.println("Cart cleared");
    }
    
  
    public static boolean checkout() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty");
            return false;
        }
        
        if (currentBuyerId == 0) {
            System.out.println("No buyer set for checkout");
            return false;
        }
        
        List<Transaction> newTransactions = new ArrayList<>();
        List<Book> booksToRemove = new ArrayList<>();
        
        for (Book book : cart) {
            String transactionId = DataStore.generateTransactionId();
            Transaction transaction = new Transaction(
                transactionId,
                currentBuyerId,
                book.getSellerId(),
                book.getTitle(),
                book.getPrice(),
                LocalDate.now(),
                "Completed"
            );
            
            newTransactions.add(transaction);
            booksToRemove.add(book);
        }
        
        DataStore.transactions.addAll(newTransactions);
        
        for (Book book : booksToRemove) {
            BookService.markBookAsSold(book);
        }
        
        double totalAmount = getCartTotal();
        int itemCount = getCartItemCount();
        
        // Clear the cart
        clearCart();
        
        System.out.println("Checkout completed successfully! " + itemCount + " items purchased for $" + String.format("%.2f", totalAmount));
        return true;
    }
    
 
    public static boolean isInCart(Book book) {
        return cart.contains(book);
    }
    

    public static ObservableList<Transaction> getPurchaseHistory(int buyerId) {
        ObservableList<Transaction> purchases = FXCollections.observableArrayList();
        
        for (Transaction transaction : DataStore.transactions) {
            if (transaction.getBuyerId() == buyerId) {
                purchases.add(transaction);
            }
        }
        
        return purchases;
    }
    

    public static ObservableList<Transaction> getSalesHistory(int sellerId) {
        ObservableList<Transaction> sales = FXCollections.observableArrayList();
        
        for (Transaction transaction : DataStore.transactions) {
            if (transaction.getSellerId() == sellerId) {
                sales.add(transaction);
            }
        }
        
        return sales;
    }
}
