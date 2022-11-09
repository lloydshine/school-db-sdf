package app;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DB {
	private static Connection c = null;
	
	public static Connection connect() {
		String url = "jdbc:sqlite:schooldb.db";
		try {
			if (c != null) {
				c.close();
			}	
			c = (Connection) DriverManager.getConnection(url);
		} catch(SQLException e) {e.printStackTrace();}
	    return c;
	}
	
	public static void deleteTable(String table) {
		try {
			c = connect();
			String deleteSQL = "DROP TABLE " + table;
			Statement stat = c.createStatement();
			stat.execute(deleteSQL);
			c.close();
			stat.close();
		} catch(SQLException e) {e.printStackTrace();}
	}
	
	public static void createTables() {
		try {
			c = connect();
			String[] tables = {
				"CREATE TABLE students(id INTEGER PRIMARY KEY AUTOINCREMENT,fullname varchar(30),course varchar(20), yearlevel INTEGER);",
				"CREATE TABLE subjects(id INTEGER PRIMARY KEY AUTOINCREMENT,subname varchar(20), instructor varchar(20));",
				"CREATE TABLE student_subjects(id INTEGER PRIMARY KEY AUTOINCREMENT,student_id integer,subject_id integer);"
			};
			Statement stat = c.createStatement();
			for(String q : tables) {
				stat.execute(q);
			}
			stat.close();
			c.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	public static ArrayList<Student> getStudents() {
		ArrayList<Student> list = new ArrayList<>();
    	try {
        	c = connect();
        	PreparedStatement ps = c.prepareStatement("SELECT * from students");
        	ResultSet rs = ps.executeQuery();
        	while(rs.next()) {
        	    list.add(new Student(rs.getInt("id"),rs.getString("fullname"),
        	        rs.getString("course"),rs.getInt("yearlevel")));
        	}
    	} catch (SQLException e) {
            e.printStackTrace();
        }
    	return list;
	}
	
	public static void insertStudent(Student student) {
		try {
       		c = connect();
       		PreparedStatement ps = c.prepareStatement("INSERT INTO students (fullname,course,yearlevel) VALUES (?,?,?)");
       		ps.setString(1, student.getFullName());
       		ps.setString(2, student.getCourse());
       		ps.setInt(3, student.getYearLevel());
       		ps.executeUpdate();
    	} catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public static void removeStudent(Student student) {
		try {
       		c = connect();
       		PreparedStatement ps = c.prepareStatement("DELETE FROM students WHERE id = ?;");
       		ps.setInt(1, student.getId());
       		ps.executeUpdate();
    	} catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public static ArrayList<Student> searchStudent(String search) {
		ArrayList<Student> list = new ArrayList<>();
    	try {
        	c = connect();
        	PreparedStatement ps = c.prepareStatement("SELECT * FROM students WHERE fullname LIKE ?;");
        	ps.setString(1, search);
        	ResultSet rs = ps.executeQuery();
        	while(rs.next()) {
        	    list.add(new Student(rs.getInt("id"),rs.getString("fullname"),
        	        rs.getString("course"),rs.getInt("yearlevel")));
        	}
    	} catch (SQLException e) {
        }
    	System.out.println(list.size());
    	return list;
	}
}
