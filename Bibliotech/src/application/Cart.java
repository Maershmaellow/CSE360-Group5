package application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import objects.Book;
import objects.DataBase;
import objects.Transaction;

public class Cart {
	private static ArrayList<Book> cart = new ArrayList<>();
    private static String currBuyer = "";
    
    public static void setCurrentBuyer(String buyerID) {
        currBuyer= buyerID;
    }
    
    // Admin can disable purchasing permissions
    public static boolean addToCart(Book book) {
        if (!DataBase.buyingEnabled) {
            System.out.println("Buying is currently disabled");
            return false;
        }
        
        if (book == null || book.getSold()) {
            System.out.println("Book is not available for purchase");
            return false;
        }
        
        if (book.getName() == currBuyer) {
            System.out.println("You cannot buy your own book");
            return false;
        }
        
        if (cart.contains(book)) {
            System.out.println("Book is already in cart");
            return false;
        }
        
        cart.add(book);
        System.out.println("Book added to cart: " + book.getName());
        return true;
    }
    public static boolean checkout() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty");
            return false;
        } else if (currBuyer.equals("")) {
            System.out.println("No buyer set for checkout");
            return false;
        }
        
        ArrayList<Transaction> newTransactions = new ArrayList<>();
        ArrayList<Book> booksToRemove = new ArrayList<>();
        
        for (Book book : cart) {
            String transactionID = DataBase.generateTransactionId();
            Transaction transaction = new Transaction(transactionID, currBuyer, book.getName(), book.getName(), book.getPrice(), "Completed", LocalDate.now());
            
            newTransactions.add(transaction);
            booksToRemove.add(book);
        }
        
        DataBase.transaction.addAll(newTransactions);
        
        for (Book book : booksToRemove) {
            // markBookAsSold(book) somehow
        }
        
        double totalAmount = getTotal();
        cart.clear();
        
        System.out.println("Checkout completed successfully! " + cart.size() + " items purchased for $" + String.format("%.2f", totalAmount));
        return true;
    }
    
    public static boolean removeFromCart(Book book) {
        if (cart.remove(book)) {
            System.out.println("Book removed from cart: " + book.getName());
            return true;
        }
        return false;
    }
    
    public static ArrayList<Book> getCart() {
    	return cart;
    }
    
    public static double getTotal() {
    	double totalPrice = 0.0;
    	
    	for(Book book : cart) {
    		totalPrice+=book.getPrice();
    	}
    	return totalPrice;  
    }
}
