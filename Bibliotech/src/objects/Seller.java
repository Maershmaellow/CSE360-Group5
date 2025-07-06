package objects;

import java.util.ArrayList;

public class Seller {
	private int total_revenue;
	private ArrayList<Book> sellList;
	
	public Seller(int rev, ArrayList<Book> bookList) {
		this.total_revenue = rev;
		
		for(Book thisBook : bookList) {
			sellList.add(thisBook);
		}
	}
	public Seller() {
		total_revenue = 0;
	}
	
	
	
	public boolean sold(Book book) {
		return sellList.add(book);
	}
	
	public void findTotalRev() {
		for(Book thisBook : sellList) {
			total_revenue += thisBook.getPrice();
		}
	}
	
	
	
	public String toString() {
		String sellerDat = "-- Seller Data --"+"\n";
		sellerDat += "revenue: "+total_revenue+"\n";
		return sellerDat;
	}

}
