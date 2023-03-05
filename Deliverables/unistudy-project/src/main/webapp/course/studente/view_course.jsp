<%@ page import="course.beans.Course" %>
<%@ page import="course.beans.Notice" %>
<%@ page import="java.util.Set" %>
<%@ page import="course.beans.Note" %>
<%@ page import="member.beans.Member" %>
<%@ page import="user.beans.Member" %><%--
  Created by IntelliJ IDEA.
  Member: francesco
  Date: 08/02/23
  Time: 18:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Pagina del corso</title>
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
<main class="page cv-page">
    <section class="portfolio-block block-intro border-bottom">
        <section class="py-4 py-xl-5">
            <div class="container">
                <div class="bg-dark border rounded border-0 border-dark overflow-hidden; bg-white">
                    <div class="row g-0">
                        <div class="col-md-6">
                            <form method="post" action="CourseControl">
                                <div class="p-4 p-md-5" style="background: white;margin-bottom: 1px;">
                                    <h1 style="margin-bottom: 20px;color: black;"><%=course.getTitle()%>
                                    </h1>
                                    <p class="text-start"><strong>Professori</strong>: <%=course.getProfessors()%>
                                    </p>
                                    <p class="text-start"><strong>Orari</strong>: <%=course.getTimeSchedule()%>
                                    </p>
                                    <input type="hidden" name="action" value="delete-enroll">
                                    <input type="hidden" name="id" value="<%=course.getId()%>">
                                    <div class="my-3">
                                        <button class="btn btn-primary btn-lg me-2" type="submit">Disiscrivimi</button>
                                    </div>
                                </div>
                            </form>

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
                    for (Notice notice : notices) {
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
                <%
                    Set<Note> notes = ((Course) request.getAttribute("course")).getNotes();
                    for (Note note : notes) {
                %>
                <div class="item">
                    <div class="row">
                        <div class="col-md-6 col-lg-9">
                            <h3><%=note.getTitle()%>
                            </h3>
                            <h4 class="organization"><%=note.getAuthorInfo()%>
                            </h4>
                        </div>
                        <div class="col-md-6 col-lg-3">
                            <div class="table-responsive border-primary" style="padding-right: 0px;">
                                <%if ((((Member) request.getSession().getAttribute("userInSession")).getId()) == note.getAuthorId()) {%>
                                <table class="table">
                                    <tbody>
                                    <tr>
                                        <form method="post" action="NoteControl">
                                            <input type="hidden" name="action" value="delete">
                                            <input type="hidden" name="id" value="<%=course.getId()%>">
                                            <input type="hidden" name="noteId" value="<%=note.getId()%>">
                                            <input type="hidden" name="noteAuthorId" value="<%=note.getAuthorId()%>">
                                            <td>
                                                <button type="submit"
                                                        style="color: var(--bs-red);">Rimuovi
                                                </button>
                                            </td>
                                        </form>
                                    </tr>
                                    <tr></tr>
                                    </tbody>
                                </table>
                                <%}%>
                            </div>
                            <span class="period"><%=note.getCreationDate()%></span>
                        </div>
                    </div>
                    <p class="text-muted"><%=note.getDescription()%>
                    </p>
                </div>
                <% }
                %>
            </div>
            <div class="row" style="text-align: center;">
                <div class="col"><a class="btn btn-primary btn-lg me-2" role="button"
                                    href="partecipante/corso/studente/publish-note.jsp?id=<%=course.getId()%>"
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

