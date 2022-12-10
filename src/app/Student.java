package app;


import javax.swing.ImageIcon;

import java.awt.Image;
import java.util.ArrayList;

public class Student {
	@Override
	public String toString() {
		return "Student [id=" + id + ", fullname=" + getFullName() + ", yearlevel=" + yearlevel + ", course=" + course + "]";
	}
	
	private ImageIcon image;
	private int id;
	private String firstname;
	private String lastname;
	private int yearlevel;
	private String course;
	
	public Student(int id,String fname,String lname,String course,int yearlevel) {
		this.image = new ImageIcon("./images/"+id+".jpg");
		Image i = image.getImage(); // transform it 
		Image newimg = i.getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		this.image = new ImageIcon(newimg);  // transform it back
		
		this.id = id;
		this.firstname = fname;
		this.lastname = lname;
		this.yearlevel = yearlevel;
		this.course = course;
	}

	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	public String getFullName() {return firstname + " " + lastname;}
	public void setFirstname(String firstname) { this.firstname = firstname; }
	public void setLastname(String lastname) {this.lastname = lastname;}
	public int getYearLevel() {return yearlevel;}
	public void setYearLevel(int lvl) {this.yearlevel = lvl;}
	public String getCourse() {return course;}
	public void setCourse(String course) {this.course = course;}
	public ImageIcon getImage() {return image;}
	public void setImage(ImageIcon image) {this.image = image;}
	public ArrayList<Subject> getSubjects() {
		ArrayList<Subject> subjects = DB.getStudentSubjects(this);
		return subjects;
	}
	public String getLastName() { return lastname; }
	public String getFirstName() { return firstname; }
	
}
