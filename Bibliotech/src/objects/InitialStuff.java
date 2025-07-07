package objects;

public class InitialStuff {
	private static void initialUsers() {
		
	}
	
	private static void initialBooks() {
		books.add(new Book("Campbell Biology", "Jane Reece", "Natural Science", "Excellent", 275.00, 1002));
        books.add(new Book("Chemistry: The Central Science", "Theodore Brown", "Natural Science", "Good", 198.99, 1004));
	}
	
	private static void initialTransactions() {
		transactions.add(new Transaction("TXN003", 1004, 1002, "Statistics for Engineers", 78.25, LocalDate.of(2024, 12, 8), "Completed"));
        transactions.add(new Transaction("TXN004", 1002, 1003, "Modern Physics", 165.75, LocalDate.of(2024, 12, 5), "Pending"));
	}
}