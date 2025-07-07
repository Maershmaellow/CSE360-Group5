package objects;

import java.util.ArrayList;

public class User {
	private String asuID;
	private String password;
	private boolean adminPriveleges;
	private boolean isActive;
	
	private Buyer buyerDat;
	private Seller sellerDat;
	
	
	
	public User(String asuID, String password,boolean adminPriveleges) {
		this.asuID = asuID;
		this.password = password;
		this.adminPriveleges = adminPriveleges;
		
		buyerDat = new Buyer();
		sellerDat = new Seller();
	}

	public String getAsuID() {
		return asuID;
	}
	
	public String getPassword() {
		return password;
	}
	
	public boolean isAdmin() {
		return adminPriveleges;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public Buyer getBuyerDat() {
		return buyerDat;
	}
	
	public Seller getSellerDat() {
		return sellerDat;
	}
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
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
