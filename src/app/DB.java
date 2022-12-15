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
				"CREATE TABLE students(id INTEGER PRIMARY KEY AUTOINCREMENT,firstname varchar(30),lastname varchar(30),course varchar(20), yearlevel INTEGER);",
				"CREATE TABLE subjects(id INTEGER PRIMARY KEY AUTOINCREMENT,subname varchar(20) UNIQUE, offernum varchar(20) UNIQUE);",
				"CREATE TABLE student_subjects(id INTEGER PRIMARY KEY AUTOINCREMENT,student_id integer,subject_id integer);"
			};
			Statement stat = c.createStatement();
			for(String q : tables) {
				stat.execute(q);
			}
			stat.close();
			c.close();
		} catch (SQLException e) { System.out.println("Database Already Exists!"); }
	}
	
	public static ArrayList<Subject> getAvailableSubjects(Student student) {
		ArrayList<Subject> list = new ArrayList<>();
    	try {
        	c = connect();
        	String sql = "SELECT * FROM subjects "
        			+ "WHERE subname NOT IN ("
        			+ "  SELECT subname FROM student_subjects ssub"
        			+ "  LEFT JOIN subjects sub ON ssub.subject_id = sub.id"
        			+ "  WHERE student_id = ?"
        			+ ")";
        	PreparedStatement ps = c.prepareStatement(sql);
        	ps.setInt(1, student.getId());
        	ResultSet rs = ps.executeQuery();
        	while(rs.next()) {
        		list.add(new Subject(rs.getInt("id"),rs.getString("subname"),
            	        rs.getString("offernum")));
        	}
    	} catch (SQLException e) {
            e.printStackTrace();
        }
    	return list;
	}
	
	public static ArrayList<Subject> getStudentSubjects(Student student) {
		ArrayList<Subject> list = new ArrayList<>();
    	try {
        	c = connect();
        	String sql = "SELECT sub.id,sub.subname,sub.offernum "
        			+ "FROM student_subjects ssub "
        			+ "LEFT JOIN subjects sub ON ssub.subject_id = sub.id "
        			+ "WHERE ssub.student_id = ?;";
        	PreparedStatement ps = c.prepareStatement(sql);
        	ps.setInt(1, student.getId());
        	ResultSet rs = ps.executeQuery();
        	while(rs.next()) {
        		list.add(new Subject(rs.getInt("id"),rs.getString("subname"),
            	        rs.getString("offernum")));
        	}
    	} catch (SQLException e) {
            e.printStackTrace();
        }
    	return list;
	}
	
	public static ArrayList<Student> getStudents() {
		ArrayList<Student> list = new ArrayList<>();
    	try {
        	c = connect();
        	PreparedStatement ps = c.prepareStatement("SELECT * from students");
        	ResultSet rs = ps.executeQuery();
        	while(rs.next()) {
        	    list.add(new Student(rs.getInt("id"),rs.getString("firstname"),rs.getString("lastname"),
        	        rs.getString("course"),rs.getInt("yearlevel")));
        	}
    	} catch (SQLException e) {
            e.printStackTrace();
        }
    	return list;
	}
	
	public static ArrayList<Subject> getSubjects() {
		ArrayList<Subject> list = new ArrayList<>();
    	try {
        	c = connect();
        	PreparedStatement ps = c.prepareStatement("SELECT * from subjects");
        	ResultSet rs = ps.executeQuery();
        	while(rs.next()) {
        	    list.add(new Subject(rs.getInt("id"),rs.getString("subname"),
        	        rs.getString("offernum")));
        	}
    	} catch (SQLException e) {
            e.printStackTrace();
        }
    	return list;
	}
	
	public static void insertStudentSubject(int student_id, int subject_id) {
		try {
       		c = connect();
       		PreparedStatement ps = c.prepareStatement("INSERT INTO student_subjects (student_id,subject_id) VALUES (?,?)");
       		ps.setInt(1, student_id);
       		ps.setInt(2, subject_id);
       		ps.executeUpdate();
    	} catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public static void insertStudent(String fname,String lname,String course,int yearlevel) {
		try {
       		c = connect();
       		PreparedStatement ps = c.prepareStatement("INSERT INTO students (firstname,lastname,course,yearlevel) VALUES (?,?,?,?)");
       		ps.setString(1, fname);
       		ps.setString(2, lname);
       		ps.setString(3, course);
       		ps.setInt(4, yearlevel);
       		ps.executeUpdate();
    	} catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public static boolean insertSubject(String subname,String offernum) {
		try {
       		c = connect();
       		PreparedStatement ps = c.prepareStatement("INSERT INTO subjects (subname,offernum) VALUES (?,?)");
       		ps.setString(1, subname);
       		ps.setString(2, offernum);
       		ps.executeUpdate();
    	} catch (SQLException e) {
            return false;
        }
		return true;
	}
	
	public static void removeStudentSubject(Student student,Subject subject) {
		try {
       		c = connect();
       		PreparedStatement ps = c.prepareStatement("DELETE FROM student_subjects WHERE student_id = ? AND subject_id = ?;");
       		ps.setInt(1, student.getId());
       		ps.setInt(2, subject.getId());
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
	
	public static void removeSubject(Subject subject) {
		try {
       		c = connect();
       		PreparedStatement ps = c.prepareStatement("DELETE FROM subjects WHERE id = ?;");
       		ps.setInt(1, subject.getId());
       		ps.executeUpdate();
    	} catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public static boolean editSubject(Subject subject) {
		try {
       		c = connect();
       		PreparedStatement ps = c.prepareStatement("UPDATE subjects SET subname = ?, offernum = ? WHERE id = ?");
       		ps.setString(1, subject.getSubname());
       		ps.setString(2, subject.getOffernum());
       		ps.setInt(3, subject.getId());
       		ps.executeUpdate();
    	} catch (SQLException e) {
    		
            return false;
        }
		return true;
	}
	
	public static void editStudent(Student student) {
		try {
       		c = connect();
       		PreparedStatement ps = c.prepareStatement("UPDATE students SET firstname = ?,lastname = ?,course = ?,yearlevel = ? WHERE id = ?");
       		ps.setString(1, student.getFirstName());
       		ps.setString(2, student.getLastName());
       		ps.setString(3, student.getCourse());
       		ps.setInt(4, student.getYearLevel());
       		ps.setInt(5, student.getId());
       		ps.executeUpdate();
    	} catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public static ArrayList<Student> getSubjectStudents(Subject subject) {
		ArrayList<Student> list = new ArrayList<>();
		try {
        	c = connect();
        	PreparedStatement ps = c.prepareStatement("SELECT students.id,firstname,lastname,course,yearlevel FROM student_subjects ss Left JOIN students ON students.id = ss.student_id WHERE subject_id = ?;");
        	ps.setInt(1, subject.getId());
        	ResultSet rs = ps.executeQuery();
        	while(rs.next()) {
        	    list.add(new Student(rs.getInt("id"),rs.getString("firstname"),rs.getString("lastname"),
        	        rs.getString("course"),rs.getInt("yearlevel")));
        	}
    	} catch (SQLException e) {
    		e.printStackTrace();
        }
    	return list;
	}
	
	public static boolean searchDuplicateStudent(String info) {
		try {
        	c = connect();
        	PreparedStatement ps = c.prepareStatement("SELECT * FROM students WHERE firstname || lastname || course || yearlevel LIKE ?;");
        	ps.setString(1, info);
        	ResultSet rs = ps.executeQuery();
        	while(rs.next()) {
        	    return true;
        	}
    	} catch (SQLException e) {
    		e.printStackTrace();
        }
		return false;
	}
	
	public static ArrayList<Student> searchStudent(String search) {
		ArrayList<Student> list = new ArrayList<>();
    	try {
        	c = connect();
        	PreparedStatement ps = c.prepareStatement("SELECT * FROM students WHERE firstname || lastname || course LIKE ?;");
        	ps.setString(1, search);
        	ResultSet rs = ps.executeQuery();
        	while(rs.next()) {
        	    list.add(new Student(rs.getInt("id"),rs.getString("firstname"),rs.getString("lastname"),
        	        rs.getString("course"),rs.getInt("yearlevel")));
        	}
    	} catch (SQLException e) {
    		e.printStackTrace();
        }
    	return list;
	}
}
