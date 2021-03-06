import java.util.*;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.UnavailableException;
import java.sql.*;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class UpdateQuestionServlet
 */
@WebServlet("/UpdateQuestionServlet")
public class UpdateQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;

	private PreparedStatement results, addto;
       
 
	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:8889/proj2db","root","root");
			String insertQuery="INSERT INTO questions(question) values(?)";
			addto = connection.prepareStatement(insertQuery);
	
			results = connection.prepareStatement("SELECT question, id " + "FROM questions ORDER by id");
		}
		catch (Exception exception) {
			exception.printStackTrace();
			throw new UnavailableException(exception.getMessage());
		}
	}

	
	public void destroy() {
		try {
			results.close();
			addto.close();
			connection.close();
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
			String addedQuestion = request.getParameter("textArea");
			addto.setString(1,addedQuestion);
			addto.executeUpdate();

			ResultSet resultsRS = results.executeQuery();
			ArrayList <String>questionArr = new ArrayList <String> ();
			while (resultsRS.next()) {
				questionArr.add(resultsRS.getString(1));
			}
			
			resultsRS.close();
			request.setAttribute("myQuestions",questionArr);
			RequestDispatcher view = request.getRequestDispatcher("displayQuestion.jsp");
			view.forward(request,response);
		}
		catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}

}