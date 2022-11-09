package app;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		DB.createTables();
		System.out.println("Students: ");
		ArrayList<Student> studentlist = DB.searchStudent("%Lloyd%");
		for(Student s : studentlist) {
			System.out.println(s.toString());
		}
		App.main(args);
	}
}
