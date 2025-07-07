package objects;

public class Book {
	private String title;
	private String author;
	private String genre;
	private String condition;
	private double price;
	private boolean sold;
	private int sellerId;
	
	public Book(String title, String author, String genre, String condition, double price) {
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.condition = condition;
		this.sold = false;
		this.sellerId = 0; 
//seller ID
		
		
		if(price < 0) {						
			this.price = generatePrice();
		} else {
			this.price = price;
		}
	}
	
	public Book(String title, String author, String genre, String condition, double price, int sellerId) {
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.condition = condition;
		this.sold = false;
		this.sellerId = sellerId;
		
		
		if(price < 0) {		// price parameter will be -1 if no entry
			this.price = generatePrice();
		} else {
			this.price = price;
		}
	}
	
	
	
	public double generatePrice() {
	
		return 0;
	}
	
	public double getPrice() {
		return price;
	}
	
	public String getName() {
		return title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public String getCondition() {
		return condition;
	}
	
	public boolean getSold() {
		return sold;
	}
	
	public int getSellerId() {
		return sellerId;
	}
	
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	
	public void setSold(boolean sold) {
		this.sold = sold;
	}
	
	public String toString() {
		String bookData = "-- Book Data --"+"\n";
		bookData += "title: "+title+"\n";
		bookData += "author: "+author+"\n";
		bookData += "condition: "+condition+"\n";
		bookData += "price: "+String.valueOf(price)+"\n";
		return bookData;
	}
}
