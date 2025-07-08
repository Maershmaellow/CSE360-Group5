package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import objects.Book;

public class FileManagement {
	public static File makeFile(Book book) {
		String filePath = "Books/"+book.getGenre()+"/"+book.getName();
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
	
	public static String readfromFile(File file) {
		String readData = "";
		try {
			Scanner read = new Scanner(file);
			while(read.hasNextLine()) {
				readData += read.nextLine();
			}
			read.close();
		} catch (FileNotFoundException e) {
			System.out.println("could not find given file");
		}
		return readData;
	}
}
