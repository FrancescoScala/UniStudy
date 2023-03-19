<%--
  Created by IntelliJ IDEA.
  User: francesco
  Date: 11/02/23
  Time: 11:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
         pageEncoding="US-ASCII" isErrorPage="true"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
    <title>Error Page</title>
</head>
<body>
Si e' verificato un errore: <%=response.getStatus() %><br>
Ci scusiamo per il disagio. Per favore, ritorna sulla <a href="<%=request.getContextPath()%>/user/login.jsp">pagina di login</a>
<br>
<%=exception%>
</body>
</html>