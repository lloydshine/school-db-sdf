package app;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Graphics2D;

public class AddStudent extends JFrame implements ActionListener {
	private static final long serialVersionUID = -6761136545409416015L;
	private JPanel contentPane;
	private ImageIcon image;
	private JButton insertimg,addstudent;
	private JTextField fnameTf;
	private JLabel lbl_img;
	@SuppressWarnings("rawtypes")
	private JComboBox coursest,yrlvlst;
	App a;
	
	String filename;
	private JTextField lnameTf;

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AddStudent(App a) {
		this.a = a;
		setTitle("ADD STUDENT");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(387, 529);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Firstname");
		lblNewLabel.setBounds(55, 203, 57, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Course: ");
		lblNewLabel_1.setBounds(55, 323, 57, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Year level: ");
		lblNewLabel_2.setBounds(214, 323, 71, 14);
		contentPane.add(lblNewLabel_2);
		
		String[] lvls = {"1","2","3","4"};
		yrlvlst = new JComboBox(lvls);
		yrlvlst.setFont(new Font("Tahoma", Font.PLAIN, 16));
		yrlvlst.setBounds(214, 338, 103, 32);
		contentPane.add(yrlvlst);
		
		String[] ccs = {"BSIT","BSCS"};
		coursest = new JComboBox(ccs);
		coursest.setFont(new Font("Tahoma", Font.PLAIN, 16));
		coursest.setBounds(55, 338, 114, 33);
		contentPane.add(coursest);
		
		insertimg = new JButton("Insert image");
		insertimg.addActionListener(this);
		insertimg.setBounds(200, 73, 119, 23);
		contentPane.add(insertimg);
		
		image = new ImageIcon("./images/none.jpg");
		Image i = image.getImage(); // transform it 
		Image newimg = i.getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		image = new ImageIcon(newimg);  // transform it back
		
		lbl_img = new JLabel();
		lbl_img.setIcon(image);
		lbl_img.setBounds(35, 11, 158, 155);
		contentPane.add(lbl_img);
		
		addstudent = new JButton("Add Student");
		addstudent.addActionListener(this);
		addstudent.setBounds(111, 399, 143, 33);
		contentPane.add(addstudent);
		
		fnameTf = new JTextField();
		fnameTf.setFont(new Font("Tahoma", Font.PLAIN, 17));
		fnameTf.setBounds(55, 218, 262, 39);
		contentPane.add(fnameTf);
		fnameTf.setColumns(10);
		
		JLabel lblLastname = new JLabel("Lastname");
		lblLastname.setBounds(55, 258, 57, 14);
		contentPane.add(lblLastname);
		
		lnameTf = new JTextField();
		lnameTf.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lnameTf.setColumns(10);
		lnameTf.setBounds(55, 273, 262, 39);
		contentPane.add(lnameTf);
	}
	
	public void save() throws IOException{
		Image img = image.getImage();
		BufferedImage bi = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = bi.createGraphics();
		g2.drawImage(img, 0, 0, null);
		g2.dispose();
		Student newstudent = a.studentlist.get(a.studentlist.size()-1);
		String img_src = newstudent.getId()+".jpg";
		File currentDir = new File("./images/"+img_src);
		if(filename == null) {
			filename = "./images/none.jpg";
		}
		File sourcefile = new File(filename);
		Files.copy(sourcefile.toPath(), currentDir.toPath());
        
    }
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==insertimg) {
			JFileChooser chooser = new JFileChooser();
			chooser.showOpenDialog(null);
			File f = chooser.getSelectedFile();
			if(f == null) {
				return;
			}
			filename = f.getAbsolutePath();
			ImageIcon icon = new ImageIcon(filename);
			Image newimage = icon.getImage().getScaledInstance(lbl_img.getWidth(),lbl_img.getHeight(),java.awt.Image.SCALE_SMOOTH);
			icon = new ImageIcon(newimage);
			lbl_img.setIcon(icon);
		}else if(e.getSource()==addstudent) {
			if(fnameTf.getText().length()<4 || lnameTf.getText().length()<4) {
				JOptionPane.showMessageDialog(this, "Invalid Name", "Error", 2);
				return;
			}
			String fname = fnameTf.getText();
			String lname = lnameTf.getText();
			String course = coursest.getSelectedItem().toString();
			String yrlevel = yrlvlst.getSelectedItem().toString();
			int yearlevel = Integer.parseInt(yrlevel);
			if(DB.searchDuplicateStudent(fname + lname)) {
				JOptionPane.showMessageDialog(this, "Duplicate Student", "Error", 2);
				return;
			}
			DB.insertStudent(fname,lname,course,yearlevel);
			fnameTf.setText("");
			lnameTf.setText("");
			JOptionPane.showMessageDialog(this, "Student Added!", "SUCCESS",1);
			a.layoutStudents();
			try {
				save();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			a.layoutStudents();
			
			image = new ImageIcon("./images/none.jpg");
			Image i = image.getImage(); // transform it 
			Image newimg = i.getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			image = new ImageIcon(newimg);  // transform it back
			lbl_img.setIcon(image);
		}
	}
}
