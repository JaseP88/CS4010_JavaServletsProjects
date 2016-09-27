

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.URL;
import java.net.URLConnection;
import java.util.regex.*;
import java.util.*;



/**
 * Servlet implementation class ReadURLServlet
 */
@WebServlet(description = "Servlet to retrieve URL from textbox", urlPatterns = { "/ReadURLServlet" })
public class ReadURLServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReadURLServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/*
		String [] url = request.getParameterValues("urlname");
		

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		for(int i=0; i<url.length; i++)
			out.println("<h1>hey</h1>" + url[i]);
		
		out.close();
	*/


		
		String title;
		String pattern = ".*<title>.*"; // look for title string
		
		String [] urlArray = request.getParameterValues("urlname");
		
		try {
			
			String urlName = "http://horstmann.com";
			URL url = new URL(urlName);
			URLConnection connection = url.openConnection();
			
			connection.connect();
			
			Scanner scanner = new Scanner(connection.getInputStream());
			
			while (scanner.hasNextLine()) {
			
				String match = scanner.next(); //read the next word and set to string
				
				if (Pattern.matches(pattern, match)) { //compare the string to the pattern
	
					title = match + scanner.nextLine(); //concatenate the string to the rest of string in the line
					System.out.println(title);
					scanner.close();
					break;
				}
			}
			
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}	
		
}
