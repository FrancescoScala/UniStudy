<%@ page import="it.unisa.beans.Course" %>
<%@ page import="java.util.Set" %>
<%@ page import="it.unisa.beans.Enrollment" %>
<%@ page import="it.unisa.beans.User" %><%--
  Created by IntelliJ IDEA.
  User: francesco
  Date: 11/02/23
  Time: 15:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Projects - Brand</title>
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
<nav class="navbar navbar-dark navbar-expand-lg fixed-top bg-white portfolio-navbar gradient"
     style="padding-bottom: 0px;margin-top: -20px;">
    <div class="container"><a class="navbar-brand logo"
                              href="<%=request.getContextPath()%>/partecipante/homepage.jsp"><img class="img-fluid"
                                                                                                  src="<%=request.getContextPath()%>/assets/img/UniStudy%20Logo%20-%20White.png"
                                                                                                  style="padding-right: 0px;"
                                                                                                  width="232"
                                                                                                  height="91"></a>
        <button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navbarNav"><span
                class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/CourseControl?action=view&qty=all-objects">Tutti i corsi</a></li>
                <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/partecipante/homepage.jsp">I
                    miei corsi</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Modifica info
                    personali</a></li>
                <li class="nav-item">
                    <div class="nav-item dropdown show" style="position: relative;padding: 8px;"><a
                            class="dropdown-toggle text-decoration-none" aria-expanded="true" data-bs-toggle="dropdown"
                            href="#"
                            style="color: var(--bs-navbar-active-color);display: flex;font-weight: bold;backdrop-filter: opacity(1);-webkit-backdrop-filter: opacity(1);"><%=((User) session.getAttribute("userInSession")).getName()%>
                    </a>
                        <div class="dropdown-menu show" data-bs-popper="none"><a class="dropdown-item"
                                                                                 href="<%=request.getContextPath()%>/partecipante/select-role.jsp">Seleziona
                            permessi</a><a class="dropdown-item"
                                           href="<%=request.getContextPath()%>/UserControl?action=logout">Logout</a>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</nav>
<main class="page projects-page">
    <section class="portfolio-block projects-cards">

        <div class="container">
            <div class="heading">
                <h2>Tutti i corsi</h2>
            </div>
            <div class="row">
                <%
                    Set<Course> courses = (Set<Course>) request.getAttribute("courses");
                    if (courses.size() >= 1) {
                        for (Course course : courses) {
                %>
                <div class="col-md-6 col-lg-4">
                    <div class="card border-0"><a href="#"></a>
                        <form method="post" action="<%=request.getContextPath()%>/CourseControl">
                            <div class="card-body">
                                <h6><%=course.getTitle()%>
                                </h6>
                                <div>
                                    <input type="hidden" id="action" name="action" value="view">
                                    <input type="hidden" id="qty" name="qty" value="single">
                                    <input type="hidden" id="id" name="id" value="<%=course.getId()%>">
                                    <input type="hidden" id="courseTitle" name="courseTitle"
                                           value="<%=course.getTitle()%>">
                                    <div class="selectgroup selectgroup-pills">
                                        <label class="selectgroup-item"><input
                                                type="checkbox" name="value" value="HTML" class="selectgroup-input"
                                                checked/>
                                            <button value="STUDENTE" name="role" type="submit"
                                                    class="selectgroup-button">
                                                Visualizza
                                            </button>
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <%
                        }
                    }
                %>
            </div>
        </div>
    </section>
</main>
<footer class="page-footer"><img class="img-fluid"
                                 src="<%=request.getContextPath()%>/assets/img/Elegant%20Education%20Technology%20Logo%20Template%20(1).png"
                                 width="300" height="150"></footer>
<script src="<%=request.getContextPath()%>/assets/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/Custom-File-Upload.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/pikaday/1.6.1/pikaday.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/Sidebar-Menu.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/theme.js"></script>
</body>

</html>
