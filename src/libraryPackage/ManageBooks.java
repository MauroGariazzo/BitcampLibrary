package libraryPackage;

import java.util.List;
import java.util.Scanner;

public class ManageBooks {
	private static Scanner scanner = new Scanner(System.in);
	
	public static void booksMenu() {
		DAOBook daoBook = new DAOBook();	
		int choice = -1;
		
		while(true) {
						
			try {
				choice = IOManager.insertObjectIntInfo("\n=== Gestione Libri ===\r\n"
						+ "1. Aggiungi un nuovo libro\r\n"
						+ "2. Visualizza tutti i libri\r\n"
						+ "3. Modifica un libro\r\n"
						+ "4. Cancella un libro\r\n"
						+ "5. Torna al menu principale\r\n"
						+ "Seleziona un'opzione:");
			
				switch(choice) {
					case 1:						
						Book book = createBook(new Book());
						if(book != null) {
							sendBookToDB(book, daoBook);
						}
						break;
					case 2:
						List<Book> books = daoBook.getAll();
						printBooks(books);
						break;
					case 3:
						book = choiceBook(daoBook);
						if(book != null) {
							book = createBook(book);
							editBook(book, daoBook);
						}
						break;
					case 4:
						book = choiceBook(daoBook);
						if(book != null) {
							deleteBook(book, daoBook);
						}						
						break;
					case 5:
						//chiudere connessione
						daoBook.close();
						return;
					default:
						System.out.println("Scelta non valida");
				}
			}
			catch(NumberFormatException e) {
				System.out.println("Input non valido");
				e.printStackTrace();
			}
		}		
	}
		
	
	private static Book createBook(Book book) {
		String title = IOManager.insertObjectStringInfo("Inserisci il titolo del libro: ");		
		DAOAuthor daoAuthor = new DAOAuthor();
		Author author = new Author();
		
		//crea nuovo autore
		if(choiceIfCreateANewAuthor() == 1) {
			if(!createAuthorForNewBook(author, daoAuthor)) {
				return null;
			}
		}
		else {
		//recuperalo dal DB e seleziona quello che ti interessa
			author = ManageAuthors.choiceAuthor(daoAuthor);
			//non c'è nessun autore disponibile, allora crealo
			
			if(author == null) {
				author = new Author();
				System.out.println("Devi prima creare un autore!");
				if(!createAuthorForNewBook(author, daoAuthor)) {
					return null;
				}				
			}
		}
		
		int year = IOManager.insertObjectIntInfo("Inserisci l'anno di pubblicazione: ");
		book.setTitle(title);
		book.setAuthor(author);
		book.setYear(year);
		return book;
	}
	
	private static void sendBookToDB(Book book, DAOBook daoBook) {
		daoBook.insert(book);
	}
	
	private static void editBook(Book book, DAOBook daoBook) {
		daoBook.update(book);
	}
	
	private static void deleteBook(Book book, DAOBook daoBook) {
		daoBook.delete(book);
	}
	
	private static boolean createAuthorForNewBook(Author author, DAOAuthor daoAuthor) {
		author = ManageAuthors.getAuthorObject(author);
		int author_id = ManageAuthors.sendAuthorToDB(daoAuthor, author);
		if(author_id > 0) {
			author.setAuthor_id(author_id);
			return true;
		}
		return false;
	}
	
			
	private static int choiceIfCreateANewAuthor() {		
		System.out.println("Inserisci l'autore del libro:");
		
		while(true) {
			System.out.println("1. Per creare un nuovo autore,"
					+ " 2. Per abbinarne uno tra quelli già esistenti");
			
			String input = scanner.nextLine();
			
			try {
				int intInput = Integer.parseInt(input);
				if(intInput >= 1 && intInput <= 2) {
					return intInput;
				}
				else {
					System.out.println("Input fuori range");					
				}
			}
			catch(Exception e) {
				System.out.println("Input non valido");
				e.printStackTrace();
			}
		}
	}
	
	
	public static void printBooks(List<Book> books)
	{
		if(books.size()==0) {
			System.out.println("Nessun libro da visualizzare");
			return;
		}
		int row = 1;
		for(Book book:books) {
			System.out.println(row + ". " + book);
			row++;
		}
	}
		
	
	public static Book choiceBook(DAOBook daoBook) {
		List<Book>books = daoBook.getAll(); 	
		int input = -1;
		if(books.size() > 0)
		{
			while(true) {
				printBooks(books);
			
				try {
					input = IOManager.insertObjectIntInfo("Seleziona un libro: ");
					if(input>=1 && input<=books.size()) {
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
			return books.get(input-1);
		}
		else {
			printBooks(books);
		}
		return null;
	}
}
