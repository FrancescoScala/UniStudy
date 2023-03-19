<%@ page import="course.beans.Notice" %>
<%@ page import="java.util.Set" %>
<%@ page import="user.beans.Member" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Rimuovi avviso</title>
</head>
<body>
<jsp:include page="/header.jsp" flush="true"></jsp:include>
<main class="page hire-me-page">
    <section class="portfolio-block hire-me">
        <section class="portfolio-block projects-with-sidebar" style="padding-top: 0px;">
            <div class="container">
                <div class="heading"></div>
                <div class="row">
                    <div class="col-md-3">
                        <ul class="list-unstyled fs-5 sidebar">
                            <%String contextPath = request.getContextPath()+"/course/gestorecorso/";%>
                            <li><a href="<%=contextPath%>info-modify-course.jsp?id=<%=request.getParameter("id")%>">Modifica info
                                corso</a></li>
                            <li><a href="<%=contextPath%>publish-alert.jsp?id=<%=request.getParameter("id")%>">Pubblica
                                avviso</a></li>
                            <li>
                                <a class="active" href="<%=request.getContextPath()%>/NoticeControl?action=view&id=<%=request.getParameter("id")%>">Rimuovi
                                    avviso</a></li>
                            <li><a href="<%=request.getContextPath()%>/NoteControl?action=view&id=<%=request.getParameter("id")%>">Rimuovi appunto</a></li>
                        </ul>
                    </div>
                    <div class="col-md-9">
                        <div class="row">
                            <div class="col" style="padding-left: 0px;padding-right: 0px;">
                                <div class="work-experience group" style="padding-left: 19px;padding-right: 20px;">
                                    <div class="heading">
                                        <h2 class="text-center">RIMUOVI AVVISO</h2>
                                    </div>
                                    <%for (Notice notice : (Set<Notice>) request.getAttribute("notices")) {%>
                                    <div class="item">
                                        <div class="row">
                                            <div class="col-md-6 col-lg-9">
                                                <h3><%=notice.getTitle()%>
                                                </h3>
                                            </div>
                                            <div class="col-md-6 col-lg-3"><span
                                                    class="period"><%=notice.getCreationDate()%></span>
                                                <div class="table-responsive border-primary"
                                                     style="padding-right: 0px;">
                                                    <table class="table">
                                                        <tbody>
                                                        <tr>
                                                            <form method="post" action="NoticeControl">
                                                                <input type="hidden" name="action" value="delete">
                                                                <input type="hidden" name="noticeId" value="<%=notice.getId()%>">
                                                                <input type="hidden" name="id" value="<%=request.getParameter("id")%>">
                                                                <td style="padding-left: 0px;padding-right: 0px;padding-bottom: 0px;">
                                                                    <button type="submit"
                                                                       style="color: var(--bs-red);">Rimuovi</button></td>
                                                            </form>
                                                        </tr>
                                                        <tr></tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                        <p class="text-muted col-lg-9"><%=notice.getDescription()%>
                                        </p>
                                    </div>
                                    <%}%>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </section>
</main>
<footer class="page-footer"><img class="img-fluid"
                                 src="assets/img/Elegant%20Education%20Technology%20Logo%20Template%20(1).png"
                                 width="300" height="150"></footer>
</body>
</html>
