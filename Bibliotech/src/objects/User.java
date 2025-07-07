package objects;

import java.util.ArrayList;

public class User {
	private String asuID;
	private String password;
	private boolean adminPriveleges;
	
	private Buyer buyerDat;
	private Seller sellerDat;
	
	
	
	public User(String asuID, String password,boolean adminPriveleges) {
		this.asuID = asuID;
		this.password = password;
		this.adminPriveleges = adminPriveleges;
		
		buyerDat = new Buyer();
		sellerDat = new Seller();
	}
	public User(String asuID, String password, boolean adminPriveleges, String temp, int rev, ArrayList<Book> sales) {
		this.asuID = asuID;
		this.password = password;
		this.adminPriveleges = adminPriveleges;
		
		buyerDat = new Buyer(temp);
		sellerDat = new Seller(rev, sales);
	}
	
	public String getID() {
		return asuID;
	}
	
	public Buyer getBuyer() {
		return buyerDat;
	}
	
	public Seller getSeller() {
		return sellerDat;
	}
	
	public String toString() {
		String userData = asuID+",";
		userData += password+",";
		userData += adminPriveleges+",BUYER DATA,";
		userData += buyerDat.toString()+",SELLER DATA,";
		userData += sellerDat.toString();
		return userData;
	}
	
}
