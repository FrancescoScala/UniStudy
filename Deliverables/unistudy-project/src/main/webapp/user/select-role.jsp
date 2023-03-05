<%@ page import="member.beans.Member" %>
<%@ page import="member.beans.Role" %>
<%@ page import="user.beans.Member" %><%--
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
    <link rel="stylesheet" href="../assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato:300,400,700&amp;display=swap">
    <link rel="stylesheet" href="../assets/css/-Login-form-Page-BS4--Login-form-Page-BS4.css">
    <link rel="stylesheet" href="../assets/css/Banner-Heading-Image-images.css">
    <link rel="stylesheet" href="../assets/css/Custom-File-Upload.css">
    <link rel="stylesheet" href="../assets/css/Cute-Select.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/pikaday/1.6.1/css/pikaday.min.css">
    <link rel="stylesheet" href="../assets/css/Multiple-Input-Select-Pills.css">
    <link rel="stylesheet" href="../assets/css/Search-Input-Responsive-with-Icon.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu-sidebar.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu.css">
</head>

<body>
<nav class="navbar navbar-dark navbar-expand-lg fixed-top bg-white portfolio-navbar gradient"
     style="padding-bottom: 0px;margin-top: -20px;">
    <div class="container"><a class="navbar-brand logo" href="<%=request.getContextPath()%>/partecipante/homepage.jsp"><img class="img-fluid"
                                                                                                                            src="<%=request.getContextPath()%>/assets/img/UniStudy%20Logo%20-%20White.png"
                                                                                                                            style="padding-right: 0px;"
                                                                                                                            width="232"
                                                                                                                            height="91"></a>
        <button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navbarNav"><span
                class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
            <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/CourseControl?action=view&qty=all-objects">Tutti i corsi</a></li>
            <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/partecipante/homepage.jsp">I miei corsi</a></li>
            <li class="nav-item">
                <div class="nav-item dropdown show" style="position: relative;padding: 8px;"><a
                        class="dropdown-toggle text-decoration-none" aria-expanded="true" data-bs-toggle="dropdown"
                        href="#"
                        style="color: var(--bs-navbar-active-color);display: flex;font-weight: bold;backdrop-filter: opacity(1);-webkit-backdrop-filter: opacity(1);"><%=((Member) session.getAttribute("userInSession")).getName()%>
                </a>
                    <div class="dropdown-menu" data-bs-popper="none"><a class="dropdown-item"
                                                                        href="<%=request.getContextPath()%>/member/select-role.jsp">Seleziona
                        permessi</a><a class="dropdown-item" href="<%=request.getContextPath()%>/UserControl?action=logout">Logout</a></div>
                </div>
            </li>
        </ul>
        </div>
    </div>
</nav>
<main class="page projects-page">
    <section class="portfolio-block projects-cards" style="padding-top: 0px;padding-bottom: 0px;">
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
                                        for (Role role : ((Member) request.getSession().getAttribute("userInSession")).getRoles()) {
                                            if (role.getRoleName().toString().equals("AMMINISTRATORE")) {
                                    %>
                                    <label class="selectgroup-item"><input
                                            type="checkbox" name="value" value="HTML" class="selectgroup-input"
                                            checked/><a class="selectgroup-button" href="<%=request.getContextPath()%>/course/homepage.jsp">Partecipante</a></label>
                                    <%
                                        }
                                        if (role.getRoleName().toString().equals("GESTOREUTENTI")) {
                                    %>
                                    <label
                                            class="selectgroup-item"><input type="checkbox" name="value" value="CSS"
                                                                            class="selectgroup-input"/><span
                                            class="selectgroup-button">Gestore utenti</span></label>
                                    <%
                                        }
                                        if (role.getRoleName().toString().equals("AMMINISTRATORE")) {
                                    %>
                                    <label class="selectgroup-item"><input type="checkbox" name="value" value="CSS"
                                                                           class="selectgroup-input"/><a
                                            class="selectgroup-button" href="<%=request.getContextPath()%>/amministratore/add_course.jsp">Amministratore</a></label>
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
                                 src="../assets/img/Elegant%20Education%20Technology%20Logo%20Template%20(1).png"
                                 width="300" height="150"></footer>
<script src="../assets/bootstrap/js/bootstrap.min.js"></script>
<script src="../assets/js/Custom-File-Upload.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/pikaday/1.6.1/pikaday.min.js"></script>
<script src="../assets/js/Sidebar-Menu.js"></script>
<script src="../assets/js/theme.js"></script>
</body>

</html>
