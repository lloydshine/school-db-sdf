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
import javax.imageio.ImageIO;
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
	private JTextField fullnamest;
	private JLabel lbl_img;
	@SuppressWarnings("rawtypes")
	private JComboBox coursest,yrlvlst;
	App a;
	
	String filename;

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AddStudent(App a) {
		this.a = a;
		setTitle("ADD STUDENT");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 387, 422);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Fullname: ");
		lblNewLabel.setBounds(55, 203, 57, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Course: ");
		lblNewLabel_1.setBounds(55, 264, 57, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Year level: ");
		lblNewLabel_2.setBounds(214, 264, 71, 14);
		contentPane.add(lblNewLabel_2);
		
		String[] lvls = {"1","2","3","4"};
		yrlvlst = new JComboBox(lvls);
		yrlvlst.setFont(new Font("Tahoma", Font.PLAIN, 16));
		yrlvlst.setBounds(214, 279, 103, 32);
		contentPane.add(yrlvlst);
		
		String[] ccs = {"BSIT","BSCS"};
		coursest = new JComboBox(ccs);
		coursest.setFont(new Font("Tahoma", Font.PLAIN, 16));
		coursest.setBounds(55, 279, 114, 33);
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
		addstudent.setBounds(111, 340, 143, 33);
		contentPane.add(addstudent);
		
		fullnamest = new JTextField();
		fullnamest.setFont(new Font("Tahoma", Font.PLAIN, 17));
		fullnamest.setBounds(55, 218, 262, 39);
		contentPane.add(fullnamest);
		fullnamest.setColumns(10);
	}
	
	public void save() throws IOException{
		Image img = image.getImage();
		BufferedImage bi = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = bi.createGraphics();
		g2.drawImage(img, 0, 0, null);
		g2.dispose();
		Student newstudent = a.studentlist.get(a.studentlist.size()-1);
		String img_src = newstudent.getId()+".jpg";
		System.out.println(img_src);
		File currentDir = new File("./images/"+img_src);
		if(filename == null) {
			filename = "./images/none.jpg";
		}
		System.out.println(filename);
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
			if(fullnamest.getText().length()<4) {
				JOptionPane.showMessageDialog(this, "Invalid Name", "Error", 2);
				return;
			}
			String fname = fullnamest.getText();
			String course = coursest.getSelectedItem().toString();
			String yrlevel = yrlvlst.getSelectedItem().toString();
			int yearlevel = Integer.parseInt(yrlevel);
			DB.insertStudent(fname, course, yearlevel);
			JOptionPane.showMessageDialog(this, "Student Added!", "SUCCESS",1);
			a.layoutStudents();
			try {
				save();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			a.layoutStudents();
			fullnamest.setText("");
		}
	}
}
