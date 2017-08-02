package library.admin;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import library.chat.Chat;

import java.sql.*;

@SuppressWarnings("serial")
public class Window extends JFrame implements ActionListener, KeyListener,Runnable {
	JPanel mainPanel, headingPanel, loginPanel, footerPanel;
	JLabel appTitle, juser, jpass, jfooter;
	JButton login, reset;
	JTextField userName;
	JPasswordField password;

	public Window(String title) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		// setLayout(new FlowLayout());

		mainPanel = new JPanel();

		headingPanel = new JPanel();
		loginPanel = new JPanel();
		footerPanel = new JPanel();

		mainPanel.setLayout(new GridLayout(3, 1));
		headingPanel.setLayout(new FlowLayout());
		loginPanel.setLayout(new GridLayout(3, 3, 10, 5));
		footerPanel.setLayout(new GridBagLayout());

		appTitle = new JLabel("Library Management System");
		juser = new JLabel("User Name: ");
		juser.setFont(new Font(Font.SERIF, 5, 20));
		jpass = new JLabel("Password: ");
		jpass.setFont(new Font(Font.SERIF, 5, 20));
		appTitle.setFont(new Font(Font.SERIF, 5, 50));
		jfooter = new JLabel("Bangabandhu Sheikh Mujibur Rahman Science and Technology University");
		jfooter.setFont(new Font(Font.SERIF, 5, 25));

		ImageIcon loginIcon = new ImageIcon("img\\login.png");
		login = new JButton("",loginIcon);
		login.addActionListener(this);
		ImageIcon resetIcon = new ImageIcon("img\\reset.png");
		reset = new JButton("Reset",resetIcon);
		reset.addActionListener(this);

		userName = new JTextField(10);
		userName.setToolTipText("email or username");
		userName.addKeyListener(this);
		password = new JPasswordField(10);
		password.setToolTipText("password");
		password.addKeyListener(this);

		headingPanel.add(appTitle);
		loginPanel.add(juser);
		loginPanel.add(userName);
		loginPanel.add(jpass);
		loginPanel.add(password);
		loginPanel.add(login);
		loginPanel.add(reset);

		footerPanel.add(jfooter);

		headingPanel.setBackground(Color.decode("#d4d4dc"));

		mainPanel.add(headingPanel);
		mainPanel.add(loginPanel);
		mainPanel.add(footerPanel);

		add(mainPanel);
	}
	@Override
	public void run() {
		System.out.println("run");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Library", "12345");
			PreparedStatement ps=con.prepareStatement("Select * from chatenable");
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				System.out.println("enif");
				if(rs.getInt(1)==1) {
					System.out.println("en");
					new Chat();
					PreparedStatement ps1=con.prepareStatement("Update chatenable set enable=? where 1");
					ps1.setInt(1, 0);
					ps1.executeUpdate();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals(login.getActionCommand())) {
			String una = userName.getText().toString().trim();
			String psw = new String(password.getPassword());
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Library", "12345");
				PreparedStatement ps = con.prepareStatement("select * from ADMIN where username=? and password=?");
				ps.setString(1, una);
				ps.setString(2, psw);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					TabedWindow tw = new TabedWindow();
					tw.setVisible(true);
					tw.setSize(900, 600);
					tw.setLocation(250, 100);
					tw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					setVisible(false);
					
				} else {
					JOptionPane.showMessageDialog(this, "Login Failed!!!");
					userName.setText("");
					password.setText("");
				}
			} catch (Exception e) {
				System.out.println(e);
				JOptionPane.showMessageDialog(this, "System Failed!!!");
			}
		}
		if (ae.getActionCommand().equals(reset.getActionCommand())) {
			userName.setText("");
			password.setText("");
		}
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		char ch = ke.getKeyChar();
		if (ch == '\n') {
			login.doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent ke) {

	}

	@Override
	public void keyTyped(KeyEvent ke) {

	}
}
