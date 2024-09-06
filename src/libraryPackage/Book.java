package libraryPackage;

public class Book {
	private int book_id;
	private String title;
	private Author author;
	private int year;
	
	public Book(String title, Author author, int year) {
		this.title = title;
		this.author = author;
		this.year = year;
	}
	
	public Book() {
		
	}
	
	public int getBook_id() {
		return book_id;
	}
	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author_fk) {
		this.author = author_fk;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	@Override
	public String toString() {
		return title + " scritto da " + author + ", " + year;
	}
}
