package objects;

public class Buyer {
	// Implement in case we want user-based settings
	private String temp;
	
	public Buyer(String temp) {
		this.temp = temp;
	}
	public Buyer() {
		temp = "placeholder for if user-settings are implemented";
	}
	
	public String toString() {
		String buyerDat = "Buyer Data"+"\n";
		buyerDat += temp;
		return buyerDat;
	}
}
