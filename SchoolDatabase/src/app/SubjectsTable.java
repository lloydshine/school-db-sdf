package app;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTable;

public class SubjectsTable extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField subnameTf;
	private JTextField offernumTf;
	private JTable table;
	private JButton addbtn, removebtn, savebtn;
	private DefaultTableModel model = new DefaultTableModel();
	Object[] columns = {"ID","Subject Name","Offer No."};
	Object[] rowdata = new Object[3];
	
	ArrayList<Subject> subjects = DB.getSubjects();

	public SubjectsTable() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(740, 100, 378, 550);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Subjects Table");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 362, 261);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setDefaultEditor(Object.class, null);
		table.setFont(new Font("Tahoma", Font.PLAIN, 20));
		table.setRowHeight(30);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selected = table.getSelectedRow();
				Subject sub = subjects.get(selected);
				subnameTf.setText(sub.getSubname());
				offernumTf.setText(sub.getOffernum());
			}
		});
		scrollPane.setViewportView(table);
		model.setColumnIdentifiers(columns);
		
		addValues();
		scrollPane.setViewportView(table);
		
		subnameTf = new JTextField();
		subnameTf.setColumns(10);
		subnameTf.setBounds(132, 303, 141, 29);
		contentPane.add(subnameTf);
		
		offernumTf = new JTextField();
		offernumTf.setColumns(10);
		offernumTf.setBounds(132, 370, 141, 29);
		contentPane.add(offernumTf);
		
		JLabel l2 = new JLabel("Subject Name:");
		l2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		l2.setBounds(23, 308, 114, 14);
		contentPane.add(l2);
		
		JLabel l3 = new JLabel("Offer Number:");
		l3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		l3.setBounds(23, 368, 106, 29);
		contentPane.add(l3);
		
		addbtn = new JButton("ADD");
		addbtn.setBounds(23, 456, 89, 23);
		contentPane.add(addbtn);
		addbtn.addActionListener(this);
		
		savebtn = new JButton("SAVE");
		savebtn.setBounds(132, 456, 76, 23);
		contentPane.add(savebtn);
		savebtn.addActionListener(this);
		
		removebtn = new JButton("REMOVE");
		removebtn.setBounds(229, 456, 89, 23);
		contentPane.add(removebtn);
		removebtn.addActionListener(this);
	}
	
	void addValues() {
		model.setRowCount(0);
		//Collections.sort(App.styles, Comparator.comparing(HairStyle::getId));
		for(int x = 0; x < subjects.size(); x++) {
			rowdata[0] = subjects.get(x).getId();
			rowdata[1] = subjects.get(x).getSubname();
			rowdata[2] = subjects.get(x).getOffernum();
			model.addRow(rowdata);
		}
		table.setModel(model);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String subname = subnameTf.getText();
		String offernum= offernumTf.getText();
		
		int selected = table.getSelectedRow();
		Subject sub = null;
		if (e.getSource() == addbtn) {
			if(subname.length() == 0 || offernum.length() == 0) {
				JOptionPane.showMessageDialog(this, "Missing Fields!", "Error", 2);
			} else {
				DB.insertSubject(subname,offernum);
				subjects = DB.getSubjects();
				Subject newsub = subjects.get(subjects.size()-1);
				Object[] rowdata = new Object[3];
				rowdata[0] = newsub.getId();
				rowdata[1] = newsub.getSubname();
				rowdata[2] = newsub.getOffernum();
				model.addRow(rowdata);
				subnameTf.setText("");
				offernumTf.setText("");
			}
		} else if (e.getSource() == removebtn) {
			if(selected == -1) {
				JOptionPane.showMessageDialog(this, "Select an Item!", "Error", 2);
				return;
			}
			sub = subjects.get(selected);
			subjects.remove(selected);
			addValues();
			subnameTf.setText("");
			offernumTf.setText("");
			DB.removeSubject(sub);
	   } else if (e.getSource() == savebtn) {
		   if(selected == -1) {
				JOptionPane.showMessageDialog(this, "Select an Item!", "Error", 2);
				return;
		   } else {
			   if(subname.length() == 0 || offernum.length() == 0) {
					JOptionPane.showMessageDialog(this, "Missing Fields!", "Error", 2);
			   } else {
				   	sub = subjects.get(selected);
					sub.setSubject_name(subname);
					sub.setOffernum(offernum);
					addValues();
					subnameTf.setText("");
					offernumTf.setText("");
					DB.editSubject(sub);
			   }
		   }
	   }
	}
}
