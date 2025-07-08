package objects;

public class User {
	private int asuID;
	private String password;
	private boolean adminPriveleges;
	private boolean isActive;
	
	private Buyer buyerDat;
	private Seller sellerDat;
	
	public User(int asuID, String password,boolean adminPriveleges) {
		this.asuID = asuID;
		this.password = password;
		this.adminPriveleges = adminPriveleges;
		this.isActive = true; 

// active
		
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
	
	public int getAsuID() {
		return asuID;
	}
	
	public String getPassword() {
		return password;
	}
	
	public boolean getAdminPriveleges() {
		return adminPriveleges;
	}
	
	public String getRole() {
		return adminPriveleges ? "Admin" : "User";
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public Buyer getBuyerDat() {
		return buyerDat;
	}
	
	public Seller getSellerDat() {
		return sellerDat;
	}
}
