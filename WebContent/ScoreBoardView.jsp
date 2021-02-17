<%@page import="com.sun.xml.internal.txw2.Document"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h2>CardFlight Bowling Lanes:</h2>

	<form method="post" action="BackEndBowlingController">
		<%
		Object value = "", input = "", score = "", rolls = "2";
		if (request.getAttribute("frameNumber") == null) {
			value = "1";
		} else {
			value = request.getAttribute("frameNumber");
			input = request.getAttribute("inputPins");
			score = request.getAttribute("totalScore");
			rolls = request.getAttribute("rollsRemaining");
		}
		String scoreStr = " Score ", frameStr = " Frame ";

		if (value.equals(11)) {
			frameStr = " Game Finished ";
			value = "";
			scoreStr = " Total Score ";
			rolls = "";
			input = "";
		
		}
		%>
		<table>
			<tr>
				<td>|<%=frameStr%> <%=value%>: |
				</td>
			</tr>
			<tr>
				<td>|---------|</td>
			</tr>
			<tr>
				<td>| Rolls remaining |</td>
				<td><%=rolls%></td>
			</tr>
			<tr>
				<td>| Input |</td>
				<td><%=input%></td>
			</tr>
			<tr>
				<td>|<%=scoreStr%> |
				</td>
				<td><%=score%></td>
			</tr>

		</table>
		<input name="nextRoll" type="submit" value="Enter next roll">
		<input type="text" name="inputScore">

	</form>
</body>
</html>