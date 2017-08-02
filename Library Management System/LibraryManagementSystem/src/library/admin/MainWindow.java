package library.admin;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class MainWindow extends JPanel implements ActionListener,KeyListener{
	
	JButton logout;
	JLabel jfooter;
	JPanel mainPanel,header,footer;
	public MainWindow(String title){

		setLayout(new GridLayout(2,1));
		
		header = new JPanel();
		footer = new JPanel();
		
		jfooter = new  JLabel("Bangabandhu Sheikh Mujibur Rahman Science and Technology University");
		ImageIcon logoutIcon = new ImageIcon("img\\logout.png");
		logout = new JButton("",logoutIcon);
		//logout.setContentAreaFilled(true);
		header.add(logout);
		footer.add(jfooter);
		
		add(header);
		add(footer);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(logout.getActionCommand())) {
			Window wd=new Window("Library Management System");
			wd.setSize(900,600);
			wd.setLocation(250, 100);
			wd.setVisible(true);
		}
		
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		
	}

	@Override
	public void keyTyped(KeyEvent ke) {
		
	}

}
