import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;

public class App extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private StudentPanel studentpanel;
	private Student clicked = null;
	
	private ArrayList<Student> studentlist;
	private JLabel image;
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
		
		JButton viewbtn = new JButton("VIEW");
		viewbtn.setBounds(34, 343, 127, 45);
		contentPane.add(viewbtn);
		
		layoutStudents();
		
	}
	
	void layoutStudents() {
		studentlist = DB.getStudents();
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
	    		   image.setIcon(clicked.getImage());
	    	   }
	       });
	       gbc.gridx = column++;
	       gbc.gridy = row;
	       studentpanel.add(itempane,gbc);
	    }
	  }
}
