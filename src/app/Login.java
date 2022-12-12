package app;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class Login extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField passwordTf;
	private JTextField usernameTf;
	private JButton loginbtn;
	private JLabel logoimg;
	private ImageIcon image;
	private JPanel panel_1;
	private JPanel panel_2;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(369, 616);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		passwordTf = new JPasswordField();
		passwordTf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		passwordTf.setToolTipText("Password");
		passwordTf.setBounds(61, 357, 245, 44);
		contentPane.add(passwordTf);
		
		usernameTf = new JTextField();
		usernameTf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		usernameTf.setToolTipText("Username");
		usernameTf.setBounds(61, 259, 245, 44);
		contentPane.add(usernameTf);
		usernameTf.setColumns(10);
		
		loginbtn = new JButton("Login");
		loginbtn.setBounds(102, 436, 147, 44);
		contentPane.add(loginbtn);
		
		image = new ImageIcon("./images/logo.png");
		Image i = image.getImage(); // transform it 
		Image newimg = i.getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		image = new ImageIcon(newimg);
		
		logoimg = new JLabel();
		logoimg.setIcon(image);
		logoimg.setBounds(102, 0, 267, 153);
		contentPane.add(logoimg);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 204, 51));
		panel.setBounds(10, 164, 332, 57);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("College Of Computer Studies \r\nDatabase");
		lblNewLabel_1.setBounds(10, 11, 319, 35);
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Trebuchet MS", Font.BOLD, 18));
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 204, 51));
		panel_1.setBounds(10, 229, 332, 337);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPassword.setBounds(130, 102, 78, 14);
		panel_1.add(lblPassword);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(130, 11, 78, 14);
		panel_1.add(lblNewLabel);
		
		panel_2 = new JPanel();
		panel_2.setBackground(new Color(0, 204, 51));
		panel_2.setBounds(10, 0, 332, 153);
		contentPane.add(panel_2);
		loginbtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == loginbtn) {
			String username = usernameTf.getText();
			String password = new String(passwordTf.getPassword());
			if(username.equals("Admin") && password.equals("admin123")) {
				this.dispose();
				App main = new App();
				main.setVisible(true);
				JOptionPane.showMessageDialog(main, "Successfully Logged In!", "Success", 1);
			} else {
				JOptionPane.showMessageDialog(this, "Incorrect Username/Password", "Error", 2);
			}
		}
		
	}
}
