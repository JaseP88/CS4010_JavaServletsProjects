<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title> DISPLAY QUESTION </title>
    </head>

    <body>
    	<h1> DISPLAY QUESTION </h1>
    	<h1> Current Assignment: <c:out value="${sessiontitle}" default="N/A" ></c:out></h1>
        <form id="addQuestionForm" method="POST" action="UpdateQuestionServlet">
            <div id="textarea">
                <textarea name="textArea" placeholder="Enter question and click add button to add it to Database"></textarea>
                <button type="submit" value="Submit">Add Question</button>
            </div>
        </form>

        <form id="addKeywordForm" method="POST" action="UpdateKeywordServlet">
            <div id="questionTableDiv">
                <table>
                    <c:forEach var="theQuestions" items="${myQuestions}">
                        <tr>
                            <td><c:out value="${theQuestions}" /></td>
                            <td><input type="text" id="keywordtext" name="keyword" placeholder="Add Keyword"/>
                                <button type="submit" value="Submit">Add Keyword</button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </form>

        <form id="keywordSearch" method="POST" action="KeywordSearchServlet">
            <div id="keywordfilter">
                <input type="text" placeholder="Find Keyword" id="keyword_search" name="search" />
                <button type="submit" value="Submit">Find</button>
            </div>
        </form>
        <form id="getAssignmentForm" method="POST" action="GetAssignmentServlet" >
    		<div id="buttondiv">
    			<button type="submit" form="getAssignmentForm" value="Submit">ASSIGNMENT</button>
    		</div>
    	</form>
    </body>
</html>