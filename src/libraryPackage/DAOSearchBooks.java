package libraryPackage;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class DAOSearchBooks {
	private DBConnector connector = new DBConnector();
	public DAOSearchBooks() {
		if(!connector.isConnectionActive()) {
			this.connector.connectToDB("/library");
		}
	}
	
	public List<Book> searchBookByTitle(String title){
		String query = "SELECT b.*, a.author_name, a.author_surname FROM book b JOIN author a ON b.author_fk=a.author_id WHERE title LIKE ?";
		List<Book> books = new ArrayList<Book>();
		try {
			PreparedStatement pstmt = connector.getPreparedStatement(query);
			pstmt.setString(1, "%"+title+"%");
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				books.add(new Book(rs.getString("title"), new Author(rs.getString("author_name"), rs.getString("author_surname")), rs.getInt("book_year")));
			}
		}
		catch(Exception e) {
			System.out.println("Qualcosa è andato storto");
			e.printStackTrace();
		}
		return books;
	}
	
	public List<Book> searchBookByAuthor(String authorNameSurname){
		String query = "SELECT b.*, a.author_name, a.author_surname FROM book b JOIN author a ON b.author_fk=a.author_id WHERE author_name LIKE ? OR author_surname LIKE ?";
		List<Book> books = new ArrayList<Book>();
		try {
			PreparedStatement pstmt = connector.getPreparedStatement(query);
			pstmt.setString(1, "%"+authorNameSurname+"%");
			pstmt.setString(2, "%"+authorNameSurname+"%");
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				books.add(new Book(rs.getString("title"), new Author(rs.getString("author_name"), rs.getString("author_surname")), rs.getInt("book_year")));
			}
		}
		catch(Exception e) {
			System.out.println("Qualcosa è andato storto");
			e.printStackTrace();
		}
		return books;
	}
	
	public List<Book> searchBookByYear(int startYear, int endYear){
		String query = "SELECT b.*, a.author_name, a.author_surname FROM book b JOIN author a ON b.author_fk=a.author_id WHERE b.book_year BETWEEN ? AND ?";
		List<Book> books = new ArrayList<Book>();
		try {
			PreparedStatement pstmt = connector.getPreparedStatement(query);
			pstmt.setInt(1, startYear);
			pstmt.setInt(2, endYear);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				books.add(new Book(rs.getString("title"), new Author(rs.getString("author_name"), rs.getString("author_surname")), rs.getInt("book_year")));
			}
		}
		catch(Exception e) {
			System.out.println("Qualcosa è andato storto");
			e.printStackTrace();
		}
		return books;
	}
}
