package app;

public class Main {
	public static void main(String[] args) {
		DB.createTables();
		System.out.println("Running");
		App.main(args);
	}
}
