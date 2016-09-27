<%@ page language="java"%>

<!DOCTYPE html>
<html>

	<head>
		<title>Title Result</title>
		<meta charset="utf-8" />
	</head>

	<body>
		
		<%
			String [] title = (String [])request.getAttribute("titles");
			String word = title[0];
			//for (int i=0; i<title.length; i++)
			System.out.println(title[0]);
			
			out.println(word);
		%>
	


	</body>

</html>	