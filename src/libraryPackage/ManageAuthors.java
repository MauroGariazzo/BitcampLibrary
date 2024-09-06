package libraryPackage;
import java.util.List;
import java.util.Scanner;

public class ManageAuthors {
	
	
	public static void authorsMenu() {
		DAOAuthor daoAuthor = new DAOAuthor();	
		int choice = -1;
		
		while(true) {
			
			try {								
				choice = IOManager.insertObjectIntInfo("\n=== Gestione Autori ===\r\n"
						+ "1. Aggiungi un nuovo autore\r\n"
						+ "2. Visualizza tutti gli autori\r\n"
						+ "3. Modifica un autore\r\n"
						+ "4. Cancella un autore\r\n"
						+ "5. Torna al menu principale\r\n"
						+ "Seleziona un'opzione:");
			
				switch(choice) {
					case 1:						
						Author author = getAuthorObject(new Author());
						sendAuthorToDB(daoAuthor, author);
						break;
					case 2:
						List<Author> authors = daoAuthor.getAll();
						printAuthors(authors);
						break;
					case 3:
						author = choiceAuthor(daoAuthor);
						if(author!=null) {
							author = getAuthorObject(author);
							editAuthor(author, daoAuthor);							
						}
						break;
					case 4:
						author = choiceAuthor(daoAuthor);
						if(author!=null) {
							deleteAuthor(author, daoAuthor);
						}						
						break;
					case 5:
						//chiudere connessione
						daoAuthor.close();
						return;
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
			
	
	//metodo per inserire le informazioni dell'autore, sia nel caso dell'inserimento che della modifica
	public static Author getAuthorObject(Author author) {		
		author.setName(IOManager.insertObjectStringInfo("Inserisci nome autore: "));
		author.setSurname(IOManager.insertObjectStringInfo("Inserisci cognome autore: "));
		
		return author;
	}
	
	
	private static void printAuthors(List<Author> authors)
	{	
		if(authors.size() == 0) {
			System.out.println("Nessun autore da visualizzare");
			return;
		}
		int row = 1;
		for(Author author:authors) {
			System.out.println(row + ". " + author);
		}
	}
	
	public static int sendAuthorToDB(DAOAuthor daoAuthor, Author author) {
		int author_id=-1;
				
		if(!author.getName().toLowerCase().equals("nessun") && !author.getSurname().toLowerCase().equals("autore")) {
			author_id = daoAuthor.insert(author);
		}
		else {
			System.out.println("Non puoi inseire un autore con questo nominativo");
		}		
		return author_id; //id dell'autore appena creato
	}
	
	private static void editAuthor(Author author, DAOAuthor daoAuthor) {
		daoAuthor.update(author);
	}
	private static void deleteAuthor(Author author, DAOAuthor daoAuthor) {
		DAOBook daoBook = new DAOBook();
		daoBook.updateAuthorBooks(author);
		daoAuthor.delete(author);
	}

	
	public static Author choiceAuthor(DAOAuthor daoAuthor) {
		List<Author>authors = daoAuthor.getAll(); 	
		int input = -1;
		if(authors.size() > 0)
		{
			while(true) {
				printAuthors(authors);
			
				try {
					input = IOManager.insertObjectIntInfo("Seleziona un autore: ");
					if(input>=1 && input<=authors.size()) {
						break;
					}
					else {
						System.out.println("Input fuori range");
					}
				}
				catch(Exception e) {
					System.out.println("Qualcosa è andato storto");
					e.printStackTrace();
				}
			}			
			return authors.get(input-1);
		}
		else {
			printAuthors(authors);
		}
		return null;
	}
}
