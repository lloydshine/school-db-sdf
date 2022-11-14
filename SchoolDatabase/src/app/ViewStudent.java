package app;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class ViewStudent extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField fnameTf;
	ArrayList<Subject> available_subs = DB.getSubjects();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ViewStudent(Student student) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(469, 566);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel image = new JLabel(student.getImage());
		image.setHorizontalAlignment(SwingConstants.CENTER);
		image.setBounds(23, 53, 188, 159);
		getContentPane().add(image);
		
		fnameTf = new JTextField();
		fnameTf.setFont(new Font("Tahoma", Font.PLAIN, 16));
		fnameTf.setText(student.getFullName());
		fnameTf.setBounds(221, 53, 207, 40);
		contentPane.add(fnameTf);
		fnameTf.setColumns(10);
		
		String[] ccs = {"BSIT","BSCS"};
		JComboBox courseCb = new JComboBox(ccs);
		courseCb.setFont(new Font("Tahoma", Font.PLAIN, 16));
		int c = 0;
		if(student.getCourse().equals("BSCS")) {
			c = 1;
		}
		courseCb.setSelectedIndex(c);
		courseCb.setMaximumRowCount(2);
		courseCb.setBounds(221, 104, 207, 30);
		contentPane.add(courseCb);
		
		String[] lvls = {"1","2","3","4"};
		JComboBox yearlevelCb = new JComboBox(lvls);
		yearlevelCb.setFont(new Font("Tahoma", Font.PLAIN, 17));
		yearlevelCb.setSelectedIndex(student.getYearLevel()-1);
		yearlevelCb.setMaximumRowCount(4);
		yearlevelCb.setBounds(221, 148, 207, 30);
		contentPane.add(yearlevelCb);
		
		JButton savebtn = new JButton("SAVE");
		savebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fnameTf.getText().length() < 5) {
					return;
				}
				student.setFullname(fnameTf.getText());
				student.setCourse((String) courseCb.getSelectedItem());
				student.setYearLevel(Integer.parseInt((String) yearlevelCb.getSelectedItem()));
				DB.editStudent(student);
			}
		});
		savebtn.setBounds(325, 189, 89, 23);
		contentPane.add(savebtn);
		
		JButton removebtn = new JButton("REMOVE");
		removebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		removebtn.setBounds(221, 189, 89, 23);
		contentPane.add(removebtn);
		
		JComboBox<Subject> subjectsCb = new JComboBox<>();
		for(Subject s : available_subs) {
			subjectsCb.addItem(s);
		}
		subjectsCb.setFont(new Font("Tahoma", Font.PLAIN, 18));
		subjectsCb.setBounds(23, 444, 168, 40);
		contentPane.add(subjectsCb);
		
		JButton addbtn = new JButton("ADD");
		addbtn.setBounds(201, 453, 89, 23);
		contentPane.add(addbtn);
	}
}
