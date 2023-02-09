<%--
  Created by IntelliJ IDEA.
  User: nickm
  Date: 09/02/2023
  Time: 17:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Iscrizione al corso</title>
</head>
<body>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    String title = request.getParameter("title");

%>
<form method="post" action="<%=request.getContextPath()%>/CourseControl">
    <input type="hidden" id="action" name="action" value="enroll">
    <input type="hidden" id="id" name="id" value="<%=id%>">
    <input type="hidden" id="title" name="title" value="<%=title%>">
    <h2><%=title%></h2>
    <button value="STUDENTE" name="role" type="submit">ISCRIVIMI</button>
</form>
</body>
</html>
