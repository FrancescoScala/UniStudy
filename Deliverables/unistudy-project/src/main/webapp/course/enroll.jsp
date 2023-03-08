<%@ page import="user.beans.Member" %><%--
  Created by IntelliJ IDEA.
  Member: francesco
  Date: 10/02/23
  Time: 22:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Iscrizione al corso</title>
</head>
<body>
<jsp:include page="/header.jsp" flush="true"></jsp:include>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    String title = request.getParameter("title");

%>
<main class="page lanidng-page">
    <section class="portfolio-block block-intro">
        <div class="container">
            <form method="post" action="<%=request.getContextPath()%>/CourseControl">
                <div class="about-me">
                    <div class="avatar"
                         style="background-image: url(&quot;../assets/img/avatars/avatar.jpg&quot;);"></div>
                    <h1 style="margin-bottom: 20px;"><%=title%></h1>
                    <div class="row" style="margin-left: 0px;">
                        <div class="col" style="margin-left: -10px;">
                            <p>Vuoi iscriverti al corso?</p>
                        </div>
                    </div>
                    <input type="hidden" id="action" name="action" value="enroll">
                    <input type="hidden" id="id" name="id" value="<%=id%>">
                    <input type="hidden" id="title" name="title" value="<%=title%>">
                    <button class="btn btn-outline-primary" type="submit" style="font-size: 20px;">Iscrivimi</button>
                </div>
            </form>
        </div>
    </section>
</main>
<footer class="page-footer"><img class="img-fluid"
                                 src="../assets/img/Elegant%20Education%20Technology%20Logo%20Template%20(1).png"
                                 width="300" height="150"></footer>
</body>

</html>
