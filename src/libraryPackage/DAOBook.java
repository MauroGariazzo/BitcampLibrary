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
	
	public int booksCount(Author author) {
	    int row = 0;
	    String query = "SELECT COUNT(*) FROM book WHERE author_fk = ?";
	    try {
	        pstmt = connector.getPreparedStatement(query);
	        pstmt.setInt(1, author.getAuthor_id());
	        ResultSet rs = pstmt.executeQuery();  // Usa executeQuery() per una SELECT

	        if (rs.next()) {
	            row = rs.getInt(1);  // Recupera il valore del COUNT(*) dal result set
	        }
	    } catch(Exception e) {
	        e.printStackTrace();
	    } finally {
	        // Chiudi PreparedStatement e ResultSet se necessario per evitare memory leak
	        try {
	            if (pstmt != null) pstmt.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    return row;  // Ritorna il conteggio dei libri
	}

	
	public void close() {
		connector.closeStatementConnection();
		connector.closePreparedStatementConnection();
		connector.closeDBConnection();
	}
}
