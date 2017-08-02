package library.admin;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class ReturnBook extends JFrame implements ActionListener,FocusListener, DocumentListener, PropertyChangeListener{
	HintTextField jbook_id,juser_id,jbook_id1,juser_id1;
	JButton jreturn,jreturn1,back;
	JPanel headerPanel,footerPanel,mainPanel;
	JLabel jBorrowDate,jtext,jfine,juser,jid;
	public ReturnBook(String title) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(3, 1));
		setResizable(false);
		
		headerPanel = new JPanel();
		headerPanel.setLayout(null);
		footerPanel = new JPanel();
		mainPanel = new JPanel();
		jbook_id = new HintTextField("Book ID",20);
		//jbook_id.addFocusListener(this);
		//jreturn.grabFocus();
		
		//jbook_id.setToolTipText("Book ID");
		juser_id = new HintTextField("User ID",20);
		
		juser_id.setToolTipText("User ID");
		jreturn = new JButton("Check");
		jreturn.addActionListener(this);
		jreturn.grabFocus();
		
		ImageIcon backIcon = new ImageIcon("img\\back.png");
		back = new JButton("",backIcon);
		back.setBounds(840, 0, 50, 50);
		back.addActionListener(this);
		
		jBorrowDate = new JLabel("");
		jfine = new JLabel("");
		
		jbook_id1 = new HintTextField("",20);
		juser_id1 = new HintTextField("",20);
		jreturn1 = new JButton("Return");
		jreturn1.addActionListener(this);
		
		jid = new JLabel("Book ID");
		jid.setBounds(200, 5, 200, 30);
		juser = new JLabel("Student ID");
		juser.setBounds(420, 5, 200, 30);
		headerPanel.add(jid);
		headerPanel.add(juser);
		
		headerPanel.add(jbook_id);
		jbook_id.setBounds(200, 50, 200, 30);
		headerPanel.add(juser_id);
		juser_id.setBounds(420, 50, 200, 30);
		headerPanel.add(jreturn);
		jreturn.setBounds(650, 50, 70, 30);
		headerPanel.add(back);
		
		mainPanel.add(jfine);
		
		footerPanel.add(jbook_id1);
		footerPanel.add(juser_id1);
		footerPanel.add(jreturn1);
		
		add(headerPanel);
		add(mainPanel);
		add(footerPanel);
		footerPanel.setVisible(false);
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getActionCommand().equals(back.getActionCommand())) {
			TabedWindow tw = new TabedWindow();
			tw.setVisible(true);
			tw.setSize(900, 600);
			tw.setLocation(250, 100);
			setVisible(false);
		}
		if(ae.getActionCommand().equals(jreturn.getActionCommand())) {
			String bookText = jbook_id.getText().toString().trim();
			Integer bookID = Integer.parseInt(bookText);
			String userID = juser_id.getText().toString().trim();
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Library", "12345");
				PreparedStatement ps = con.prepareStatement("select * from BORROW where USER_ID=? and BOOK_ID=?");
				ps.setString(1, userID);
				ps.setInt(2, bookID);
				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					JOptionPane.showMessageDialog(this, "Found");
					long _borrow = rs.getLong(3);
					long _return = rs.getLong(4);
					Date date = new Date();
					long _today = date.getTime();
					if(_today>_return) {
						long due = _today - _return;
						int fine = (int)(due/(60*60*24*1000))*5;
						jfine.setText("Fine is : "+fine+" Taka");
						
					}else {
						jfine.setText("No fine.");
					}
					footerPanel.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(this, "Invalid book id or user id");
				}
			}catch(Exception e) {
				System.out.println(e);
			}
		}
		if(ae.getActionCommand().equals(jreturn1.getActionCommand())) {
			int copy=0;
			String bookText = jbook_id1.getText().toString().trim();
			Integer bookID = Integer.parseInt(bookText);
			String userID = juser_id1.getText().toString().trim();
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Library", "12345");
				PreparedStatement ps = con.prepareStatement("delete from BORROW where USER_ID=? and BOOK_ID=?");
				ps.setString(1, userID);
				ps.setInt(2, bookID);
				int delete = ps.executeUpdate();
				if(delete!=0) {
					//JOptionPane.showMessageDialog(this, "Successfully Returned.");
					PreparedStatement ps2 = con.prepareStatement("select * from BOOK_DETAILS where BOOK_ID=?");
					ps2.setInt(1, bookID);
					ResultSet rs2 = ps2.executeQuery();
					if(rs2.next()) {
						copy = rs2.getInt(3);
					}else {
						copy=0;
					}
					PreparedStatement ps3 = con.prepareStatement("update BOOK_DETAILS set AVAILABLE_COPY=? where BOOK_ID=?");
					ps3.setInt(1, copy+1);
					ps3.setInt(2, bookID);
					int update = ps3.executeUpdate();
					if(update!=0) {
						JOptionPane.showMessageDialog(this, "Successfully Returned");
						footerPanel.setVisible(false);
						mainPanel.setVisible(false);
					}else {
						JOptionPane.showMessageDialog(this, "Server error!");
					}
				}else {
					JOptionPane.showMessageDialog(this, "Invalid book id or user id !");
					footerPanel.setVisible(true);
				}
			}catch(Exception e) {
				System.out.println(e);
			}
		}
	}
	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void focusGained(FocusEvent e) {
		
	}
	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

}
