<%@ page import="java.util.*" %>


<!DOCTYPE html>
<html>

	<head>
		<title>Title Result</title>
		
	</head>

	<body>
	
		<%
			ArrayList<String> titleList = (ArrayList)request.getAttribute("titles");
			Iterator<String> itr = titleList.iterator();
        	
			
			while (itr.hasNext()) {
        		String element = (String)itr.next();
        		//System.out.println(element);
        		String s = element+"<br>";
        		out.println(s);
        	}
		%>
	

	</body>

</html>	