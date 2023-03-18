<%--
  Created by IntelliJ IDEA.
  Member: nickm
  Date: 06/02/2023
  Time: 16:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Aggiungi corso</title>
    <script
            src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js">
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            console.log("Ready!");
            $("#createCourseButton").click(chiamaControl);

            function chiamaControl() {
                console.log("chiamaControl");
                $.post("<%=request.getContextPath()%>/CourseControl",
                    {
                        "action": $("#action").val(),
                        "title": $("#title").val(),
                        "professors": $("#professors").val(),
                        "schedule": $("#schedule").val()
                    },
                    function (data) {
                        var success = "\"OK\"";
                        var result = (JSON.stringify(data.result)).localeCompare(success);

                        if (result == 0) {
                            console.log("ok!");
                            $("#mex").html("Aggiunta effettuata");
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
<main class="page hire-me-page">
    <section class="portfolio-block hire-me">
        <section class="portfolio-block projects-with-sidebar" style="padding-top: 0px;">
            <div class="container">
                <div class="heading"></div>
                <div class="row">
                    <div class="col-md-3">
                        <ul class="list-unstyled fs-5 sidebar">
                            <li><a class="active"
                                   href="<%=request.getContextPath()%>/course/amministratore/add_course.jsp">Aggiungi
                                corso</a></li>
                            <li><a href="<%=request.getContextPath()%>/course/amministratore/delete_course.jsp">Rimuovi
                                corso</a></li>
                        </ul>
                    </div>
                    <div class="col-md-9">
                        <div class="row">
                            <div class="col" style="padding-left: 0px;padding-right: 0px;">
                                <h3><span id="mex"></span></h3>
                                <form id="form-id" method="post" action="#" onSubmit="return false;">
                                    <input type="hidden" id="action" name="action" value="create">
                                    <h2 style="margin-bottom: 30px;">Aggiungi corso</h2>
                                    <div class="mb-3"><label class="form-label" for="title">Titolo</label><input
                                            class="form-control" name="title" required=""
                                            pattern="^.{1,50}$"
                                            oninvalid="this.setCustomValidity('Il campo evidenziato deve avere da 1 a 50 caratteri')"
                                            onchange="this.setCustomValidity('')" id="title"></div>
                                    <div class="mb-3"><label class="form-label" for="schedule">Orario</label><input
                                            class="form-control" placeholder="Lun 09:00 - 11:00, Gio 15:00 - 18:00" name="schedule" required=""
                                            pattern="^((Lun|Mar|Mer|Gio|Ven)\s(0[0-9]|1[0-9]|2[0-4]):[0-5][0-9]\s-\s(0[0-9]|1[0-9]|2[0-4]):[0-5][0-9],?\s?)*$"
                                            oninvalid="this.setCustomValidity('Il campo evidenziato deve rispettare il formato. Es: Lun 09:00 - 10:00')"
                                            onchange="this.setCustomValidity('')" id="schedule"></div>
                                    <div class="mb-3"><label class="form-label"
                                                             for="professors">Professori</label><input
                                            class="form-control" name="professors" required=""
                                            pattern="^^(?:[a-zA-Z](?:\s[a-zA-Z]+)?(?:,\s?(?=[a-zA-Z]))?)+(?!,$)$"
                                            oninvalid="this.setCustomValidity('Il campo evidenziato deve contenere solo lettere e/o spazi al suo interno. In caso di più docenti, separarli tramite una virgola')"
                                            onchange="this.setCustomValidity('')" id="professors"></div>
                                    <button id="createCourseButton" class="btn btn-primary d-block w-100"
                                            type="submit">Conferma
                                    </button>
                                </form>
                                <div class="mb-3">
                                    <div class="row">
                                        <div class="col-md-12 col-xl-12 justify-content-center button">

                                        </div>
                                    </div>
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
                                 src="../../assets/img/Elegant%20Education%20Technology%20Logo%20Template%20(1).png"
                                 width="300" height="150"></footer>
</body>
</html>