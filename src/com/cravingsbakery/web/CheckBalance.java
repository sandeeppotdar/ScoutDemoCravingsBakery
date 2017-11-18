package com.cravingsbakery.web;

//Loading required libraries
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CheckBalance extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		String inAccountNum = request.getParameter("inAccountNum");

		try {
			Class.forName("org.postgresql.Driver");
			Connection con = null;
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/initech", "initech", "pass123");
			System.out.println("Connected to database successfully");
			Statement st = null;	
			
			st = con.createStatement();

			String selectStatement = "SELECT * FROM account WHERE accountnum = '" + inAccountNum + "'";
		    ResultSet rs = st.executeQuery(selectStatement);
			
			System.out.println(selectStatement);

			if (!rs.isBeforeFirst()) {
				out.println("No such account");
			} //end if
			else {
				// Extract data from result set
				while (rs.next()) {
					// Retrieve by column name
					String outaccountnum = rs.getString("accountnum");
					String outaccountname = rs.getString("accountname");
					int outbalance = rs.getInt("balance");

					// Display values
					out.println("<b> Account Number: </b>" + outaccountnum + " <b> Name: </b>" + outaccountname + "   <b> Balance Rewards: </b>" + outbalance + "<br>");
				} //end while
			} //end else

			// Clean-up environment
			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {

		} // end finally
	} // end try
} //end class
