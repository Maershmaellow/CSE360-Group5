package objects;

import java.time.LocalDate;

public class Transaction {
    private String transactionId;
    private int buyerId;
    private int sellerId;
    private String bookTitle;
    private double amount;
    private LocalDate date;
    private String status;
    
    public Transaction(String transactionId, int buyerId, int sellerId, String bookTitle, double amount, LocalDate date, String status) {
        this.transactionId = transactionId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.bookTitle = bookTitle;
        this.amount = amount;
        this.date = date;
        this.status = status;
    }
    
    public String getTransactionId() {
        return transactionId;
    }
    
    public int getBuyerId() {
        return buyerId;
    }
    
    public int getSellerId() {
        return sellerId;
    }
    
    public String getBookTitle() {
        return bookTitle;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public String getStatus() {
        return status;
    }
    
    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", buyerId=" + buyerId +
                ", sellerId=" + sellerId +
                ", bookTitle='" + bookTitle + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", status='" + status + '\'' +
                '}';
    }
}
