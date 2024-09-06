package libraryPackage;

import java.sql.*;

public class DBConnector {
	private String URL = "jdbc:mysql://localhost:3306";
	private final String USERNAME = "root";				
	private final String PASSWORD = "Bitcamp_0";
	private static Connection conn; //statico in quanto la connessione può essere aperta sia da author che da book. Non può essere aperta 2 volte
	//quindi il valore di conn sarà condiviso fra le 2 istanze
	private PreparedStatement preparedStatement;
	private Statement statement;
	
	public DBConnector() {
		
	}
	
	
	public void connectToDB(String dbName) {
		try {
			this.URL += dbName;
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("Connessione al DB...Connessione al DB avvenuta con successo");
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Qualcosa è andato storto nella connessione al DB");
		}
	}
	
	public boolean isConnectionActive() {
		try {
			if (conn.isValid(5)) {
				//System.out.println("Connessione al database attiva.");
				return true;
			}
			else {
				System.out.println("Connessione al database inattiva.");
				return false;
			}
		}
		catch(Exception e) {
			System.out.println("Connessione al database non valida");
			e.printStackTrace();
		}
		return false;
	}
	
	public Statement getStatement() {
		try {
			statement = conn.createStatement(); 
			return statement;
		}
		catch(Exception e) {
			System.out.println("Qualcosa è andato storto nella creazione dell'oggetto Statement");
			e.printStackTrace();
			return null;
		}
	}
	
	public PreparedStatement getPreparedStatement(String query) {
		try {
			preparedStatement = conn.prepareStatement(query);
			return preparedStatement;
		}
		catch(Exception e) {
			System.out.println("Qualcosa è andato storto nella creazione dell'oggetto PreparedStatement");
			e.printStackTrace();
			return null;
		}
	}
	
	public void closeDBConnection() {
		try {
			this.conn.close();
			System.out.println("Connessione al DB chiusa");
		}
		catch(Exception e) {			
			System.out.println("Qualcosa è andato storto nella chiusura della connessione");
			e.printStackTrace();
		}
	}
	
	public void closeStatementConnection() {
		try {
			if(statement != null) {
				statement.close();
			}
		}
		catch(Exception e) {
			System.out.println("Qualcosa è andato storto nella chiusura dell' oggetto");
			e.printStackTrace();
		}
	}
	
	public void closePreparedStatementConnection(){
		try {
			if(preparedStatement != null) {
				preparedStatement.close();
			}
		}
		catch(Exception e) {			
			System.out.println("Qualcosa è andato storto nella chiusura dell' oggetto");
			e.printStackTrace();
		}
	}
}
