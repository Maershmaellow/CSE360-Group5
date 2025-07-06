package objects;

public class Book {
	private String title;
	private String author;
	private String genre;
	private String condition;
	private double price;
	private boolean sold;
	
	public Book(String title, String author, String genre, String condition, double price) {
		this.title = title;
		this.author = author;
		this.condition = condition;
		this.sold = false;
		
		
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
	
	
	
	public String toString() {
		String bookData = "-- Book Data --"+"\n";
		bookData += "title: "+title+"\n";
		bookData += "author: "+author+"\n";
		bookData += "condition: "+condition+"\n";
		bookData += "price: "+String.valueOf(price)+"\n";
		return bookData;
	}
}
