<%@ page import="java.util.Set" %>
<%@ page import="user.beans.Member" %>
<%@ page import="course.beans.Course" %>
<%--
  Created by IntelliJ IDEA.
  Member: francesco
  Date: 11/02/23
  Time: 15:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Tutti i corsi</title>
</head>
<body>
<jsp:include page="/header.jsp" flush="true"></jsp:include>
<main class="page projects-page mt-5">
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
</body>

</html>
