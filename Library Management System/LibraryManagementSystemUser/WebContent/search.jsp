<%@page import="com.sun.corba.se.spi.orbutil.fsm.Guard.Result"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<% String submit=""; int data=1;%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/search.css" />
<link rel="stylesheet" type="text/css" href="css/nav.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Books</title>
</head>

<body>
<header>
	<div class="nav">
		<ul>
			<li class="home"><a href="home.jsp">Home</a></li>
			<li class="tutorials"><a href="#">Profile</a></li>
			<li class="about"><a href="search.jsp">Search</a></li>
			<li class="news"><a href="#">About</a></li>
			<li class="contact"><a href="logout.jsp">Logout</a></li>
		</ul>
	</div>
	</header>
<p style="font-size: 50px; color:maroon;font-family: monospace;" align="center" >Search Book From Library</p>
<form id="search-form_3" action="search.jsp">
		<input type="text" class="search_3" name="searchbox"
			placeholder="Book Name" /> <input type="submit" name="search"
			class="submit_3" value="Search" />
	</form>
	<table border="1" align="center">
		<tr>
			<th>Book ID</th>
			<th>Book Name</th>
			<th>Book Writer</th>
			<th>Book Department</th>
			<th>Total Copy</th>
			<th>Available Copy</th>
		</tr>
		<%
			submit = request.getParameter("searchbox");

			if (submit != null) {
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Library",
							"12345");
					Statement ps = con.createStatement();

					ResultSet rs = ps
							.executeQuery("select * from BOOKS where UPPER(BOOK_NAME) like UPPER('%" + submit + "%')");
					while (rs.next()) {
						int id = rs.getInt(1);
						PreparedStatement ps2 = con.prepareStatement("select * from book_details where book_id=?");
						ps2.setInt(1, id);
						ResultSet rs2 = ps2.executeQuery();
						
						while(rs2.next()){
							data=0;
							%>
							<tr>
							<td><%= rs.getInt(1) %></td>
							<td><%= rs.getString(2) %></td>
							<td><%= rs.getString(4) %></td>
							<td><%= rs.getString(6) %></td>
							<td><%= rs2.getInt(2) %></td>
							<td><%= rs2.getInt(3) %></td>
							</tr>
							<%
						}
					}
					if(data==1){
						%>
						<tr>
						<td colspan="6" style="font-size: 34px; font-family: cursive;color:maroon;"><center >No Books Found !</center></td>
						</tr>
						
						<%
					}

				} catch (Exception e) {
					System.out.println(e);
				}
			}
		%>
</table>
	
</body>
</html>