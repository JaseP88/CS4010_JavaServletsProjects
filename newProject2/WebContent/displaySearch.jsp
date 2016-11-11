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

        <div id="displayQ">
            <table>
                <c:forEach var="theQuestions" items="${myQuestions}">
                    <tr>
                        <td><c:out value="${theQuestions}" /></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>