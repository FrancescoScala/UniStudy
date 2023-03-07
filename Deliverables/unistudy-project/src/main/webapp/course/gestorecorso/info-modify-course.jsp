<%@ page import="member.beans.Member" %>
<%@ page import="user.beans.Member" %><%--
  Created by IntelliJ IDEA.
  Member: nickm
  Date: 10/02/2023
  Time: 19:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Hire me - Brand</title>
    <link rel="stylesheet" href="../../assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato:300,400,700&amp;display=swap">
    <link rel="stylesheet" href="../../assets/css/-Login-form-Page-BS4--Login-form-Page-BS4.css">
    <link rel="stylesheet" href="../../assets/css/Banner-Heading-Image-images.css">
    <link rel="stylesheet" href="../../assets/css/Custom-File-Upload.css">
    <link rel="stylesheet" href="../../assets/css/Cute-Select.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/pikaday/1.6.1/css/pikaday.min.css">
    <link rel="stylesheet" href="../../assets/css/Multiple-Input-Select-Pills.css">
    <link rel="stylesheet" href="../../assets/css/Search-Input-Responsive-with-Icon.css">
    <link rel="stylesheet" href="../../assets/css/Sidebar-Menu-sidebar.css">
    <link rel="stylesheet" href="../../assets/css/Sidebar-Menu.css">

    <script
            src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js">
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            console.log("Ready!");
            loadCourseInfo();
            $("#confirmButton").click(chiamaControl);

            function loadCourseInfo() {
                console.log("loadCourseInfo()");
                var jqxhr = $.post("<%=request.getContextPath()%>/CourseControl",
                    {
                        "action": "view",
                        "qty": "one",
                        "id": <%=request.getParameter("id")%>
                    },
                    function (data) {
                        let course = data.course;

                        let object = course[0];
                        console.log(object);
                        $("#title").append(object["title"]);
                        let schedule = document.getElementById("schedule");
                        let professors = document.getElementById("professors");
                        schedule.value = object["timeSchedule"];
                        professors.value = object["professors"];
                    });
            }

            function chiamaControl() {
                console.log("chiamaControl");
                var jqxhr = $.post("<%=request.getContextPath()%>/CourseControl",
                    {
                        "action": "modify",
                        "id": $("#id").val(),
                        "schedule": $("#schedule").val(),
                        "professors": $("#professors").val()
                    },
                    function (data) {
                        var success = "\"OK\"";
                        var result = (JSON.stringify(data.result)).localeCompare(success);

                        if (result == 0) {
                            console.log("ok!");
                            $("#mex").html("Aggiunta avviso effettuata");
                        } else {
                            $("#mex").html(data.result);
                        }
                    });
            }
        });
    </script>

</head>

<body>
<jsp:include page="/header.jsp" flush="true"></jsp:include>
<main class="page hire-me-page mt-5">
    <section class="portfolio-block hire-me" style="padding-top: 0px;">
        <section class="portfolio-block projects-with-sidebar" style="padding-top: 0px;">
            <div class="container">
                <div class="heading"></div>
                <div class="row">
                    <div class="col-md-3">
                        <ul class="list-unstyled fs-5 sidebar">
                            <%String contextPath = request.getContextPath()+"/partecipante/corso/gestore/";%>
                            <li><a class="active" href="<%=contextPath%>info-modify-course.jsp?id=<%=request.getParameter("id")%>">Modifica info
                                corso</a></li>
                            <li><a href="<%=contextPath%>publish-alert.jsp?id=<%=request.getParameter("id")%>">Pubblica
                                avviso</a></li>
                            <li>
                                <a href="<%=request.getContextPath()%>/NoticeControl?action=view&id=<%=request.getParameter("id")%>">Rimuovi
                                    avviso</a></li>
                            <li><a href="<%=request.getContextPath()%>/NoteControl?action=view&id=<%=request.getParameter("id")%>">Rimuovi appunto</a></li>
                        </ul>
                    </div>
                    <div class="col-md-9">
                        <div class="row">
                            <div class="col" style="padding-left: 0px;padding-right: 0px;">
                                <form id="form-id" onsubmit="return false;">
                                    <h2 style="margin-bottom: 30px;">Modifica info corso</h2>
                                    <p id="mex"></p>
                                    <input type="hidden" id="id" name="id" value="<%=request.getParameter("id")%>">
                                    <div id="title-div" class="mb-3"><label class="form-label"
                                                                            for="title">Titolo</label>
                                        <h3 id="title"></h3></div>
                                    <div id="schedule-div" class="mb-3"><label id="schedule-label" class="form-label"
                                                                               for="schedule">Orario</label><input
                                            class="form-control" type="text" name="schedule" id="schedule" value="">
                                    </div>
                                    <div id=" professors-div" class="mb-3"><label class="form-label"
                                                                                  id="professors-label"
                                                                                  for="professors">Professori</label><input
                                            class='form-control' type='text' name='professors' id='professors' value="">
                                    </div>
                                    <div class="mb-3">
                                        <div class="row">
                                            <div class="col-md-12 col-xl-12 justify-content-center button">
                                                <button id="confirmButton" class="btn btn-primary d-block w-100" type="submit">Conferma
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </section>
</main>
<footer class="page-footer"><img class="img-fluid"
                                 src="../../assets/img/Elegant%20Education%20Technology%20Logo%20Template%20(1).png"
                                 width="300" height="150"></footer>
<script src="../../assets/bootstrap/js/bootstrap.min.js"></script>
<script src="../../assets/js/Custom-File-Upload.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/pikaday/1.6.1/pikaday.min.js"></script>
<script src="../../assets/js/Sidebar-Menu.js"></script>
<script src="../../assets/js/theme.js"></script>
</body>

</html>
