package app;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField passwordTf;
	private JTextField usernameTf;
	private JButton loginbtn;
	
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
		setSize(798, 616);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		passwordTf = new JPasswordField();
		passwordTf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		passwordTf.setToolTipText("Password");
		passwordTf.setBounds(227, 319, 331, 53);
		contentPane.add(passwordTf);
		
		usernameTf = new JTextField();
		usernameTf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		usernameTf.setToolTipText("Username");
		usernameTf.setBounds(227, 221, 331, 53);
		contentPane.add(usernameTf);
		usernameTf.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Username:");
		lblNewLabel.setBounds(227, 203, 63, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(227, 301, 63, 14);
		contentPane.add(lblPassword);
		
		loginbtn = new JButton("Login");
		loginbtn.setBounds(318, 412, 147, 44);
		contentPane.add(loginbtn);
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
