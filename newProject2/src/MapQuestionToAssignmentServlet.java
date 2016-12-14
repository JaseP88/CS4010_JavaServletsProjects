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


@WebServlet("/MapQuestionToAssignmentServlet")
public class MapQuestionToAssignmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	private PreparedStatement addtomapping, findTitle, findQuestion;
 
	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:8889/proj2db","root","root");

			String insertmappingQuery = "INSERT INTO questionAssignmentMapping(Qid, Aid) values(?, ?)";
			addtomapping = connection.prepareStatement(insertmappingQuery);	//insert id mapping of keyword and question
			
		}
		catch (Exception exception) {
			exception.printStackTrace();
			throw new UnavailableException(exception.getMessage());
		}
	}
	
	public void destroy() {
		try {
			addtomapping.close();
			connection.close();
			findTitle.close();
			findQuestion.close();
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
			int aID, qID;
			String questionparam = request.getParameter("questionparam");
			String titleparam = request.getParameter("sessionparam");
			
			//find the assignment title in assignmentTable
			String findtitlequery = "SELECT id FROM assignmentTable WHERE title = " +"'"+ titleparam+"'";
			findTitle = connection.prepareStatement(findtitlequery);
			ResultSet rs = findTitle.executeQuery();
			rs.next();
			aID = rs.getInt(1);
			
			//find the question in questions (table)
			String findquestionquery = "SELECT id FROM questions WHERE question = "+"'"+ questionparam+"'";
			findQuestion = connection.prepareStatement(findquestionquery);
			ResultSet qrs = findQuestion.executeQuery();
			qrs.next();
			qID = qrs.getInt(1);
			
			//add qID / aID to questionAssignmentMapping (table)
			addtomapping.setInt(1,qID);
			addtomapping.setInt(2,aID);
			addtomapping.executeUpdate();
			
			rs.close();
			qrs.close();
			
			RequestDispatcher view = request.getRequestDispatcher("GetQuestionServlet");
			view.forward(request,response);
		}
		catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
		
	}

}
