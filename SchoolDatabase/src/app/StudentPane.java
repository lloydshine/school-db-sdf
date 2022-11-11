package app;


import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class StudentPane extends JPanel {
	
	private static final long serialVersionUID = -5422043241751244122L;

	public StudentPane(Student student) {
		this.setBackground(Color.LIGHT_GRAY);
		Border blackline = BorderFactory.createLineBorder(Color.black);
		setBorder(blackline);
		setPreferredSize(new Dimension(230, 289));
		setLayout(null);
		
		JLabel image = new JLabel(student.getImage());
		image.setHorizontalAlignment(SwingConstants.CENTER);
		image.setBounds(20, 11, 188, 159);
		add(image);
		
		JLabel l1 = new JLabel("Name:");
		l1.setBounds(10, 181, 38, 14);
		add(l1);
		
		JLabel namelabel = new JLabel(student.getFullName());
		namelabel.setBounds(58, 181, 172, 14);
		add(namelabel);
		
		JLabel l2 = new JLabel("Yrlvl:");
		l2.setBounds(10, 206, 38, 14);
		add(l2);
		
		JLabel agelabel = new JLabel(Integer.toString(student.getYearLevel()));
		agelabel.setBounds(58, 206, 140, 14);
		add(agelabel);
		
		JLabel l3 = new JLabel("Course:");
		l3.setBounds(10, 231, 48, 14);
		add(l3);
		
		JLabel courselabel = new JLabel(student.getCourse());
		courselabel.setBounds(58, 231, 172, 14);
		add(courselabel);
	}
}
