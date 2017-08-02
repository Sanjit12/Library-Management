package library.admin;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class BorrowBook extends JFrame implements ActionListener,KeyListener{
	Integer data=0;
	String dataText="";
	JPanel headerPanel,mainPanel,footerPanel;
	JTextField bookID,userID;
	JLabel bookLabel,userLabel,jbookName,jbookWriter,jbookID,jbookName1,jbookWriter1,jbookID1;
	JButton jBorrow,back;
	public BorrowBook(String title) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(3, 1));
		setResizable(false);
		
		headerPanel = new JPanel();
		headerPanel.setLayout(new FlowLayout());
		mainPanel = new JPanel();
		mainPanel.setLayout(new FlowLayout());
		footerPanel = new JPanel();
		footerPanel.setLayout(new GridLayout(3,2));
		
		bookID = new JTextField(30);
		bookID.addKeyListener(this);
		bookLabel = new JLabel("Book Availability (Book id)");
		
		jbookID =  new JLabel("Book ID : ");
		jbookName = new JLabel("Book Name : ");
		jbookWriter = new JLabel("Book Writer : ");
		jbookID1 =  new JLabel("");
		jbookName1 = new JLabel("");
		jbookWriter1 = new JLabel("");	
		
		userID = new JTextField(30);
		userID.setToolTipText("Enter user ID");
		jBorrow = new JButton("Borrow Book");
		jBorrow.addActionListener(this);
		
		ImageIcon backIcon = new ImageIcon("img\\back.png");
		back = new JButton("",backIcon);
		back.setBounds(840, 0, 50, 50);
		back.addActionListener(this);
		
		//userID.setVisible(false);
		//jBorrow.setVisible(false);
		
		headerPanel.add(bookLabel);
		bookLabel.setBounds(220, 5, 200, 30);
		headerPanel.add(bookID);
		bookID.setBounds(430, 5, 200, 30);
		headerPanel.add(back);
		headerPanel.setLayout(null);
		
		footerPanel.add(jbookID);
		footerPanel.add(jbookID1);
		footerPanel.add(jbookName);
		footerPanel.add(jbookName1);
		footerPanel.add(jbookWriter);
		footerPanel.add(jbookWriter1);
		
		mainPanel.add(userID);
		mainPanel.add(jBorrow);
		
		footerPanel.setVisible(false);
		mainPanel.setVisible(false);
		
		add(headerPanel);
		add(footerPanel);
		add(mainPanel);
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		int borrowedBook=0;
		if(ae.getActionCommand().equals(back.getActionCommand())) {
			TabedWindow tw = new TabedWindow();
			tw.setVisible(true);
			tw.setSize(900, 600);
			tw.setLocation(250, 100);
			setVisible(false);
		}
		int copy=0;
		if(ae.getActionCommand().equals(jBorrow.getActionCommand())) {
			String id = userID.getText().toString().trim();
			//DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			//Date date = new Date();
			//System.out.println(date.toString());
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Library", "12345");
				PreparedStatement ps = con.prepareStatement("insert into BORROW values(?,?,?,?)");
				ps.setString(1, id);
				ps.setInt(2, data);
				//java.util.Date date = new java.util.Date();
				Date date = new Date();
				long _borrow = date.getTime();
				long _return = _borrow + 60*60*24*1000*7;
				//System.out.println(val);
				ps.setLong(3, _borrow);
				ps.setLong(4, _return);
				ps.executeQuery();
				String bd=DateFormat.getDateInstance().format(new Date(_borrow));
				
				System.out.println(bd);
				String bd2=DateFormat.getDateInstance().format(new Date(_return));
				//System.out.println(bd2);
				//JOptionPane.showMessageDialog(this, "Book Borrowed by "+id);
				PreparedStatement ps11 = con.prepareStatement("select count(USER_ID) from BORROW where USER_ID=?");
				ps11.setString(1, id);
				ResultSet rs11= ps11.executeQuery();
				if(rs11.next()){
					borrowedBook = rs11.getInt(1);	
				}else {
					//JOptionPane.showMessageDialog(this, "System Error!");
					System.out.println("no valid id");
				}
				
				
				if(borrowedBook<=5) {
					PreparedStatement ps2 = con.prepareStatement("select * from BOOK_DETAILS where BOOK_ID=?");
					ps2.setInt(1, data);
					ResultSet rs2 = ps2.executeQuery();
					if(rs2.next()) {
						copy = rs2.getInt(3);
					}else {
						copy=0;
					}
					PreparedStatement ps3 = con.prepareStatement("update BOOK_DETAILS set AVAILABLE_COPY=? where BOOK_ID=?");
					ps3.setInt(1, copy-1);
					ps3.setInt(2, data);
					int update = ps3.executeUpdate();
					if(update!=0) {
						JOptionPane.showMessageDialog(this, "Successfully Borrowed by"+id);
					}else {
						JOptionPane.showMessageDialog(this, "Server error!");
					}
				}else
				{
					JOptionPane.showMessageDialog(this, "Please return Previous Book. You already boorowed 5 book");
				}
				
			}catch(Exception e) {
				System.out.println(e);
			}
		}
	}
	@Override
	public void keyPressed(KeyEvent ke) {
		char ch = ke.getKeyChar();
		if (ch == '\n') {
			//System.out.println("ll");
			dataText = bookID.getText().toString().trim();
			data = Integer.parseInt(dataText);
			//System.out.println(data);
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Library", "12345");
				PreparedStatement ps = con.prepareStatement("select * from BOOK_DETAILS where BOOK_ID=?");
				ps.setInt(1, data);
				ResultSet rs =ps.executeQuery();
				if(rs.next()) {
					int available = rs.getInt(3);
					if(available>=4) {
						JOptionPane.showMessageDialog(this,"This Book is available!");
						PreparedStatement ps2 = con.prepareStatement("select * from BOOKS where BOOK_ID=?");
						ps2.setInt(1, data);
						ResultSet rs2 =ps2.executeQuery();
						if(rs2.next()) {
							footerPanel.setVisible(true);
							jbookID1.setText(dataText);
							jbookName1.setText(rs2.getString(2));
							jbookWriter1.setText(rs2.getString(4));
							mainPanel.setVisible(true);
							
						}else {
							JOptionPane.showMessageDialog(this,"System Error!!");
						}
					}else {
						JOptionPane.showMessageDialog(this,"Not Available!!");
					}
					
				}else {
					mainPanel.setVisible(false);
					footerPanel.setVisible(false);
					JOptionPane.showMessageDialog(this,"Invalid Book ID.");
				}
			}catch(Exception e) {
				System.out.println(e);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent ke) {

	}

	@Override
	public void keyTyped(KeyEvent ke) {

	}

}
