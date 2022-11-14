package app;


import javax.swing.ImageIcon;

import java.awt.Image;
import java.util.ArrayList;

public class Student {
	@Override
	public String toString() {
		return "Student [id=" + id + ", fullname=" + fullname + ", yearlevel=" + yearlevel + ", course=" + course + "]";
	}
	
	private ImageIcon image;
	private int id;
	private String fullname;
	private int yearlevel;
	private String course;
	private ArrayList<Subject> subjects;
	
	public Student(int id,String fullname,String course,int yearlevel) {
		this.image = new ImageIcon("./images/"+id+".jpg");
		Image i = image.getImage(); // transform it 
		Image newimg = i.getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		this.image = new ImageIcon(newimg);  // transform it back
		
		this.id = id;
		this.fullname = fullname;
		this.yearlevel = yearlevel;
		this.course = course;
	}

	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	public String getFullName() {return fullname;}
	public void setFullname(String fullname) {this.fullname = fullname;}
	public int getYearLevel() {return yearlevel;}
	public void setYearLevel(int lvl) {this.yearlevel = lvl;}
	public String getCourse() {return course;}
	public void setCourse(String course) {this.course = course;}
	public ImageIcon getImage() {return image;}
	public void setImage(ImageIcon image) {this.image = image;}
	
}
