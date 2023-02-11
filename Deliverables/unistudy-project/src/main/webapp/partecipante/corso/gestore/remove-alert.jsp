<%@ page import="it.unisa.beans.Notice" %>
<%@ page import="java.util.Set" %><%--
  Created by IntelliJ IDEA.
  User: francesco
  Date: 10/02/23
  Time: 21:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
  <title>Hire me - Brand</title>
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
<nav class="navbar navbar-dark navbar-expand-lg fixed-top bg-white portfolio-navbar gradient" style="padding-bottom: 0px;margin-top: -20px;">
  <div class="container"><a class="navbar-brand logo" href="../partecipante/my-courses.html"><img class="img-fluid" src="assets/img/UniStudy%20Logo%20-%20White.png" style="padding-right: 0px;" width="232" height="91"></a><button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navbarNav"><span class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav ms-auto">
        <li class="nav-item"><a class="nav-link" href="my-courses.html">I miei corsi</a></li>
        <li class="nav-item"><a class="nav-link" href="../partecipante/info-modify.html">Modifica info personali</a></li>
        <li class="nav-item">
          <div class="nav-item dropdown show" style="position: relative;padding: 8px;"><a class="dropdown-toggle text-decoration-none" aria-expanded="true" data-bs-toggle="dropdown" href="#" style="color: var(--bs-navbar-active-color);display: flex;font-weight: bold;backdrop-filter: opacity(1);-webkit-backdrop-filter: opacity(1);">NomeUtente</a>
            <div class="dropdown-menu show" data-bs-popper="none"><a class="dropdown-item" href="../partecipante/select-role.html">Seleziona permessi</a><a class="dropdown-item" href="#">Logout</a></div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</nav>
<main class="page hire-me-page">
  <section class="portfolio-block hire-me" style="padding-top: 0px;">
    <section class="portfolio-block projects-with-sidebar" style="padding-top: 0px;">
      <div class="container">
        <div class="heading"></div>
        <div class="row">
          <div class="col-md-3">
            <ul class="list-unstyled fs-5 sidebar">
              <li><a href="info-modify-course.jsp?id=<%=request.getParameter("id")%>">Modifica info corso</a></li>
              <li><a class="active" href="publish-alert.jsp?id=<%=request.getParameter("id")%>">Pubblica avviso</a></li>
              <li><a href="modify-alert.html?id=<%=request.getParameter("id")%>">Modifica avviso</a></li>
              <li><a href="<%=request.getContextPath()%>/NoticeControl?action=view&id=<%=request.getParameter("id")%>">Rimuovi avviso</a></li>
              <li><a href="remove-note.html?id=<%=request.getParameter("id")%>">Rimuovi appunto</a></li>
            </ul>
          </div>
          <div class="col-md-9">
            <div class="row">
              <div class="col" style="padding-left: 0px;padding-right: 0px;">
                <div class="work-experience group" style="padding-left: 19px;padding-right: 20px;">
                  <div class="heading">
                    <h2 class="text-center">RIMUOVI AVVISO</h2>
                  </div>
                  <%for(Notice notice : (Set<Notice>)request.getAttribute("notices")){%>
                  <div class="item">
                    <div class="row">
                      <div class="col-md-6 col-lg-9">
                        <h3><%=notice.getTitle()%></h3>
                      </div>
                      <div class="col-md-6 col-lg-3"><span class="period"><%=notice.getCreationDate()%></span>
                        <div class="table-responsive border-primary" style="padding-right: 0px;">
                          <table class="table">
                            <tbody>
                            <tr>
                              <td style="padding-left: 0px;padding-right: 0px;padding-bottom: 0px;"><a class="text-decoration-none" href="#" style="color: var(--bs-red);">Rimuovi</a></td>
                            </tr>
                            <tr></tr>
                            </tbody>
                          </table>
                        </div>
                      </div>
                    </div>
                    <p class="text-muted col-lg-9"><%=notice.getDescription()%></p>
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
<footer class="page-footer"><img class="img-fluid" src="assets/img/Elegant%20Education%20Technology%20Logo%20Template%20(1).png" width="300" height="150"></footer>
<script src="assets/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/js/Custom-File-Upload.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/pikaday/1.6.1/pikaday.min.js"></script>
<script src="assets/js/Sidebar-Menu.js"></script>
<script src="assets/js/theme.js"></script>
</body>

</html>
