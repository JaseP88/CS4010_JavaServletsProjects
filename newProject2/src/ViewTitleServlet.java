import java.util.*;

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
import javax.servlet.http.HttpSession;

@WebServlet("/ViewTitleServlet")
public class ViewTitleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	private PreparedStatement questionresults, findTitle, findQID;
   
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
			findTitle.close();
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
			
			String action = request.getParameter("action");
			String titleparam = request.getParameter("titleparam");
			
			//determine if select button was pushed or view button
			if (action.equals("select")) {
				HttpSession session = request.getSession();
				session.setAttribute("sessiontitle",titleparam);
				
				RequestDispatcher view = request.getRequestDispatcher("GetAssignmentServlet");
				view.forward(request,response);
			}
			
			else {
				int aID; //assignment id
				int qID;
			
				ArrayList <String>questionArr = new ArrayList <String> ();
			
				String findtitlequery = "SELECT id FROM assignmentTable WHERE title = " +"'"+ titleparam+"'";
		
				findTitle = connection.prepareStatement(findtitlequery);
				ResultSet rs = findTitle.executeQuery();
			
				rs.next();
				aID = rs.getInt(1);
			
				String findQIDquery = "SELECT Qid FROM questionAssignmentMapping WHERE Aid = " + "'" +String.valueOf(aID)+"'";	//sql query with aID found
				findQID = connection.prepareStatement(findQIDquery);	//using Aid find Qid from mapping
				ResultSet qIDrs = findQID.executeQuery();
			
				String findQuestionQuery;
			
				while (qIDrs.next()) {
					qID = qIDrs.getInt(1);
				
					findQuestionQuery = "SELECT question FROM questions WHERE id = " + "'"+String.valueOf(qID)+"'";	//sql query with qID found
					questionresults = connection.prepareStatement(findQuestionQuery);
					ResultSet questionrs = questionresults.executeQuery();	//get the question from qID
					questionrs.next();
					questionArr.add(questionrs.getString(1));	//add that question into the ListArray
					questionrs.close();
				}
			
				qIDrs.close();
				rs.close();
			
				//send the ListArray
				request.setAttribute("theQuestions",questionArr);
				request.setAttribute("title",titleparam);
				RequestDispatcher view = request.getRequestDispatcher("displayAssignment.jsp");
				view.forward(request,response);
			}
			
		}
		catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}

}
