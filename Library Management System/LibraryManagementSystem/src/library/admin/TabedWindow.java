package library.admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class TabedWindow extends JFrame implements ActionListener{
	MainWindow mw;
	HomeWindow hw;
	Search sw;
	public TabedWindow() {
		JTabbedPane jtp=new JTabbedPane();
		mw=new MainWindow("none");
		hw=new HomeWindow();
		hw.addBook.addActionListener(this);
		hw.editBook.addActionListener(this);
		hw.borrowBook.addActionListener(this);
		hw.returnBook.addActionListener(this);
		hw.editBook2.addActionListener(this);
		mw.logout.addActionListener(this);
		sw = new Search("Search");
		
		jtp.addTab("Home", hw);
		jtp.addTab("Search", sw);
		jtp.addTab("Logout", mw);

		add(jtp);
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getActionCommand().equals(mw.logout.getActionCommand())) {
			Window wd=new Window("Library Management System");
			wd.setSize(900,600);
			wd.setLocation(250, 100);
			wd.setVisible(true);
			setVisible(false);
		}
		if(ae.getActionCommand().equals(hw.addBook.getActionCommand())) {
			AddBook wd=new AddBook("Add Book");
			wd.setSize(900,600);
			wd.setLocation(250, 100);
			wd.setVisible(true);
			setVisible(false);
		}
		if(ae.getActionCommand().equals(hw.editBook.getActionCommand())) {
			EditBook wd=new EditBook("Edit Book");
			wd.setSize(900,600);
			wd.setLocation(250, 100);
			wd.setVisible(true);
			setVisible(false);
		}
		if(ae.getActionCommand().equals(hw.borrowBook.getActionCommand())) {
			BorrowBook wd=new BorrowBook("Borrow Book");
			wd.setSize(900,600);
			wd.setLocation(250, 100);
			wd.setVisible(true);
			setVisible(false);
		}
		if(ae.getActionCommand().equals(hw.returnBook.getActionCommand())) {
			ReturnBook wd=new ReturnBook("Return Book");
			wd.setSize(900,600);
			wd.setLocation(250, 100);
			wd.setVisible(true);
			setVisible(false);
		}
		if(ae.getActionCommand().equals(hw.editBook2.getActionCommand())) {
			EditBook2 wd=new EditBook2("Search Book");
			wd.setSize(900,600);
			wd.setLocation(250, 100);
			wd.setVisible(true);
			setVisible(false);
		}
	}
}
