<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title> DISPLAY SEARCH QUESTION </title>
    </head>

    <body>
        <div id="backbutt">
            <form id="goback" method="POST" action="GetQuestionServlet">
                <button type="submit" value="Submit"> GO BACK </button>
            </form>
        </div>

		<h1> Current Assignment: <c:out value="${sessiontitle}" default="N/A" ></c:out></h1>
		<form method="POST" action="GetAssignmentServlet" >
			<button type="submit">Change Assignment</button>
		</form>

        <div id="displayQ">
            <table>
                <c:forEach var="theQuestions" items="${myQuestions}">
                    <tr>
                    	<form method="POST" action="MapQuestionToAssignmentServlet" >
                    		<input type="hidden" name="questionparam" value="${theQuestions}" />
                    		<input type="hidden" name="sessionparam" value="${sessiontitle}" />
                        	<td><c:out value="${theQuestions}" /></td>
                        	<td><button type="submit">Add to Assignment</button></td>
                        </form>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>