<%@ page import="user.beans.Member" %>
<%@ page import="user.beans.Role" %><%--
  Created by IntelliJ IDEA.
  Member: francesco
  Date: 10/02/23
  Time: 22:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Seleziona permesso</title>
</head>
<body>
<jsp:include page="/header.jsp" flush="true"></jsp:include>
<main class="page projects-page">
    <section class="portfolio-block projects-cards" >
        <div class="container">
            <div class="heading"></div>
            <div class="row">
                <div class="col-md-6 col-lg-12">
                    <div class="card border-0"><a href="#"></a>
                        <div class="card-body">
                            <h3>Seleziona uno fra i tuoi permessi</h3>
                            <div>
                                <div class="selectgroup selectgroup-pills">
                                    <%
                                        for (Role role : ((Member) request.getSession().getAttribute("memberInSession")).getRoles()) {
                                            if (role.getRoleName().toString().equals("GESTOREUTENTI")) {
                                    %>
                                    <label
                                            class="selectgroup-item"><input type="checkbox" name="value" value="CSS"
                                                                            class="selectgroup-input"/><span
                                            class="selectgroup-button">Gestore utenti</span></label>
                                    <%
                                        }
                                        if (role.getRoleName().toString().equals("AMMINISTRATORE")) {
                                            System.out.println("è AMMINISTAR");
                                    %>
                                    <label class="selectgroup-item"><input type="checkbox" name="value" value="CSS"
                                                                           class="selectgroup-input"/><a
                                            class="selectgroup-button"
                                            href="<%=request.getContextPath()%>/course/amministratore/add_course.jsp">Amministratore</a></label>
                                    <%
                                            }
                                        }
                                    %>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>
<footer class="page-footer"><img class="img-fluid"
                                 src="<%=request.getContextPath()%>/assets/img/Elegant%20Education%20Technology%20Logo%20Template%20(1).png"
                                 width="300" height="150"></footer>
</body>
</html>
