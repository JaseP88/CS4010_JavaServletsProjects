

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.RequestDispatcher;
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
		
		try {
			
			String title;
			String pattern = ".*<title>.*"; // look for title string
			
			String [] urlArray = request.getParameterValues("urlname");
			ArrayList <String>titleArray = new ArrayList <String> ();	//to hold all the titles
			
			for (int i=0; i<urlArray.length; i++) {
				
				String urlName = urlArray[i];
				URL url = new URL(urlName);
				URLConnection connection = url.openConnection();
				
				connection.connect();
				
				Scanner scanner = new Scanner(connection.getInputStream());;
				
				while (scanner.hasNextLine()) {
					String match = scanner.next();
					
					if (Pattern.matches(pattern,  match)) {
						
						title = match+scanner.nextLine();
						
			
						//System.out.println(title);
						title = title.replaceAll("\\<", "").replaceAll("\\>"," ");	//replace brackets to not affect the DOM
						//System.out.println(title);
						
						titleArray.add(title);
						
						//System.out.println(title);
						break;
					}
				}
				scanner.close();
			}
			
			request.setAttribute("titles", titleArray);
			RequestDispatcher view = request.getRequestDispatcher("result.jsp");
			
			view.forward(request, response);
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}	
		
}
