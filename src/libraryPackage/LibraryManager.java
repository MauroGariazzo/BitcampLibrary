package libraryPackage;
import java.util.Scanner;

public class LibraryManager {
	 
	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		//con la creazione dell'oggetto LibraryDBInitializer viene creato in automatico il db, le tabelle e lo switch
		LibraryDBInitializer.Initialize();
		mainMenu();
	}

	private static void mainMenu() {		
		int choice = -1;
		
		while(choice != 4) {
			
			try {
				choice = IOManager.insertObjectIntInfo("\n=== Menu Principale ===\n"
						+ "1. Gestione Libri\r\n"
						+ "2. Gestione Autori\r\n"
						+ "3. Ricerca Avanzata Libri\r\n"
						+ "4. Esci\r\n"
						+ "Seleziona un'opzione:\r\n");
				
				switch(choice) {
					case 1:												
						ManageBooks.booksMenu();
						break;
					case 2:
						ManageAuthors.authorsMenu();
						break;
					case 3:
						ManageBookSearch.manageBooksSearchMenu();
						break;
					case 4:
						System.out.println("Sei uscito");
						scanner.close();
						break;
					default:
						System.out.println("Scelta non valida");
				}
			}
			catch(NumberFormatException e) {
				System.out.println("Qualcosa è andato storto");
				e.printStackTrace();
			}
		}
	}
	
	
}
