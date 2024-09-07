package libraryPackage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOAuthor {

	private DBConnector connector = new DBConnector();
	
	private PreparedStatement pstmt;
	private Statement stmt;
	
	public DAOAuthor() {
		if(!connector.isConnectionActive()) {
			this.connector.connectToDB("/library");
		}
	}
	
	public List<Author> getAll() {
		List<Author>authors = new ArrayList<Author>();
		String query = "SELECT * FROM Author";
		
		try {
			stmt = connector.getStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				Author author = new Author(rs.getString("author_name"),rs.getString("author_surname"));
				author.setAuthor_id(rs.getInt("author_id"));
				authors.add(author);
			}
		}
		catch(Exception e) {			
			e.printStackTrace();			
		}		
		return authors;
	}
	
	public int insert(Author author) {		
		String query = "INSERT INTO author(author_name, author_surname) VALUES(?,?)";
		int author_id = -1;
		try {
			pstmt = connector.getPreparedStatement(query);
			pstmt.setString(1, author.getName());
			pstmt.setString(2, author.getSurname());
			pstmt.executeUpdate();
			System.out.println(author.getName() + " " + author.getSurname() + " inserito correttamente");
			
			//query per ottenere l'id dell'autore appena inserito
			String selectQuery = "SELECT MAX(author_id) FROM author";
	        Statement stmt = connector.getStatement();
	        ResultSet rs = stmt.executeQuery(selectQuery);

	        if (rs.next()) {
	        	author_id = rs.getInt(1);  // Ottieni l'ID massimo(ultimo id)
	        }
		}
		
		catch(Exception e) {			
			System.out.println("Qualcosa è andato storto");
			e.printStackTrace();
		}
		return author_id;
	}
	
	public void update(Author author) {
		String query = "UPDATE author SET author_name = ?, author_surname = ? WHERE author_id = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = connector.getPreparedStatement(query);		
			pstmt.setString(1, author.getName());
			pstmt.setString(2, author.getSurname());
			pstmt.setInt(3, author.getAuthor_id());
			
			pstmt.executeUpdate();
			System.out.println(author.getName() + " " + author.getSurname() + " inserito correttamente");
		}
		catch(Exception e) {
			System.out.println("Qualcosa è andato storto");
			e.printStackTrace();
		}
	}
	
	public void delete(Author author) {		
		String query = "DELETE FROM author WHERE author_id = ?";
		try {
			pstmt = connector.getPreparedStatement(query);			
			pstmt.setInt(1, author.getAuthor_id());
			
			pstmt.executeUpdate();
			System.out.println(author.getName() + " " + author.getSurname() + " eliminato correttamente");
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void close() {
		connector.closeStatementConnection();
		connector.closePreparedStatementConnection();
		connector.closeDBConnection();
	}
}
