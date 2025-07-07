package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import objects.Book;
import objects.User;

public class FileManagement {
	public static File makeFile(Book book) {
		Path filePath = Path.get("Books", book.getGenre(), book.getName(),".txt");
		File newFile = new File(filePath);
		try {
			FileWriter writer = new FileWriter(filePath);
			writer.write(book.toString());
			writer.close();
		}catch (IOException e) {
			System.out.println("Could not open writer for file->"+filePath);
		}
		
		return newFile;
	}
	public static File makeFile(User account) {
		String filePath = "Accounts/"+account.getID()+".txt";
		File newFile = new File(filePath);
		try {
			FileWriter writer = new FileWriter(filePath);
			writer.write(account.toString());
			writer.close();
		}catch (IOException e) {
			System.out.println("Could not open writer for file->"+filePath);
		}
		
		return newFile;
	}
	
	
	public static Book bookFromString(String filecontents) {
		String[] delimited = filecontents.split(",");
		
		Book newBook = new Book(delimited[0], delimited[1], delimited[2], delimited[3], Double.parseDouble(delimited[4]), Boolean.parseBoolean(delimited[5]), delimited[6]);
		return newBook;
	}
	public static User userFromString(String filecontents) {
		String[] delimited = filecontents.split(",");
		// String asuID, String password, boolean adminPriveleges, String temp, int rev, ArrayList<Book> sales
		String bookReverse = "";
		ArrayList<Book> bookArr = new ArrayList<>();
		for(int i = 7; i < delimited.length; i++) {
			bookReverse += delimited[i];
			if(bookReverse.split(",").length == 7) {
				bookArr.add(bookFromString(bookReverse));
				bookReverse = "";
			}else {
				bookReverse+=",";
			}
		}
		User newBook = new User(delimited[0], delimited[1], Boolean.parseBoolean(delimited[2]), delimited[4], Integer.parseInt(delimited[6]), bookArr);
		return newBook;
	}
	
	public static String readfromFile(File file) {
		String readData = "";
		try {
			Scanner read = new Scanner(file);
			if(read.hasNextLine()) {
				readData = read.nextLine()+"\n";
			}
			read.close();
		} catch (FileNotFoundException e) {
			System.out.println("could not find given file");
		}
		return readData;
	}
}
