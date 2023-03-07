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
    <title>Modifica info corso</title>

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
                $.post("<%=request.getContextPath()%>/CourseControl",
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
                $.post("<%=request.getContextPath()%>/CourseControl",
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
                            $("#mex").html("Aggiornamento corso effettuato");
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
    <section class="portfolio-block hire-me">
        <section class="portfolio-block projects-with-sidebar" style="padding-top: 0px;">
            <div class="container">
                <div class="heading"></div>
                <div class="row">
                    <div class="col-md-3">
                        <ul class="list-unstyled fs-5 sidebar">
                            <%String contextPath = request.getContextPath() + "/course/gestorecorso/";%>
                            <li><a class="active"
                                   href="<%=contextPath%>info-modify-course.jsp?id=<%=request.getParameter("id")%>">Modifica
                                info
                                corso</a></li>
                            <li><a href="<%=contextPath%>publish-alert.jsp?id=<%=request.getParameter("id")%>">Pubblica
                                avviso</a></li>
                            <li>
                                <a href="<%=request.getContextPath()%>/NoticeControl?action=view&id=<%=request.getParameter("id")%>">Rimuovi
                                    avviso</a></li>
                            <li>
                                <a href="<%=request.getContextPath()%>/NoteControl?action=view&id=<%=request.getParameter("id")%>">Rimuovi
                                    appunto</a></li>
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
                                            class="form-control" type="text" name="schedule" required=""
                                            pattern="^((Lun|Mar|Mer|Gio|Ven)\s(0[0-9]|1[0-9]|2[0-4]):[0-5][0-9]\s-\s(0[0-9]|1[0-9]|2[0-4]):[0-5][0-9],?\s?)*$"
                                            oninvalid="this.setCustomValidity('Il campo evidenziato deve rispettare il formato. Es: Lun 09:00 - 10:00')"
                                            onchange="this.setCustomValidity('')" id="schedule" value="">
                                    </div>
                                    <div id=" professors-div" class="mb-3"><label class="form-label"
                                                                                  id="professors-label"
                                                                                  for="professors">Professori</label><input
                                            class='form-control' type='text' name='professors' required=""
                                            pattern="^^(?:[a-zA-Z](?:\s[a-zA-Z]+)?(?:,\s?(?=[a-zA-Z]))?)+(?!,$)$"
                                            oninvalid="this.setCustomValidity('Il campo evidenziato contenere solo lettere e/o spazi al suo interno. In caso di più docenti, separarli tramite una virgola')"
                                            onchange="this.setCustomValidity('')" id='professors' value="">
                                    </div>
                                    <div class="mb-3">
                                        <div class="row">
                                            <div class="col-md-12 col-xl-12 justify-content-center button">
                                                <button id="confirmButton" class="btn btn-primary d-block w-100"
                                                        type="submit">Conferma
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
</body>
</html>
