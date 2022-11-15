package app;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ViewStudent extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField fnameTf;
	private ArrayList<Subject> available_subs;
	private ArrayList<Subject> subs;
	private JTable table;
	private DefaultTableModel model = new DefaultTableModel();
	Object[] columns = {"ID","Subject Name","Offer No."};
	Object[] rowdata = new Object[3];
	private Student student;
	private JComboBox<Subject> subjectsCb;
	
	private JButton savebtn,addbtn,removebtn_student,removebtn_subject;
	@SuppressWarnings("rawtypes")
	private JComboBox courseCb,yearlevelCb;
	private int selected_sub = -1;
	
	void addSubjects() {
		model.setRowCount(0);
		subs = student.getSubjects();
		for(int x = 0; x < subs.size(); x++) {
			rowdata[0] = subs.get(x).getId();
			rowdata[1] = subs.get(x).getSubname();
			rowdata[2] = subs.get(x).getOffernum();
			model.addRow(rowdata);
		}
		table.setModel(model);
	}
	
	void addAvailableSubjects() {
		subjectsCb.removeAllItems();
		available_subs = DB.getAvailableSubjects(student);
		for(Subject s : available_subs) {
			subjectsCb.addItem(s);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ViewStudent(Student student) {
		available_subs = DB.getAvailableSubjects(student);
		this.student = student;
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
		courseCb = new JComboBox(ccs);
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
		yearlevelCb = new JComboBox(lvls);
		yearlevelCb.setFont(new Font("Tahoma", Font.PLAIN, 17));
		yearlevelCb.setSelectedIndex(student.getYearLevel()-1);
		yearlevelCb.setMaximumRowCount(4);
		yearlevelCb.setBounds(221, 148, 207, 30);
		contentPane.add(yearlevelCb);
		
		savebtn = new JButton("SAVE");
		savebtn.addActionListener(this);
		savebtn.setBounds(325, 189, 89, 23);
		contentPane.add(savebtn);
		
		removebtn_student = new JButton("REMOVE");
		removebtn_student.addActionListener(this);
		removebtn_student.setBounds(221, 189, 89, 23);
		contentPane.add(removebtn_student);
		
		subjectsCb = new JComboBox<>();
		subjectsCb.setFont(new Font("Tahoma", Font.PLAIN, 18));
		subjectsCb.setBounds(32, 444, 168, 40);
		addAvailableSubjects();
		contentPane.add(subjectsCb);
		
		addbtn = new JButton("ADD");
		addbtn.setBounds(210, 456, 89, 23);
		addbtn.addActionListener(this);
		contentPane.add(addbtn);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 223, 362, 210);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setDefaultEditor(Object.class, null);
		table.setFont(new Font("Tahoma", Font.PLAIN, 20));
		table.setRowHeight(30);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selected_sub = table.getSelectedRow();
			}
		});
		scrollPane.setViewportView(table);
		model.setColumnIdentifiers(columns);
		
		addSubjects();
		
		removebtn_subject = new JButton("REMOVE");
		removebtn_subject.setBounds(309, 456, 89, 23);
		removebtn_subject.addActionListener(this);
		contentPane.add(removebtn_subject);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == savebtn) {
			if(fnameTf.getText().length() < 5) {
				JOptionPane.showMessageDialog(this, "Missing Fields!", "Error", 2);
				return;
			}
			student.setFullname(fnameTf.getText());
			student.setCourse((String) courseCb.getSelectedItem());
			student.setYearLevel(Integer.parseInt((String) yearlevelCb.getSelectedItem()));
			DB.editStudent(student);
			JOptionPane.showMessageDialog(this, "Successfuly Edited!", "Success", 1);
		} else if (e.getSource() == removebtn_student) {
			return;
		} else if (e.getSource() == addbtn) {
			Subject selected = (Subject) subjectsCb.getSelectedItem();
			DB.insertStudentSubject(student.getId(),selected.getId());
			addSubjects();
			addAvailableSubjects();
			JOptionPane.showMessageDialog(this, "Successfuly Added!", "Success", 1);
		} else if (e.getSource() == removebtn_subject) {
			if(selected_sub == -1) {
				JOptionPane.showMessageDialog(this, "Select a Subject!", "Error", 2);
				return;
			}
			Subject selected = subs.get(selected_sub);
			DB.removeStudentSubject(student, selected);
			addSubjects();
			addAvailableSubjects();
			JOptionPane.showMessageDialog(this, "Successfuly Removed!", "Success", 1);
			selected_sub = -1;
		}
		
	}
}
