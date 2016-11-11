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
 * Servlet implementation class UpdateKeywordServlet
 Update keyword and map its id to the questions table id
 */
@WebServlet("/UpdateKeywordServlet")
public class UpdateKeywordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection connection;
	private PreparedStatement questionresults, addtokeyword, addtomapping, retrieveKID, retrieveQID;

	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:8889/proj2db","root","root");

			String insertkeywordQuery = "INSERT INTO keywords(keyword) values(?)";
			String insertmappingQuery = "INSERT INTO mappings(Kid, Qid) values(?, ?)";
			String retrieveQuestionTable = "SELECT question, id " + "FROM questions ORDER by id";
			String retrieveQuestionID = "SELECT id " + "FROM questions";
			String retrieveKeywordID = "SELECT id " + "FROM keywords";	//not used
		
			
			addtokeyword = connection.prepareStatement(insertkeywordQuery);	//insert keywords into keyword table
			addtomapping = connection.prepareStatement(insertmappingQuery);	//insert id mapping of keyword and question
			questionresults = connection.prepareStatement(retrieveQuestionTable);	//retrieve all fields in question table
			retrieveKID = connection.prepareStatement(retrieveKeywordID);	//retrieve the keyword table id
			retrieveQID = connection.prepareStatement(retrieveQuestionID);
			
			//questionresults = connection.prepareStatement("SELECT question, id " + "FROM questions ORDER by id");
		}
		catch (Exception exception) {
			exception.printStackTrace();
			throw new UnavailableException(exception.getMessage());
		}
	}


	public void destroy() {
		try {
			retrieveKID.close();
			retrieveQID.close();
			questionresults.close();
			addtokeyword.close();
			addtomapping.close();
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
			int k_id, q_id;
			ResultSet resultsRS;
			Statement st = connection.createStatement();
			String queryForkeywordId = "SELECT LAST_INSERT_ID() as id FROM keywords";

			//add the keywords to the database
			String [] keywordArr = request.getParameterValues("keyword");
			ArrayList <String>keywordList = new ArrayList <String>();
			
			for (int i=0; i<keywordArr.length; i++) {
				if (keywordArr[i] != "") {
					addtokeyword.setString(1,keywordArr[i]);
					addtokeyword.executeUpdate();

					//get the last used id of keyword
					ResultSet rs2 = st.executeQuery(queryForkeywordId);
					rs2.next();
					k_id = rs2.getInt("id");
					//System.out.println("Last id of this session was..." +k_id);


					//get the selected question id
					resultsRS = retrieveQID.executeQuery();
					
					for (int j=0; j<=i; j++) {
						resultsRS.next();
					}
					q_id = resultsRS.getInt(1);
					//System.out.println(q_id);

					//add qid and kid to mapping table
					addtomapping.setInt(1,k_id);
					addtomapping.setInt(2,q_id);
					addtomapping.executeUpdate();
					break;
				}
			}		
			
			//Process the question database to forward to displayQuestion.jsp
			resultsRS = questionresults.executeQuery();
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