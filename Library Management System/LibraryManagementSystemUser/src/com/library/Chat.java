package com.library;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Servlet implementation class Chat
 */
@WebServlet("/Chat")
public class Chat extends HttpServlet implements ActionListener, Runnable {
	private static final long serialVersionUID = 1L;
	JButton b1, b2;
	JTextField tf1, tf;
	JTextArea ta;
	Socket s;
	PrintWriter pw;
	static BufferedReader br;
	Thread t;

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == b1) {
			ta.append("[YOU #]: " + tf.getText() + "\n");
			pw.println("[CLIENT #]: " + tf.getText());
			tf.setText("");
		} else if (ae.getSource() == b2) {
			tf1.setText("CHAT DISABLED");
			pw.println("BYE");

			try {
				s.close();
			} catch (Exception e) {
			}
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				String s1 = br.readLine();
				ta.append(s1 + "\n");
				if (s1.equals("BYE")) {
					break;
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Chat() {
		super();
		JFrame f = new JFrame("CLIENT");
		f.setLayout(new FlowLayout());
		b1 = new JButton("Send");
		b1.addActionListener(this);
		b2 = new JButton("Close");
		b2.addActionListener(this);
		tf1 = new JTextField(15);
		tf = new JTextField(15);
		ta = new JTextArea(12, 20);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.add(tf1);
		f.add(ta);
		f.add(tf);
		f.add(b1);
		f.add(b2);

		try {
			s = new Socket("192.168.56.101", 7000);
			tf1.setText("CHAT ENABLED");
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw = new PrintWriter(s.getOutputStream(), true);
		} catch (Exception e) {
		}

		t = new Thread(this);
		t.start();
		f.setSize(300, 350);
		f.setVisible(true);
		f.setLocation(600, 200);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Library", "12345");

			PreparedStatement ps1=con.prepareStatement("Update chatenable set enable=?");
			ps1.setInt(1, 1);
			ps1.executeUpdate();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
