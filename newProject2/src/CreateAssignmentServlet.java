import java.util.*;

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


@WebServlet("/CreateAssignmentServlet")
public class CreateAssignmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;

	private PreparedStatement results, addto;
	
	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:8889/proj2db","root","root");
			String insertQuery="INSERT INTO assignmentTable(title) values(?)";
			addto = connection.prepareStatement(insertQuery);
	
			results = connection.prepareStatement("SELECT title, id FROM assignmentTable ORDER by id");
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
			String addedTitle = request.getParameter("titleInput");
			addto.setString(1,addedTitle);
			addto.executeUpdate();

			ResultSet resultsRS = results.executeQuery();
			ArrayList <String>titleArray = new ArrayList <String> ();
			while (resultsRS.next()) {
				titleArray.add(resultsRS.getString(1));
			}
			
			resultsRS.close();
			request.setAttribute("myTitle",titleArray);
			RequestDispatcher view = request.getRequestDispatcher("displayTitle.jsp");
			view.forward(request,response);
		}
		catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}

}
