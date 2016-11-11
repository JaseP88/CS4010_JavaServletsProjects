import java.util.*;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.UnavailableException;
import java.sql.*;
import java.io.*;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class KeywordSearchServlet
 */
@WebServlet("/KeywordSearchServlet")
public class KeywordSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	private PreparedStatement questionresults, findkeyword, findQID;
   
	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:8889/proj2db","root","root");

			



		}
		catch (Exception exception) {
			exception.printStackTrace();
			throw new UnavailableException(exception.getMessage());
		}
	}


	public void destroy() {
		try {
			connection.close();
			questionresults.close();
			findkeyword.close();
			findQID.close();
		}
		catch(SQLException SQLException) {
			SQLException.printStackTrace();
		}
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int qID, kID; 

			String findQIDquery, findQuestionQuery;
			ArrayList <String>questionArr = new ArrayList <String> ();

			//set up the query to find id if string matches attribute
			String findkeywordquery = "SELECT id FROM keywords WHERE keyword = " + "'" + request.getParameter("search") + "'";

			findkeyword = connection.prepareStatement(findkeywordquery);
			ResultSet rs = findkeyword.executeQuery();
		

			while (rs.next()) {	//get all Kid from the keyword (multiple similar keywords)
				kID = rs.getInt(1);
				//System.out.println("*kID* " +kID);


				findQIDquery = "SELECT Qid FROM mappings WHERE Kid = " + "'" +String.valueOf(kID)+"'";	//sql query with kID found
				findQID = connection.prepareStatement(findQIDquery);	//using Kid find Qid from mapping (kid and qid all unique ID)
				
				
				ResultSet qIDrs = findQID.executeQuery();
				qIDrs.next();
				qID = qIDrs.getInt(1);		//obtained the unique QID
				//System.out.println("*qID* "+qID);


				findQuestionQuery = "SELECT question FROM questions WHERE id = " + "'"+String.valueOf(qID)+"'";	//sql query with qID found
				questionresults = connection.prepareStatement(findQuestionQuery);
				
				
				ResultSet questionrs = questionresults.executeQuery();	//get the question from qID
				questionrs.next();
				questionArr.add(questionrs.getString(1));	//add that question into the ListArray
				
				questionrs.close();
				qIDrs.close();
			}

			rs.close();
	
			
			//send the ListArray
			request.setAttribute("myQuestions",questionArr);
			RequestDispatcher view = request.getRequestDispatcher("displaySearch.jsp");
			view.forward(request,response);
		}
		catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}

}