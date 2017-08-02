package library.admin;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class EditBook2 extends JFrame implements ActionListener{
	int id=0;
	JPanel headerPanel,mainPanel;
	JTextField jsearchBox;
	JButton jsearch,jsave,jdelete,back;
	JLabel jbookName,jbookID,jbookWriter,jbookINBS,jbookPublisher,jbookDepartment,bookLabel;
	JTextArea bookName,bookID,bookWriter,bookINBS,bookPublisher,bookDepartment;
	public EditBook2(String title) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(3, 2));
		setResizable(false);
		
		
		headerPanel = new JPanel();
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(7, 2,0,7));
		
		
		
		jbookName = new JLabel("Book Name : ");
		jbookID = new JLabel("Book ID : ");
		jbookWriter = new JLabel("Writer : ");
		jbookINBS = new JLabel("INBS : ");
		jbookPublisher = new JLabel("Publisher : ");
		jbookDepartment = new JLabel("Department : ");
		
		bookName = new JTextArea();
		bookID = new JTextArea();
		bookWriter = new JTextArea();
		bookINBS = new JTextArea();
		bookPublisher = new JTextArea();
		bookDepartment = new JTextArea();
		
		ImageIcon backIcon = new ImageIcon("img\\back.png");
		back = new JButton("",backIcon);
		back.setBounds(842, 5, 50, 50);
		back.addActionListener(this);
		
		jsearch = new JButton("Search");
		jsearch.addActionListener(this);
		jsave = new JButton("Save");
		jsave.addActionListener(this);
		jdelete = new JButton("Delete");
		jdelete.addActionListener(this);
		
		jsearchBox = new JTextField(30);
		jsearchBox.setToolTipText("Enter Book ID");
		
		jsearchBox.setBounds(250, 5, 300, 50);
		jsearch.setBounds(580, 5, 80, 50);
		
		bookLabel = new JLabel("Enter Book ID");
		bookLabel.setBounds(150, 8, 245, 30);
		
		headerPanel.add(bookLabel);
		headerPanel.add(jsearchBox);
		headerPanel.add(jsearch);
		headerPanel.add(back);
		headerPanel.setLayout(null);
		
		mainPanel.add(jbookID);
		mainPanel.add(bookID);
		mainPanel.add(jbookName);
		mainPanel.add(bookName);
		mainPanel.add(jbookINBS);
		mainPanel.add(bookINBS);
		mainPanel.add(jbookWriter);
		mainPanel.add(bookWriter);
		mainPanel.add(jbookPublisher);
		mainPanel.add(bookPublisher);
		mainPanel.add(jbookDepartment);
		mainPanel.add(bookDepartment);
		mainPanel.add(jsave);
		mainPanel.add(jdelete);
		
		mainPanel.setVisible(false);

		add(headerPanel);
		add(mainPanel);
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
		if(ae.getActionCommand().equals(jsearch.getActionCommand())) {
			id = Integer.parseInt(jsearchBox.getText().toString().trim());
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Library", "12345");
				PreparedStatement ps = con.prepareStatement("select * from BOOKS where BOOK_ID=?");
				System.out.println(id);
				ps.setInt(1, id);
				ResultSet rs =ps.executeQuery();
				if(rs.next()) {
					mainPanel.setVisible(true);
					bookID.setText(""+rs.getInt(1));
					bookName.setText(rs.getString(2));
					bookINBS.setText(rs.getString(3));
					bookWriter.setText(rs.getString(4));
					bookPublisher.setText(rs.getString(5));
					bookDepartment.setText(rs.getString(6));
					
				}else {
					JOptionPane.showMessageDialog(this, "Not Found !");
				}
			}catch(Exception e) {
				System.out.println(e);
			}
		}
		if(ae.getActionCommand().equals(jsave.getActionCommand())) {
			Integer uid;
			String name,inbs,writer,publisher,department;
			uid = Integer.parseInt(bookID.getText().toString().trim());
			name = bookName.getText().toString().trim();
			inbs = bookINBS.getText().toString().trim();
			writer= bookWriter.getText().toString().trim();
			publisher = bookPublisher.getText().toString().trim();
			department = bookDepartment.getText().toString().trim();
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Library", "12345");
				PreparedStatement ps = con.prepareStatement("update BOOKS set BOOK_ID=?,BOOK_NAME=?,INBS=?,WRITER=?,PUBLISHER=?,DEPARTMENT=? where BOOK_ID=?");
				ps.setInt(1, uid);
				ps.setString(2, name);
				ps.setString(3, inbs);
				ps.setString(4, writer);
				ps.setString(5, publisher);
				ps.setString(6, department);
				ps.setInt(7, id);
				int rs =ps.executeUpdate();
				if(rs==1) {
					JOptionPane.showMessageDialog(this, "Updated");
					mainPanel.setVisible(false);
				}else {
					JOptionPane.showMessageDialog(this, "Not updated");
				}
			}catch(Exception e) {
				System.out.println(e);
			}
		}
		if(ae.getActionCommand().equals(jdelete.getActionCommand())) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Library", "12345");
				PreparedStatement ps = con.prepareStatement("delete from BOOKS where BOOK_ID=?");
				ps.setInt(1, id);
				System.out.println("id "+id);
				int rs =ps.executeUpdate();
				if(rs==1) {
					JOptionPane.showMessageDialog(this, "Deleted");
					mainPanel.setVisible(false);
				}else {
					JOptionPane.showMessageDialog(this, "Not Deleted");
				}
			}catch(Exception e) {
				System.out.println(e);
			}
		}
	}
}
