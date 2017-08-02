package library.admin;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.table.TableModel;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

@SuppressWarnings("serial")
public class EditBook extends JFrame implements ActionListener{
	JPanel jheader ,jmain;
	JLabel fheader,jdept;
	JTable dataTable;
	JComboBox<String >jbookCombobox;
	JScrollPane sp;
	JButton back;
	public EditBook(String title) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(2, 1));
		setResizable(false);
		String[] dept = new String[9];
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
		jbookCombobox.addActionListener(this);
		
		fheader = new JLabel("Select A department for edit");
		jdept = new JLabel("");
		//jdept.setVisible(false);
		
		sp = new JScrollPane();
		dataTable = new JTable();
		sp.add(dataTable);
		
		ImageIcon backIcon = new ImageIcon("img\\back.png");
		back = new JButton("",backIcon);
		back.setBounds(842, 0, 50, 50);
		back.addActionListener(this);
		
		jheader = new JPanel();
		jheader.setLayout(new FlowLayout());
		jmain = new JPanel();
		jmain.setLayout(new GridLayout(1,1));
		
		jheader.add(fheader);
		jheader.add(jbookCombobox);
		jbookCombobox.setBounds(300, 5, 300,30);
		jheader.setLayout(null);
		
		jheader.add(jdept);
		jheader.add(back);
		
		jmain.add(sp);
		
		add(jheader);
		add(jmain);
		
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getActionCommand().equals(back.getActionCommand())) {
			System.out.println("back");
			TabedWindow tw = new TabedWindow();
			tw.setVisible(true);
			tw.setSize(900, 600);
			tw.setLocation(250, 100);
			setVisible(false);
		}
		if(ae.getActionCommand().equals(jbookCombobox.getActionCommand())) {
			//Vector<Vector<String>> data=new Vector<Vector<String>>();
			int rows=0;
			String department = jbookCombobox.getSelectedItem().toString();
			jdept.setVisible(false);
			jdept.setText(department);
			
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Library", "12345");
				PreparedStatement ps = con.prepareStatement("select * from BOOKS where DEPARTMENT=?");
				ps.setString(1, department);
				ResultSet rs =ps.executeQuery();
				PreparedStatement ps2 = con.prepareStatement("select count(DEPARTMENT) as id from BOOKS where DEPARTMENT=?");
				ps2.setString(1, department);
				ResultSet rs2 = ps2.executeQuery();
				if(rs2.next()) {
					rows= rs2.getInt(1);
					System.out.println(rows);
				}
				String data[][] = new String[rows][6];
				int j=0;
				while(rs.next()) {
//					Vector<String> tem=new Vector<String>();
//					tem.addElement(rs.getString(1));
//					tem.addElement(rs.getString(2));
//					tem.addElement(rs.getString(3));
//					tem.addElement(rs.getString(4));
//					tem.addElement(rs.getString(5));
//					tem.addElement(rs.getString(6));
//					
//					data.addElement(tem);
					for(int i=0;i<6;i++) {
						data[j][i] = new String(rs.getString(i+1));
					}
					j++;
				}
				/*Vector<String> column = new Vector<String>();
				column.addElement("ID");
				column.addElement("Book Name");
				column.addElement("INBS");
				column.addElement("Writer");
				column.addElement("Publisher");
				column.addElement("Department");*/
				String column2[]={"ID","NAME","SALARY"};
				String data2[][]={ {"101","Amit","670000"},    
	                    {"102","Jai","780000"},    
	                    {"101","Sachin","700000"}};
				String column[]={"ID","Book Name","INBS","Writer","Publisher","Department"};
				dataTable = new JTable(data,column);
				dataTable.setBounds(30,40,200,900);          
				jmain.remove(sp);
				sp=new JScrollPane(dataTable); 
				jmain.add(sp);
				
			}catch(Exception e) {
				System.out.println(e);
			}
			
		}
		
	}
	

}
