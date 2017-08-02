<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
		String name=request.getParameter("fullname");
		String id=request.getParameter("id");
		String dept=request.getParameter("dept");
		String username=request.getParameter("username");
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		String cpassword=request.getParameter("cpassword");
		String phone=request.getParameter("phone");
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		if(password.equals(cpassword)){
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Library", "12345");
				
				PreparedStatement ps=con.prepareStatement("insert into students values(?,?,?,?,?,?,?)");
				ps.setString(1, id);
				ps.setString(2, name);
				ps.setString(3, dept);
				ps.setString(4, email);
				ps.setString(5, password);
				ps.setString(6, phone);
				ps.setString(7, username);
				ps.execute();
				
				RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
				pw.println("<h1 align=center>Registration Successful</h1>");
				rd.include(request, response);
			
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		else{
			RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
			pw.println("<h1 align=center>Password didn\'t match.</h1>");
			rd.include(request, response);
		}
	%>
</body>
</html>