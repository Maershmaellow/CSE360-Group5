package objects;

public class Book {
	private String title;
	private String author;
	private String genre;
	private String condition;
	private double price;
	private boolean sold;
	private Seller seller;
	
	public Book(String title, String author, String genre, String condition, double price, Seller seller) {
		this.title = title;
		this.author = author;
		this.condition = condition;
		this.sold = false;
		this.seller = seller;
		
		if(price < 0) {						// Note: if the user does not manually enter a price, price parameter will be -1
			this.price = generatePrice();
		} else {
			this.price = price;
		}
	}
	
	
	
	public double generatePrice() {
		// this function generates a price 
		return 0;
	}
	
	public double getPrice() {
		return price;
	}
	
	public String getName() {
		return title;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public boolean getSold() {
		return sold;
	}
	
	public String getCondition() {
		return condition;
	}
	
	public Seller getSeller() {
		return seller;
	}
	
	
	
	public String toString() {
		String bookData = "-- Book Data --"+"\n";
		bookData += "title: "+title+"\n";
		bookData += "author: "+author+"\n";
		bookData += "condition: "+condition+"\n";
		bookData += "price: "+String.valueOf(price)+"\n";
		bookData += "sold: "+String.valueOf(sold)+"\n";
		bookData += "seller: "+seller.toString()+"\n";
		return bookData;
	}
}
