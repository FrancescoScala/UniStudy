<%@ page import="it.unisa.beans.Course" %>
<%@ page import="it.unisa.beans.Notice" %>
<%@ page import="java.util.Set" %><%--
  Created by IntelliJ IDEA.
  User: francesco
  Date: 08/02/23
  Time: 18:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>CV - Brand</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato:300,400,700&amp;display=swap">
    <link rel="stylesheet" href="assets/css/-Login-form-Page-BS4--Login-form-Page-BS4.css">
    <link rel="stylesheet" href="assets/css/Banner-Heading-Image-images.css">
    <link rel="stylesheet" href="assets/css/Custom-File-Upload.css">
    <link rel="stylesheet" href="assets/css/Cute-Select.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/pikaday/1.6.1/css/pikaday.min.css">
    <link rel="stylesheet" href="assets/css/Multiple-Input-Select-Pills.css">
    <link rel="stylesheet" href="assets/css/Search-Input-Responsive-with-Icon.css">
    <link rel="stylesheet" href="assets/css/Sidebar-Menu-sidebar.css">
    <link rel="stylesheet" href="assets/css/Sidebar-Menu.css">
</head>

<body>
<%Course course = (Course) request.getAttribute("course");%>
<nav class="navbar navbar-dark navbar-expand-lg fixed-top bg-white portfolio-navbar gradient"
     style="padding-bottom: 0px;margin-top: -20px;">
    <div class="container"><a class="navbar-brand logo" href="../partecipante/my-courses.html"><img class="img-fluid"
                                                                                                    src="assets/img/UniStudy%20Logo%20-%20White.png"
                                                                                                    style="padding-right: 0px;"
                                                                                                    width="232"
                                                                                                    height="91"></a>
        <button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navbarNav"><span
                class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item"><a class="nav-link" href="my-courses.html">I miei corsi</a></li>
                <li class="nav-item"><a class="nav-link" href="../partecipante/info-modify.html">Modifica info
                    personali</a></li>
                <li class="nav-item">
                    <div class="nav-item dropdown show" style="position: relative;padding: 8px;"><a
                            class="dropdown-toggle text-decoration-none" aria-expanded="true" data-bs-toggle="dropdown"
                            href="#"
                            style="color: var(--bs-navbar-active-color);display: flex;font-weight: bold;backdrop-filter: opacity(1);-webkit-backdrop-filter: opacity(1);">NomeUtente</a>
                        <div class="dropdown-menu show" data-bs-popper="none"><a class="dropdown-item"
                                                                                 href="../partecipante/select-role.html">Seleziona
                            permessi</a><a class="dropdown-item" href="#">Logout</a></div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</nav>
<main class="page cv-page">
    <section class="portfolio-block block-intro border-bottom">
        <section class="py-4 py-xl-5">
            <div class="container">
                <div class="bg-dark border rounded border-0 border-dark overflow-hidden; bg-white">
                    <div class="row g-0">
                        <div class="col-md-6">
                            <div class="p-4 p-md-5" style="background: white;margin-bottom: 1px;">
                                <h1 style="margin-bottom: 20px;color: black;"><%=course.getTitle()%>
                                </h1>
                                <p class="text-start"><strong>Professori</strong>: <%=course.getProfessors()%>
                                </p>
                                <p class="text-start"><strong>Orari</strong>: <%=course.getTimeSchedule()%>
                                </p>
                                <div class="my-3"><a class="btn btn-primary btn-lg me-2" role="button" href="#">Disiscrivimi</a>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 order-first order-md-last" style="min-height: 250px;"><img
                                class="w-100 h-100 fit-cover" src="assets/img/nature/image2.jpg"></div>
                    </div>
                </div>
            </div>
        </section>
    </section>
    <section class="portfolio-block cv">
        <div class="container">
            <div class="work-experience group">
                <div class="heading">
                    <h2 class="text-center">avvisi</h2>
                </div>

                <%
                    Set<Notice> notices = ((Course) request.getAttribute("course")).getNotices();
                    for (Notice notice: notices) {
                %>

                <div class="item">
                    <div class="row">
                        <div class="col-md-6 col-lg-9">
                            <h3><%=notice.getTitle()%>
                            </h3>
                            <h4 class="organization">Special Inc.</h4>
                        </div>
                        <div class="col-md-6 col-lg-3"><span class="period"><%=notice.getCreationDate()%></span></div>
                    </div>
                    <p class="text-muted"><%=notice.getDescription()%>
                    </p>
                </div>

                <% }
                %>
            </div>
            <div class="education group">
                <div class="heading">
                    <h2 class="text-center">appunti</h2>
                </div>
                <div class="item">
                    <div class="row">
                        <div class="col-md-6 col-lg-9">
                            <h3>Cos'è il middleware</h3>
                            <h4 class="organization">Mario Rossi</h4>
                        </div>
                        <div class="col-md-6 col-lg-3">
                            <div class="table-responsive border-primary" style="padding-right: 0px;">
                                <table class="table">
                                    <tbody>
                                    <tr>
                                        <td><a class="text-decoration-none" href="#" style="color: var(--bs-red);">Rimuovi</a>
                                        </td>
                                    </tr>
                                    <tr></tr>
                                    </tbody>
                                </table>
                            </div>
                            <span class="period">06/11/2018</span>
                        </div>
                    </div>
                    <p class="text-muted">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean eget velit
                        ultricies, feugiat est sed, efficitur nunc, vivamus vel accumsan dui.</p>
                </div>
                <div class="item">
                    <div class="row">
                        <div class="col-md-6 col-lg-9">
                            <h3>Introduction to Java EE</h3>
                            <h4 class="organization">Pinco Pallino</h4>
                        </div>
                        <div class="col-md-6 col-lg-3"><span class="period">06/11/2018</span></div>
                    </div>
                    <p class="text-muted">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean eget velit
                        ultricies, feugiat est sed, efficitur nunc, vivamus vel accumsan dui.</p>
                </div>
                <div class="item">
                    <div class="row">
                        <div class="col-md-6 col-lg-9">
                            <h3>Esercizi sui Socket dal laboratorio di lunedì</h3>
                            <h4 class="organization">Luigi Silvestre</h4>
                        </div>
                        <div class="col-md-6 col-lg-3"><span class="period">06/11/2018</span></div>
                    </div>
                    <p class="text-muted">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean eget velit
                        ultricies, feugiat est sed, efficitur nunc, vivamus vel accumsan dui.</p>
                </div>
            </div>
            <div class="row" style="text-align: center;">
                <div class="col"><a class="btn btn-primary btn-lg me-2" role="button" href="#"
                                    style="background: rgb(32,201,151);">Aggiungi un nuovo appunto</a></div>
            </div>
        </div>
    </section>
</main>
<footer class="page-footer"><img class="img-fluid"
                                 src="assets/img/Elegant%20Education%20Technology%20Logo%20Template%20(1).png"
                                 width="300" height="150"></footer>
<script src="assets/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/js/Custom-File-Upload.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/pikaday/1.6.1/pikaday.min.js"></script>
<script src="assets/js/Sidebar-Menu.js"></script>
<script src="assets/js/theme.js"></script>
</body>

</html>

