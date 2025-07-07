package objects;

import java.util.ArrayList;

public class DataBase {
	public static ArrayList<User> users = new ArrayList<>();
	public static ArrayList<Book> books = new ArrayList<>();
	public static ArrayList<Transaction> transaction = new ArrayList<>();
	
	// Settings for the Admin to change system wide
	public static boolean signupEnabled = true;
    public static boolean buyingEnabled = true;
    public static boolean sellingEnabled = true;
    
    public static String generateTransactionId() {
        return "TXN" + String.format("%03d", transaction.size() + 1);
    }
    
    public static ArrayList<Transaction> getPurchaseHistory(String buyerID) {
        ArrayList<Transaction> purchases = new ArrayList<>();
        
        for (Transaction transaction : transaction) {
            if (transaction.getBuyerId().equals(buyerID)) {
                purchases.add(transaction);
            }
        }
        return purchases;
    }
    
    public static ArrayList<Transaction> getSalesHistory(int sellerID) {
        ArrayList<Transaction> sales = new ArrayList<>();
        
        for (Transaction transaction : transaction) {
            if (transaction.getSellerId().equals(sellerID)) {
                sales.add(transaction);
            }
        }
        return sales;
    }
}
