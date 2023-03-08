<%@ page import="course.beans.Course" %>
<%@ page import="course.beans.Notice" %>
<%@ page import="java.util.Set" %>
<%@ page import="course.beans.Note" %>
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
</head>
<body>
<%Course course = (Course) request.getAttribute("course");%>
<jsp:include page="/header.jsp" flush="true"></jsp:include>
<main class="page cv-page">
    <section class="portfolio-block block-intro border-bottom">
        <section class="py-4 py-xl-5">
            <div class="container">
                <div class="bg-dark border rounded border-0 border-dark overflow-hidden; bg-white">
                    <div class="row g-0">
                        <div class="col-md-6">
                            <form method="post" action="<%=request.getContextPath()%>/CourseControl">
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
                                <%if ((((Member) request.getSession().getAttribute("memberInSession")).getId()) == note.getAuthorId()) {%>
                                <table class="table">
                                    <tbody>
                                    <tr>
                                        <form method="post" action="<%=request.getContextPath()%>/NoteControl">
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
                                    href="<%=request.getContextPath()%>/course/studente/publish-note.jsp?id=<%=course.getId()%>"
                                    style="background: rgb(32,201,151);">Aggiungi un nuovo appunto</a></div>
            </div>
        </div>
    </section>
</main>
<footer class="page-footer"><img class="img-fluid"
                                 src="assets/img/Elegant%20Education%20Technology%20Logo%20Template%20(1).png"
                                 width="300" height="150"></footer>
</body>
</html>

