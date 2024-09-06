package libraryPackage;
import java.sql.*;

public class LibraryDBInitializer {
	private static DBConnector connector = new DBConnector();
	private static PreparedStatement preparedStatement;
	private static Statement statement;
	
	public static void Initialize() {
		connector.connectToDB("");
		createDB();
		switchDB();
		createTables();
		insertNoAuthor();
		closeConnections();
	}
	
	private static void createDB() {
		String query = "CREATE DATABASE IF NOT EXISTS library";
		
		try {
			preparedStatement = connector.getPreparedStatement(query);
			preparedStatement.executeUpdate();			
			System.out.println("DB creato con successo");
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
	
	private static void createTables() {
		String createAuthorTableQuery = "CREATE TABLE IF NOT EXISTS author"
				+ "(author_id INT AUTO_INCREMENT PRIMARY KEY,"
				+ "author_name VARCHAR(100) NOT NULL,"
				+ "author_surname VARCHAR(100) NOT NULL)";
		
		String createBookTableQuery = "CREATE TABLE IF NOT EXISTS book"
				+ "(book_id INT AUTO_INCREMENT PRIMARY KEY,"
				+ "title VARCHAR(100) NOT NULL,"
				+ "author_fk INT NOT NULL,"
				+ "book_year int,"
				+ "FOREIGN KEY(author_fk) REFERENCES author(author_id))";
		
		try {
			preparedStatement = connector.getPreparedStatement(createAuthorTableQuery);
			preparedStatement.executeUpdate();
			preparedStatement = connector.getPreparedStatement(createBookTableQuery);
			preparedStatement.executeUpdate();
			System.out.println("Tabelle create con successo");		
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	private static void insertNoAuthor() {
		String checkQuery = "SELECT COUNT(*) FROM AUTHOR WHERE author_name = ? AND author_surname = ?";
		String insertQuery = "INSERT INTO AUTHOR(author_name, author_surname) VALUES(?,?)";
		try{	
			preparedStatement = connector.getPreparedStatement(checkQuery);
			
			preparedStatement.setString(1, "Nessun");
			preparedStatement.setString(2, "Autore");
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next() && rs.getInt(1) == 0) {  // Controlla se il conteggio è 0
	            preparedStatement = connector.getPreparedStatement(insertQuery);
	            preparedStatement.setString(1, "Nessun");
	            preparedStatement.setString(2, "Autore");
	            preparedStatement.executeUpdate();
	        }
		}
		catch(Exception e) {
			System.out.println("Qualcosa è andato storto");
			e.printStackTrace();
		}
	}
	
	private static void switchDB() {
		String query = "USE library";
		
		try {
			statement = connector.getStatement();
			statement.executeUpdate(query);
			System.out.println("Library selezionato");
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
	private static void closeConnections() {
		connector.closeStatementConnection();
		connector.closePreparedStatementConnection();
		connector.closeDBConnection();
	}
}
