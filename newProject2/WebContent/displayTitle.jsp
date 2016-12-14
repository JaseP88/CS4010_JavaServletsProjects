<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
		<title> DISPLAY TITLE </title>
	</head>

	<body>
		<h1> ASSIGNMENT TITLES </h1>
		
		<div id="backbutt">
            <form id="goback" method="POST" action="GetQuestionServlet">
                <button type="submit" value="Submit"> GO BACK </button>
            </form>
        </div>
        
		<form id="addAssignmentForm" method="POST" action="CreateAssignmentServlet">
            <div id="titleInput">
                <textarea name="titleInput" placeholder="Create Assignment Title Here"></textarea>
                <button type="submit" value="Submit">Create Assignment</button>
            </div>
        </form>
        
        <h1> Current Assignment: <c:out value="${sessiontitle}" default="N/A" ></c:out></h1>
        
        <div id="TitleTableDiv">
        	<table>
            	<c:forEach var="theTitles" items="${myTitle}">
            		
                		<tr>
                			<form method="POST" action="ViewTitleServlet" >
                				<input type="hidden" name="titleparam" value="${theTitles}" />
                    			<td> <c:out value="${theTitles}" /> </td>
                    			<td> <button type="submit" value="view" name="action">View</button> </td>
                    		
                    			<td> <button value="select" name="action">Select</button></td>  </form>                 	
                   		</tr>
                   	
                </c:forEach>
             </table>
         </div>
   
	</body> 
</html>