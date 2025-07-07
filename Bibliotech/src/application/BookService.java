package application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Book;
//CODE//
public class BookService {
    
//return books that didn't sale
    public static ObservableList<Book> getAllBooks(){
        ObservableList<Book> availableBooks = FXCollections.observableArrayList();
        for(Book book : DataStore.books){
            if(!book.getSold()){
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }
//return books, both sold and unsold
    public static ObservableList<Book> getAllBooksIncludingSold(){
        return DataStore.books;
    }
    
  //add new book to the store.
    public static boolean listNewBook(String title, String author, String genre, String condition, double price, int sellerId){
        if(!DataStore.isSellingEnabled){
            System.out.println("Selling is currently disabled");
            return false;
        }
        if(title == null || title.trim().isEmpty() ||
            author == null || author.trim().isEmpty() ||
            genre == null || genre.trim().isEmpty() ||
            condition == null || condition.trim().isEmpty() ||
            price < 0) {
            System.out.println("Invalid book information provided");
            return false;
        }
        Book newBook = new Book(title.trim(), author.trim(), genre.trim(), condition.trim(), price, sellerId);
        DataStore.books.add(newBook);
        System.out.println("Book listed successfully: " + title + " by " + author);
        return true;
    }
//filtering based on genre and name
    public static ObservableList<Book> filterBooks(String searchTerm, String genre){
        ObservableList<Book> filteredBooks = FXCollections.observableArrayList()
        String lowerSearchTerm = searchTerm != null ? searchTerm.toLowerCase().trim() : "";
        String lowerGenre = genre != null ? genre.toLowerCase().trim() : "";
        for(Book book : DataStore.books){
            if(book.getSold()){
                continue; //skip if book sold
            }
            boolean matchesSearch = lowerSearchTerm.isEmpty() || 
                                  book.getTitle().toLowerCase().contains(lowerSearchTerm) ||
                                  book.getAuthor().toLowerCase().contains(lowerSearchTerm);
            boolean matchesGenre = lowerGenre.isEmpty() || 
                                 lowerGenre.equals("all") ||
                                 book.getGenre().toLowerCase().equals(lowerGenre);
            if(matchesSearch && matchesGenre){
                filteredBooks.add(book);
            }
        }
        return filteredBooks;
    }
    //find books by seller id
    public static ObservableList<Book> getBooksBySeller(int sellerId) {
        ObservableList<Book> sellerBooks = FXCollections.observableArrayList();
        for(Book book : DataStore.books){
            if (book.getSellerId() == sellerId){
                sellerBooks.add(book);
            }
        }
        return sellerBooks;
    }
//check off books as sold
    public static boolean markBookAsSold(Book book){
        for(Book b : DataStore.books){
            if (b == book) {
                b.setSold(true);
                return true;
            }
        }
        return false;
    }
//delete book from the store
    public static boolean removeBook(Book book){
        return DataStore.books.remove(book);
    }
    //get all genres available in the store
    public static ObservableList<String> getAvailableGenres() {
        ObservableList<String> genres = FXCollections.observableArrayList();
        genres.add("All");
        for (Book book : DataStore.books) {
            if(!book.getSold() && !genres.contains(book.getGenre())){
                genres.add(book.getGenre());
            }
        }
        return genres;
    }
}
