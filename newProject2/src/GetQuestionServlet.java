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
 * Servlet implementation class GetQuestionServlet
 */
@WebServlet("/GetQuestionServlet")
public class GetQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Connection connection;
	private PreparedStatement results;
 

	// Set up the database connection
	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:8889/proj2db","root","root");
			Statement stmt = connection.createStatement();

			/* Create the table if none exists */
			stmt.execute("CREATE TABLE IF NOT EXISTS questions (id int NOT NULL AUTO_INCREMENT," +
				"question varchar (30) NOT NULL," +
				"PRIMARY KEY (id));" 
			);

			stmt.execute("CREATE TABLE IF NOT EXISTS keywords (id int NOT NULL AUTO_INCREMENT," +
				"keyword varchar (30) NOT NULL," +
				"PRIMARY KEY (id));"
			);

			stmt.execute("CREATE TABLE IF NOT EXISTS mappings (id int NOT NULL AUTO_INCREMENT," +
				"Kid int NOT NULL," + "Qid int NOT NULL,"+
				"PRIMARY KEY (id));"
			);
			
			stmt.execute("CREATE TABLE IF NOT EXISTS assignmentTable (" +
					"id int NOT NULL AUTO_INCREMENT," +
					"title varchar(30) NOT NULL," +
					"PRIMARY KEY (id) );"
			);
			
			stmt.execute("CREATE TABLE IF NOT EXISTS questionAssignmentMapping (" +
					"id int NOT NULL AUTO_INCREMENT," +
					"Qid int NOT NULL," +
					"Aid int NOT NULL," +
					"PRIMARY KEY (id) );"
			);

			stmt.close();
			results = connection.prepareStatement("SELECT question, id " + "FROM questions ORDER by id");
		}
		catch (Exception exception) {
			exception.printStackTrace();
			throw new UnavailableException(exception.getMessage());
		}
	}

	// Cut connection
	public void destroy() {
		try {
			results.close();
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
			ResultSet resultsRS = results.executeQuery();
			ArrayList <String>questionArr = new ArrayList <String> ();
			while (resultsRS.next()) {
				questionArr.add(resultsRS.getString(1));
			}
			resultsRS.close();

			//System.out.println(questionArr.get(0));
			request.setAttribute("myQuestions",questionArr);
			RequestDispatcher view = request.getRequestDispatcher("displayQuestion.jsp");
			view.forward(request,response);
		}

		catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}

}