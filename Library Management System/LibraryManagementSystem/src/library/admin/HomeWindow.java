package library.admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class HomeWindow extends JPanel implements ActionListener{
	JButton addBook,editBook,borrowBook,returnBook,editBook2;
	JPanel header,footer,mainPanel;
	public HomeWindow() {
		header = new JPanel();
		mainPanel = new JPanel();
		footer = new JPanel();
		ImageIcon imageForOne = new ImageIcon("img\\add.png");
		addBook = new JButton("Add Book",imageForOne);
		//addBook.setLayout(new FlowLayout());
		ImageIcon imageForTwo = new ImageIcon("img\\edit.png");
		editBook = new JButton("View Book",imageForTwo);
		ImageIcon imageForThree = new ImageIcon("img\\borrow.png");
		borrowBook = new JButton("Borrow Book",imageForThree);
		ImageIcon imageForFour = new ImageIcon("img\\return2.png");
		returnBook = new JButton("Return Book",imageForFour);
		ImageIcon imageForFive = new ImageIcon("img\\return2.png");
		editBook2 = new JButton("Edit Book",imageForFive);
		
		addBook.addActionListener(this);
		editBook.addActionListener(this);
		borrowBook.addActionListener(this);
		returnBook.addActionListener(this);
		editBook2.addActionListener(this);
		
		mainPanel.add(addBook);
		mainPanel.add(editBook);
		mainPanel.add(editBook2);
		mainPanel.add(borrowBook);
		mainPanel.add(returnBook);
		
		add(mainPanel);
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getActionCommand().equals(addBook.getActionCommand())) {
			System.out.println("ok");
		}
		else if(ae.getActionCommand().equals(editBook.getActionCommand())) {
			System.out.println("ok2");
		}
		else if(ae.getActionCommand().equals(borrowBook.getActionCommand())) {
			System.out.println("ok3");
		}
		else if(ae.getActionCommand().equals(returnBook.getActionCommand())) {
			System.out.println("ok4");
		}
	
	}
}
