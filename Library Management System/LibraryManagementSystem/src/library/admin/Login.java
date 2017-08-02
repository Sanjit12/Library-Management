package library.admin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import library.chat.*;
/**
 * @author OVI
 *
 */
public class Login {
	static Connection con=null;
	public static void main(String[] args) {

		Window wd=new Window("Library Management System");
		wd.setSize(900,600);
		wd.setLocation(250, 100);
		wd.setVisible(true);
		
	}
}

















