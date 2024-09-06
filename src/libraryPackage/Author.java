package libraryPackage;

public class Author {
	private int author_id;	
	private String name;
	private String surname;
	
	public Author(String name, String surname) {
		this.name = name;
		this.surname = surname;
	}
	
	public Author() {
		
	}
	
	public int getAuthor_id() {
		return author_id;
	}
	public void setAuthor_id(int author_id) {
		this.author_id = author_id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	@Override
	public String toString() {
		return name + " " + surname;
	}
}
