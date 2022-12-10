package app;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class App extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private StudentPanel studentpanel;
	private Student clicked = null;
	private JButton viewbtn, searchbtn,addstudentbtn,viewsubjectbtn;
	ArrayList<Student> studentlist;
	private JLabel image, name;
	private JScrollPane scrollPane;
	private JTextField textField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App frame = new App();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public App() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(798, 616);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		image = new JLabel("No Student Selected");
		image.setHorizontalAlignment(SwingConstants.CENTER);
		image.setBackground(Color.LIGHT_GRAY);
		image.setBounds(10, 112, 188, 159);
		contentPane.add(image);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(211, 90, 544, 475);
		scrollPane.setPreferredSize(new Dimension(544, 475));
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		contentPane.add(scrollPane);
		
		studentpanel = new StudentPanel();
		scrollPane.setViewportView(studentpanel);
		
		textField = new JTextField();
		textField.setBounds(341, 31, 256, 37);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel l2 = new JLabel("Search Student:");
		l2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		l2.setBounds(231, 34, 113, 26);
		contentPane.add(l2);
		
		viewbtn = new JButton("VIEW");
		viewbtn.addActionListener(this);
		viewbtn.setEnabled(false);
		viewbtn.setBounds(36, 376, 127, 45);
		contentPane.add(viewbtn);
		
		name = new JLabel("");
		name.setHorizontalAlignment(SwingConstants.CENTER);
		name.setFont(new Font("Tahoma", Font.PLAIN, 14));
		name.setBounds(10, 306, 188, 26);
		contentPane.add(name);
		
		searchbtn = new JButton("SEARCH");
		searchbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layoutStudents();
			}
		});
		searchbtn.setBounds(619, 31, 102, 37);
		contentPane.add(searchbtn);
		
		addstudentbtn = new JButton("Add Student");
		addstudentbtn.setBounds(36, 468, 127, 45);
		addstudentbtn.addActionListener(this);
		contentPane.add(addstudentbtn);
		
		viewsubjectbtn = new JButton("View Subjects");
		viewsubjectbtn.setBounds(36, 524, 127, 45);
		viewsubjectbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SubjectsTable st = new SubjectsTable();
				st.setVisible(true);
			}
		});
		contentPane.add(viewsubjectbtn);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 255, 0));
		panel.setBounds(10, 25, 173, 43);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel cd = new JLabel("CCS DATABASE");
		cd.setBounds(8, 11, 158, 26);
		panel.add(cd);
		cd.setBackground(Color.WHITE);
		cd.setForeground(new Color(255, 255, 255));
		cd.setFont(new Font("Tahoma", Font.BOLD, 21));
		
		layoutStudents();
		
	}
	
	void layoutStudents() {
		studentlist = DB.searchStudent("%"+textField.getText()+"%");
		studentpanel.removeAll();
		int row = 0, column = 0;
	    for (Student student : studentlist) {
	    	if(column == 2) {
	    		column = 0; row++;
	    	}
	       StudentPane itempane = new StudentPane(student);
	       GridBagConstraints gbc = new GridBagConstraints();
	       itempane.addMouseListener(new MouseAdapter() {
	    	   @Override
	    	   public void mouseClicked(MouseEvent e) {
	    		   clicked = student;
	    		   viewbtn.setEnabled(true);
	    		   image.setText("");
	    		   image.setIcon(clicked.getImage());
	    		   name.setText(student.getFullName());
	    	   }
	       });
	       gbc.gridx = column++;
	       gbc.gridy = row;
	       studentpanel.add(itempane,gbc);
	    }
	    studentpanel.revalidate();
	    studentpanel.repaint();
	    if(studentlist.size() == 0) {
			JOptionPane.showMessageDialog(this, "No Student Found!", "Error", 2);
		}
	  }

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == addstudentbtn) {
			AddStudent as = new AddStudent(this);
			as.setVisible(true);
		} else if(e.getSource() == viewbtn) {
			ViewStudent vs = new ViewStudent(clicked,this);
			vs.setVisible(true);
			viewbtn.setEnabled(false);
			image.setIcon(null);
			image.setText("No Student Selected");
			name.setText("");
		}
	}
}
