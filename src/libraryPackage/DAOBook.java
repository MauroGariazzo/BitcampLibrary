package libraryPackage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOBook {

	private DBConnector connector = new DBConnector();
	
	private PreparedStatement pstmt;
	private Statement stmt;
	
	public DAOBook() {
		if(!connector.isConnectionActive()) {
			this.connector.connectToDB("/library");
		}
	}
	
	public List<Book> getAll() {
		List<Book>books = new ArrayList<Book>();
		String query = "SELECT b.book_id, b.title, b.book_year, a.author_name, a.author_surname, a.author_id\n"
				+ "FROM book b\n"
				+ "JOIN author a\n"
				+ "ON b.author_fk = a.author_id;";
		
		try {
			stmt = connector.getStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {	
				Author author = new Author(rs.getString("author_name"), rs.getString("author_surname"));
				author.setAuthor_id(rs.getInt("author_id"));
				Book book = new Book(rs.getString("title"), author, rs.getInt("book_year"));
				book.setBook_id(rs.getInt("book_id"));
				books.add(book);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		return books;
	}
	
	public void insert(Book book) {
		String query = "INSERT INTO book(title, author_fk, book_year) VALUES(?,?,?)";
		
		try {
			pstmt = connector.getPreparedStatement(query);
			pstmt.setString(1, book.getTitle());
			pstmt.setInt(2, book.getAuthor().getAuthor_id());
			pstmt.setInt(3, book.getYear());
			pstmt.executeUpdate();
			System.out.println(book.getTitle() + " di " + book.getAuthor().getName() + " " + book.getAuthor().getSurname() + " inserito correttamente");
		}
		
		catch(Exception e) {			
			System.out.println("Qualcosa è andato storto");
			e.printStackTrace();
		}
	}
	
	public void update(Book book) {
		String query = "UPDATE book SET title = ?, author_fk = ?, book_year = ? WHERE book_id = ?";
		
		try {
			pstmt = connector.getPreparedStatement(query);		
			pstmt.setString(1, book.getTitle());
			pstmt.setInt(2, book.getAuthor().getAuthor_id());
			pstmt.setInt(3, book.getYear());
			pstmt.setInt(4, book.getBook_id());
			
			pstmt.executeUpdate();
			System.out.println(book.getTitle() + " di " + book.getAuthor().getName() + " " + book.getAuthor().getSurname() + " inserito correttamente");
		}
		catch(Exception e) {
			System.out.println("Qualcosa è andato storto");
			e.printStackTrace();
		}
	}
	
	public void delete(Book book) {		
		String query = "DELETE FROM book WHERE book_id = ?";
		try {
			pstmt = connector.getPreparedStatement(query);
			pstmt.setInt(1, book.getBook_id());
			
			pstmt.executeUpdate();
			System.out.println(book.getTitle() + " di " + book.getAuthor().getName() + " " + book.getAuthor().getSurname() + " cancellato correttamente");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateAuthorBooks(Author author) {
		String query = "UPDATE BOOK SET author_fk = 1 WHERE AUTHOR_FK = ?";
		try {
			pstmt = connector.getPreparedStatement(query);
			pstmt.setInt(1, author.getAuthor_id());
			pstmt.executeUpdate();
		}
		catch(Exception e) {
			System.out.println();
			e.printStackTrace();
		}
		
	}
	
	public void close() {
		connector.closeStatementConnection();
		connector.closePreparedStatementConnection();
		connector.closeDBConnection();
	}
}
