package libraryPackage;
import java.util.List;
import java.util.Scanner;

public class ManageBookSearch {
	private static Scanner scanner = new Scanner(System.in);
	
	public static void manageBooksSearchMenu() {				
		int choice = -1;
		DAOSearchBooks daoSearchBooks = new DAOSearchBooks();
		while(choice != 4) {						
			
			try {
				choice = IOManager.insertObjectIntInfo("=== Ricerca Avanzata Libri ===\r\n"
						+ "1. Cerca per Titolo\r\n"
						+ "2. Cerca per Autore\r\n"
						+ "3. Cerca per Anno di Pubblicazione\r\n"
						+ "4. Torna al menu principale\r\n"
						+ "Seleziona un'opzione:\r\n");
				switch(choice) {
					case 1:
						String title = IOManager.insertObjectStringInfo("Inserisci titolo: ");
						List<Book>books = daoSearchBooks.searchBookByTitle(title);
						ManageBooks.printBooks(books);
						break;
					case 2:
						String author = IOManager.insertObjectStringInfo("Inserisci il nome o il cognome dell'autore: ");
						books = daoSearchBooks.searchBookByAuthor(author);
						ManageBooks.printBooks(books);
						break;
					case 3:
						while(true) {
							int startYear = IOManager.insertObjectIntInfo("Inserisci anno di inizio: ");
							int endYear = IOManager.insertObjectIntInfo("Inserisci anno di fine: ");
							if(startYear <= endYear) {
								books = daoSearchBooks.searchBookByYear(startYear, endYear);
								ManageBooks.printBooks(books);
								break;
							}
							else {
								System.out.println("L'anno di fine non può essere minore della data di inizio");
							}
						}
						break;
					case 4:
						//chiusura oggetto dao
						return;
					default:
						System.out.println("Input non compreso nel range");
						break;
				}
			}
			catch(Exception e) {
				System.out.println("Input non valido");
				e.printStackTrace();
			}
		}
	}
	
	
	
}
