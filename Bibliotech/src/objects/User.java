package objects;

public class User {
	private int asuID;
	private String password;
	private boolean adminPriveleges;
	
	private Buyer buyerDat;
	private Seller sellerDat;
	
	public User(int asuID, String password,boolean adminPriveleges) {
		this.asuID = asuID;
		this.password = password;
		this.adminPriveleges = adminPriveleges;
		
		buyerDat = new Buyer();
		sellerDat = new Seller();
	}
	
	public String toString() {
		String userData = "-- Account Info --"+"\n";
		userData += "asuID: "+asuID+"\n";
		userData += "password: "+password+"\n";
		userData += "admin: "+adminPriveleges+"\n";
		userData += buyerDat.toString()+"\n";
		userData += sellerDat.toString()+"\n";
		return userData;
	}
	
}
