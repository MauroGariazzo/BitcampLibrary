package libraryPackage;
import java.util.Scanner;
import java.util.List;

public class IOManager {
	private static Scanner scanner = new Scanner(System.in);
	
	public static String insertObjectStringInfo(String message) {
		while(true) {
			System.out.println(message);
			String input = scanner.nextLine();
			
			if(!input.isEmpty()) {
				return input;
			}
			System.out.println("Il campo non può essere vuoto");
		}	
	}
	
	public static int insertObjectIntInfo(String message) {
		while(true) {
			System.out.println(message);
			String input = scanner.nextLine();
			
			if(!input.isEmpty()) {
				try {
					int intInput = Integer.parseInt(input);
					return intInput;
				}
				catch(Exception e) {
					System.out.println("Input non valido");
				}
			}
			else {
				System.out.println("Il campo non può essere vuoto");
			}
			
		}	
	}
}
