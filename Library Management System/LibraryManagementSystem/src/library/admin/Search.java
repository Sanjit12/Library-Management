package library.admin;

import javax.swing.*;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.FlowLayout;
import java.awt.Font;

@SuppressWarnings("serial")
public class Search extends JPanel implements ActionListener {
	JButton logout;
	JLabel jfooter, jmain,jkeywoard;
	JPanel header, mainPanel, footer;
	JTextField jsearch;
	JButton jSearchButton,back;
	JTable dataTable;
	JScrollPane sp;
	public Search(String title) {

		setLayout(new GridLayout(2, 1));

		header = new JPanel(new FlowLayout());
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 1));
		//footer = new JPanel();

		jmain = new JLabel("");
		jkeywoard = new JLabel("KeyWoard : ");
		jkeywoard.setVisible(false);
		jmain.setVisible(false);
		
		ImageIcon backIcon = new ImageIcon("img\\back.png");
		back = new JButton("",backIcon);
		back.setBounds(842, 0, 50, 50);
		back.addActionListener(this);
		
		dataTable = new JTable();
		sp = new JScrollPane();
		sp.add(dataTable);
		// jheader = new JLabel("Search Book");
		// jheader.setFont(new Font("ARIAL", 50,30));
		// jheader.setFont(new Font());
		//jfooter = new JLabel("Bangabandhu Sheikh Mujibur Rahman Science and Technology University");
		//jfooter.setFont(new Font("ARIAL", 30, 25));

		jsearch = new JTextField(15);
		Font bigFont = jsearch.getFont().deriveFont(Font.PLAIN, 45f);
		jsearch.setFont(bigFont);

		ImageIcon searchIcon = new ImageIcon("img\\search2.png");
		jSearchButton = new JButton("Search", searchIcon);
		jSearchButton.addActionListener(this);

		// header.add(jheader);
		header.add(jsearch);
		header.add(jSearchButton);
		header.add(back);
		header.add(jkeywoard);
		header.add(jmain);
		//mainPanel.add(jmain);
		mainPanel.add(sp);
		//footer.add(jfooter);

		add(header);
		add(mainPanel);
		//add(footer);

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
		int rows=0;
		String data = "";
		if (ae.getActionCommand().equals(jSearchButton.getActionCommand())) {
			try {
				data = jsearch.getText().toString().trim();
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Library", "12345");
				Statement ps = con.createStatement();
				
				//ps.setString(1, data);
				//ResultSet rs = ps.executeQuery();
				ResultSet rs2 = ps.executeQuery("select count(DEPARTMENT) from BOOKS where UPPER(BOOK_NAME) like UPPER('%"+data+"%')");
				if(rs2.next()) {
					rows= rs2.getInt(1);
					System.out.println(rows);
				}
				ResultSet rs1 = ps.executeQuery("select * from BOOKS where UPPER(BOOK_NAME) like UPPER('%"+data+"%')");
				String data2[][] = new String[rows][6];
				int j=0;
				while (rs1.next()) {
					//System.out.println(rs1.getInt(1));
					for(int i=0;i<6;i++) {
						data2[j][i] = new String(rs1.getString(i+1));
					}
					j++;
				}
				String column[]={"ID","Book Name","INBS","Writer","Publisher","Department"};
				dataTable = new JTable(data2,column);
				dataTable.setBounds(30,40,200,900);          
				mainPanel.remove(sp);
				sp=new JScrollPane(dataTable); 
				mainPanel.add(sp);
			} catch (Exception e) {
				System.out.println(e);
			}

			jmain.setText(data);
		}
	}
}
