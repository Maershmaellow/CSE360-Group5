package objects;

import java.time.LocalDate;

public class Transaction {
	private String transactionID;
    private String buyerID;
    private String sellerID;
    
    private String bookTitle;
    private double cost;
    private String status;
    private LocalDate date;
    
    
    public Transaction(String transactionId, String buyerId, String sellerId, String bookTitle, double cost, String status, LocalDate Date) {
        this.transactionID = transactionId;
        this.buyerID = buyerId;
        this.sellerID = sellerId;
        
        this.bookTitle = bookTitle;
        this.cost = cost;
        this.status = status;
        this.date = date;
    }
    
    public String getTransactionId() {
        return transactionID;
    }
    public String getBuyerId() {
        return buyerID;
    }
    public String getSellerId() {
        return sellerID;
    }
    
    
    
    public String getBookTitle() {
        return bookTitle;
    }
    
    public double getPrice() {
        return cost;
    }
    
    public String getStatus() {
        return status;
    }
    
    public LocalDate getDate() {
    	return date;
    }
    
    public String toString() {
        String transactionData = transactionID+",";
        transactionData = buyerID+",";
        transactionData += sellerID+",";
        transactionData += bookTitle+",";
        transactionData += cost+",";
        transactionData += status;
        
        return transactionData;
    }
    
}
