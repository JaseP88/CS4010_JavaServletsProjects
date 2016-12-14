<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
		<title> DISPLAY ASSIGNMENT </title>
	</head>
	<body>
		<h1> ASSIGNMENT: <c:out value="${title}" default="N/A" ></c:out></h1>
		
		<div id="backbutt">
            <form id="goback" method="POST" action="GetAssignmentServlet">
                <button type="submit" value="Submit"> GO BACK </button>
            </form>
        </div>
        
        <div id="displayQ">
            <table>
                <c:forEach var="theQuestions" items="${theQuestions}">
                    <tr>
                        <td><c:out value="${theQuestions}" /></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>


</html>