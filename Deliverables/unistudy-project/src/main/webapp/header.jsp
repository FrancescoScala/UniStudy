<%@ page import="user.beans.Member" %><%--
  Created by IntelliJ IDEA.
  User: nickm
  Date: 06/03/2023
  Time: 18:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Header</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato:300,400,700&amp;display=swap">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/fonts/fontawesome-all.min.css">
    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/assets/css/-Login-form-Page-BS4--Login-form-Page-BS4.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/Banner-Heading-Image-images.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/Custom-File-Upload.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/Cute-Select.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/pikaday/1.6.1/css/pikaday.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/Multiple-Input-Select-Pills.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/Search-Input-Responsive-with-Icon.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/Sidebar-Menu-sidebar.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/Sidebar-Menu.css">
</head>
<body>
<nav class="navbar navbar-dark navbar-expand-lg fixed-top bg-white portfolio-navbar gradient mb-2"
     style="padding-bottom: 0px;margin-top: -20px;">
    <div class="container"><a class="navbar-brand logo" href="<%=request.getContextPath()%>/course/homepage.jsp">
        <img class="img-fluid"
             src="<%=request.getContextPath()%>/assets/img/UniStudy%20Logo%20-%20White.png"
             style="padding-right: 0px;"
             width="232"
             height="91"></a>
        <button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navbarNav"><span
                class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item"><a class="nav-link"
                                        href="<%=request.getContextPath()%>/CourseControl?action=view&qty=all-objects">Tutti
                    i corsi</a></li>
                <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/course/homepage.jsp">I miei
                    corsi</a></li>
                <li class="nav-item">
                    <div class="nav-item dropdown show" style="position: relative;padding: 8px;"><a
                            class="dropdown-toggle text-decoration-none" aria-expanded="true" data-bs-toggle="dropdown"
                            href="#"
                            style="color: var(--bs-navbar-active-color);display: flex;font-weight: bold;backdrop-filter: opacity(1);-webkit-backdrop-filter: opacity(1);"><%=((Member) session.getAttribute("memberInSession")).getName()%>
                    </a>
                        <div class="dropdown-menu" data-bs-popper="none"><a class="dropdown-item"
                                                                            href="<%=request.getContextPath()%>/user/select-role.jsp">Seleziona
                            permessi</a><a class="dropdown-item"
                                           href="<%=request.getContextPath()%>/user/MemberControl?action=logout">Logout</a><a
                                class="dropdown-item"
                                href="<%=request.getContextPath()%>/user/info-modify.jsp">Modifica info personali</a>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</nav>
<script src="<%=request.getContextPath()%>/assets/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/Custom-File-Upload.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/pikaday/1.6.1/pikaday.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/Sidebar-Menu.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/theme.js"></script>
</body>
</html>
