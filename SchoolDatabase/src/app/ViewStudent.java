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

public class ViewStudent extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField fnameTf;
	ArrayList<Subject> available_subs = DB.getSubjects();
	private JTable table;
	private DefaultTableModel model = new DefaultTableModel();
	Object[] columns = {"ID","Subject Name","Offer No."};
	Object[] rowdata = new Object[3];

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
		subjectsCb.setBounds(32, 444, 168, 40);
		contentPane.add(subjectsCb);
		
		JButton addbtn = new JButton("ADD");
		addbtn.setBounds(210, 456, 89, 23);
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
				int selected = table.getSelectedRow();
			}
		});
		scrollPane.setViewportView(table);
		model.setColumnIdentifiers(columns);
		
		Object[] rowdata = new Object[3];
		rowdata[0] = "1";
		rowdata[1] = "Calculus";
		rowdata[2] = "832568";
		model.addRow(rowdata);
		table.setModel(model);
		
		JButton removebtn_1 = new JButton("REMOVE");
		removebtn_1.setBounds(309, 456, 89, 23);
		contentPane.add(removebtn_1);
	}
}
