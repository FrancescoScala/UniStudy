<%@ page import="it.unisa.beans.User" %>
<%@ page import="it.unisa.beans.Enrollment" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="">Hello Servlet <%=((User) session.getAttribute("userInSession")).getName()%>
</a>
<%
    if (((Set<Enrollment>) session.getAttribute("enrollments")).size() >= 1) {
        for (Enrollment enrollment : (Set<Enrollment>) session.getAttribute("enrollments")) {
%>
<form method="post" action="CourseControl">
    <input type="hidden" id="action" name="action" value="view">
    <input type="hidden" id="qty" name="qty" value="single">
    <input type="hidden" id="id" name="id" value="86">
    <input type="hidden" id="courseTitle" name="courseTitle" value="Programmazione 3">
    <h2 >Programmazione 3</h2>
    <button value="STUDENTE" name="role" type="submit">VISUALIZZA</button>
</form>
<%
        }
    }
%>
</body>
</html>