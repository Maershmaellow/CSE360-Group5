package application;

import objects.Book;
import application.FileManagement;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BuyerTasks {
	public static ArrayList<Book> searchBook(String name, String conditionFilter, File genreFilter) {
		ArrayList<Book> searchList = new ArrayList<>();
		
		File[] files;
		
		if (genreFilter != null) {
			files = genreFilter.listFiles();
		}
		
		if (files != null) {
			for (File file : files) {
				String book = FileManagement.readfromFile(file);
				String sold = book.substring( book.indexOf("Sold: "))
					
				if (!book.getSold() && book.getName().toLowerCase().contains(name.toLowerCase() && ) {
					
				}
			}
		}
		
		
		
		
		
		return searchList; 
	}
	
	public static void addBook2Cart(ArrayList<Book> book) {
		
	}
	
	public static void makePurchase() {
		
	}
	
	public static void rateSeller() {
		
	}
}
