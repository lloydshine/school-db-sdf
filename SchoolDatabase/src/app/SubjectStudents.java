package app;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;

public class SubjectStudents extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultTableModel model = new DefaultTableModel();
	Object[] columns = {"ID","Fullname","Course","YearLevel"};
	Object[] rowdata = new Object[4];
	private JTable table;
	private ArrayList<Student> students;
	private JLabel lblNewLabel;
	private JLabel subname;
	private JLabel lblTotalStudents;
	private JLabel scount;

	public SubjectStudents(Subject sub) {
		students = DB.getSubjectStudents(sub);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(740, 100, 593, 318);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Subjects Table");
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 61, 577, 219);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setRowSelectionAllowed(false);
		table.setDefaultEditor(Object.class, null);
		table.setFont(new Font("Tahoma", Font.PLAIN, 20));
		table.setRowHeight(30);
		
		scrollPane.setViewportView(table);
		model.setColumnIdentifiers(columns);
		
		addValues();
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		scrollPane.setViewportView(table);
		
		lblNewLabel = new JLabel("Subject:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 22, 83, 25);
		contentPane.add(lblNewLabel);
		
		subname = new JLabel(sub.getSubname() + "-" + sub.getOffernum());
		subname.setFont(new Font("Tahoma", Font.PLAIN, 20));
		subname.setBounds(103, 22, 209, 25);
		contentPane.add(subname);
		
		lblTotalStudents = new JLabel("Total Students:");
		lblTotalStudents.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTotalStudents.setBounds(322, 22, 143, 25);
		contentPane.add(lblTotalStudents);
		
		scount = new JLabel(Integer.toString(students.size()));
		scount.setFont(new Font("Tahoma", Font.PLAIN, 20));
		scount.setBounds(472, 22, 68, 25);
		contentPane.add(scount);
	}
	
	void addValues() {
		model.setRowCount(0);
		//Collections.sort(App.styles, Comparator.comparing(HairStyle::getId));
		for(int x = 0; x < students.size(); x++) {
			rowdata[0] = students.get(x).getId();
			rowdata[1] = students.get(x).getFullName();
			rowdata[2] = students.get(x).getCourse();
			rowdata[3] = students.get(x).getYearLevel();
			model.addRow(rowdata);
		}
		table.setModel(model);
	}

}
