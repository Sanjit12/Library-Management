package library.admin;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class AddBook extends JFrame implements KeyListener,ActionListener{
	String name,inbs,writer,publisher,department;
	int id;
	JButton back,save,reset;
	JLabel jheader,jbookName,jbookID,jbookWriter,jbookINBS,jbookPublisher,jbookDepartment;
	JTextArea bookName,bookID,bookWriter,bookINBS,bookPublisher,bookDepartment;
	JComboBox<String >jbookCombobox;
	JPanel header,main,footer;
	public AddBook(String title) {
		super(title);
		setLayout(new GridLayout(3,1));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		jheader = new JLabel("Add Book");
		jheader.setBounds(450, 0, 100, 50);
		
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
		//bookDepartment = new JTextArea();
		
		String[] dept = new String[10];
		dept[0]="Computer Science and Engineering";
		dept[1]="Electrical and Electronic Engineering";
		dept[2]="Applied Physics, Electronics and Communication Engineering";
		dept[3]="Applied Chemistry and Chemical Engineering";
		dept[4]="Mathematics";
		dept[5]="Statistics";
		dept[6]="Chemistry";
		dept[7]="Pharmacy";
		dept[8]="Environmental Science & Disaster Management";	
		
		jbookCombobox = new JComboBox<String>(dept);
		
		ImageIcon backIcon = new ImageIcon("img\\back.png");
		back = new JButton("",backIcon);
		back.setBounds(842, 0, 50, 50);
		back.addActionListener(this);
		addKeyListener(this);
		
		save = new JButton("Save");
		save.addActionListener(this);
		reset = new JButton("Reset");
		reset.addActionListener(this);
		
		header = new JPanel();
		header.setLayout(null);
		
		main = new JPanel();
		main.setLayout(new GridLayout(7,2,0,7));
		
		footer = new JPanel();
		
		header.add(jheader);
		header.add(back);
		
		main.add(jbookID);
		main.add(bookID);
		main.add(jbookName);
		main.add(bookName);
		main.add(jbookINBS);
		main.add(bookINBS);
		main.add(jbookWriter);
		main.add(bookWriter);
		main.add(jbookPublisher);
		main.add(bookPublisher);
		main.add(jbookDepartment);
		main.add(jbookCombobox);
		main.add(save);
		main.add(reset);
		
		add(header);
		add(main);
		add(footer);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if(ae.getActionCommand().equals(back.getActionCommand())) {
			System.out.println("back");
			TabedWindow tw = new TabedWindow();
			tw.setVisible(true);
			tw.setSize(900, 600);
			tw.setLocation(250, 100);
			setVisible(false);
		}
		if(ae.getActionCommand().equals(save.getActionCommand())) {
			id = Integer.parseInt(bookID.getText().toString().trim());
			name = bookName.getText().toString().trim();
			inbs = bookINBS.getText().toString().trim();
			writer= bookWriter.getText().toString().trim();
			publisher = bookPublisher.getText().toString().trim();
			department = jbookCombobox.getSelectedItem().toString();
			//JOptionPane.showMessageDialog(this, id+" "+name+" "+inbs+" "+writer+" "+publisher+" "+department);
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Library", "12345");
				PreparedStatement ps = con.prepareStatement("insert into BOOKS values(?,?,?,?,?,?)");
				ps.setString(3, inbs);
				ps.setInt(1, id);
				ps.setString(2,name);
				ps.setString(4, writer);
				ps.setString(5, publisher);
				ps.setString(6, department);
				ps.executeQuery();
				JOptionPane.showMessageDialog(this,"Data Saved!\n"+id+" "+name+" "+inbs+" "+writer+" "+publisher+" "+department);
			}catch(Exception e) {
				JOptionPane.showMessageDialog(this,"Please Cheek the input again!");
				System.out.println(e);
			}
			
		}
		if(ae.getActionCommand().equals(reset.getActionCommand())) {
			bookID.setText("");
			bookName.setText("");
			bookINBS.setText("");
			bookWriter.setText("");
			bookPublisher.setText("");
		}
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		char key = ke.getKeyChar();
		System.out.println(key);
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		char key = ke.getKeyChar();
		System.out.println(key);
		
	}

	@Override
	public void keyTyped(KeyEvent ke) {
		char key = ke.getKeyChar();
		System.out.println(key);
		
	}
}