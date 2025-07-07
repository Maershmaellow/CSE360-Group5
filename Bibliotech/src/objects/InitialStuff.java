package objects;

import java.time.LocalDate;

public class InitialStuff {
	private static void initialUsers() {
		DataBase.users.add(new User("Bob", "password123", false));
        DataBase.users.add(new User("Phil", "mypass456", false));
        DataBase.users.add(new User("Johnathan", "student789", false));
	}
	
	private static void initialBooks() {
		// String title, String author, String genre, String condition, double price, boolean sold, String seller
		DataBase.books.add(new Book("Database Stuff", "Tommy Hilfiger", "Computer Science", "Good", 48.50, false,"Bob"));
        DataBase.books.add(new Book("Web Dev Fundamentals", "Mark Johnsohn", "Computer Science", "Fair", 42.00, false, "Phil"));
		
		DataBase.books.add(new Book("Campbell Biology", "Jane Reece", "Natural Science", "Excellent", 275.00, false, "Johnathan"));
        DataBase.books.add(new Book("Chemistry: The Central Science", "Theodore Brown", "Natural Science", "Good", 198.99, false, "Bob"));
	}
	
	private static void initialTransactions() {
		//String transactionId, String buyerId, String sellerId, String bookTitle, double cost, String status, LocalDate Date
		DataBase.transaction.add(new Transaction("TXN003", "Bob", "Phil", "Statistics and Analysis", 78.25, "Completed", LocalDate.of(2024, 12, 8)));
        DataBase.transaction.add(new Transaction("TXN004", "Johnathan", "Phil", "Modern Physics", 165.75, "Pending", LocalDate.of(2024, 12, 5)));
	}
}