<%--
  Created by IntelliJ IDEA.
  User: francesco
  Date: 04/02/23
  Time: 16:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form method="post" action="UserControl">
    <input type="hidden" name="action" value="login">
    <input type="text" name="email" placeholder="Email">
    <input type="text" name="password" placeholder="Password">
    <button type="submit">Logga mocc a mammt</button>
</form>
</body>
</html>
