<%@page import="java.sql.SQLException"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/nav.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
</head>
<body bgcolor="#E0EEEE">
	<header>
	<div class="nav">
		<ul>
			<li class="home"><a href="#">Home</a></li>
			<li class="tutorials"><a href="#">Profile</a></li>
			<li class="about"><a href="search.jsp">Search</a></li>
			<li class="news"><a href="chat.jsp">Discussions</a></li>
			<li class="contact"><a href="logout.jsp">Logout</a></li>
		</ul>
	</div>
	</header>
	<%
		int nodata=1;
		String fullname = "";
		String id = "";
		String borrow_date = "", return_date = "";
		long fine = 0;
		int book_id;

		String name = (String) session.getAttribute("user");
		if (name == null) {
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
		}
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Library", "12345");

			PreparedStatement ps = con.prepareStatement("select * from  students where USERNAME=?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				fullname = rs.getString(2);
				id = rs.getString(1);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	%>
	<h1 align="center">
		Welcome To BSMRSTU Library,
		<%=fullname%></h1>
	<br>
	<br>
	<br>
	<div class="header" align="right"></div>
	<table border="1" align="center">
		<tr>
			<th>Book ID</th>
			<th>Book Name</th>
			<th>Department</th>
			<th>Borrow Date</th>
			<th>Return Date</th>
			<th>Fine</th>
		</tr>
		<%
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Library", "12345");

				PreparedStatement ps2 = con.prepareStatement("select * from borrow where user_id=?");
				ps2.setString(1, id);
				ResultSet rs2 = ps2.executeQuery();
				String book_name = "", dept_name = "";
				while (rs2.next()) {
					nodata=0;
					book_id = rs2.getInt(2);
					long b = rs2.getLong(3), r = rs2.getLong(4);
					long c = new Date().getTime();
					if (c > r) {
						fine = ((c - r) / (60 * 1000)) * 5;
					}
					borrow_date = DateFormat.getDateInstance().format(new Date(b));
					return_date = DateFormat.getDateInstance().format(new Date(r));
					PreparedStatement ps3 = con.prepareStatement("select * from books where book_id=?");
					ps3.setInt(1, book_id);
					ResultSet rs3 = ps3.executeQuery();
					if (rs3.next()) {
						book_name = rs3.getString(2);
						dept_name = rs3.getString(6);
		%>
		<tr>
			<td><%=book_id%></td>
			<td><%=book_name%></td>
			<td><%=dept_name%></td>
			<td><%=borrow_date%></td>
			<td><%=return_date%></td>
			<td><%=fine%></td>
		</tr>
		<%
			}
				}
				if(nodata==1)
				{
					%>
						<tr><td colspan="6"><center>No Books Borrowed</center></td></tr>
					<%
				}

			} catch (Exception E) {

			}
		%>

	</table>
</body>
</html>